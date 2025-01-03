package server.dataManagers;

import server.Server;
import server.ServerManager;
import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.Institutions;
import shared.enums.MessageType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

// all method return a copy of data object to prevent directly data access
public class ServerLogDataManager {
	private static Log mainLog;
    private static ServerLogDataManager instance;

    // Internal storage for logs by institution and class type
    private final Map<Institutions, Map<Class<?>, List<String>>> institutionLogs;
    private final Map<Institutions, Map<Class<?>, Boolean>> modified; // Tracks modifications

    private static final String LOG_FILE_PATH_SUFFIX = ServerManager.LOGS_FILE_PATH_SUFFIX;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");

    private ServerLogDataManager() {
        institutionLogs = new HashMap<>();
        modified = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllLogs);
        mainLog = Log.getInstance(ServerGUI.logTextArea);
    }

    public static synchronized ServerLogDataManager getInstance() {
        if (instance == null) {
            instance = new ServerLogDataManager();
        }
        return instance;
    }

    // Add a log entry to the map
    public synchronized void addLog(Institutions institution, Class<?> logClass, String logEntry) {
        institutionLogs.putIfAbsent(institution, new HashMap<>());
        modified.putIfAbsent(institution, new HashMap<>());

        Map<Class<?>, List<String>> classLogs = institutionLogs.get(institution);
        Map<Class<?>, Boolean> classModified = modified.get(institution);

        classLogs.putIfAbsent(logClass, new ArrayList<>());
        classModified.putIfAbsent(logClass, false);

        classLogs.get(logClass).add(logClass.getName() + logEntry);
        classModified.put(logClass, true); // Mark as modified
    }

    // Retrieve all logs for an institution
    public synchronized List<String> getAllLogs(Institutions institution) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (List<String> l : institutionLogs.get(institution).values()) {
            if (!l.isEmpty()) {
                result.addAll(l);
            }
        }
        return result;
    }

    // Retrieve logs by class type for an institution
    public synchronized List<String> getLogsByType(Institutions institution, Class<?> logClass) {
        if (!institutionLogs.containsKey(institution) || !institutionLogs.get(institution).containsKey(logClass)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(institutionLogs.get(institution).get(logClass));
    }

    // Retrieve logs by content for an institution
    public synchronized List<String> getLogsByContent(Institutions institution, String content) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }
        List<String> result = new ArrayList<>();
        for (List<String> l : institutionLogs.get(institution).values()) {
            if (!l.isEmpty()) {
                for (String s : l){
                    if (s.toLowerCase().contains(content.toLowerCase())) {
                        result.add(s);
                    }
                }
            }
        }
        return result;
    }

    // Retrieve logs by date
    public synchronized List<String> getLogsByDate(String date) {
        List<String> result = new ArrayList<>();
        for (Map<Class<?> ,List<String>> m : institutionLogs.values()) {
            if (!m.isEmpty()) {
                for (List<String> l : m.values()){
                    for (String s : l){
                        if (s.startsWith("[" + date)) {
                            result.add(s);
                        }
                    }
                }
            }
        }
        return result;
    }

    // Retrieve logs by institution and date
    public synchronized List<String> getLogsByInstitutionIDAndDate(Institutions institution, String date) {
        List<String> result = new ArrayList<>();
        for (List<String> l : institutionLogs.get(institution).values()) {
            if (!l.isEmpty()) {
                for (String s : l){
                    if (s.startsWith("[" + date)) {
                        result.add(s);
                    }
                }
            }
        }
        return result;
    }

    // Retrieve logs for an institution between two times
    public synchronized List<String> getLogsByTimeRange(Institutions institution, String startTime, String endTime) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }

        LocalDateTime start = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endTime, DATE_TIME_FORMATTER);

        List<String> result = new ArrayList<>();
        for (List<String> l : institutionLogs.get(institution).values()) {
            if (!l.isEmpty()) {
                for (String s : l){
                    String timestamp = s.substring(1, s.indexOf("]")).trim();
                    try {
                        LocalDateTime logTime = LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER);
                        if (!logTime.isBefore(start) && !logTime.isAfter(end)) {
                            result.add(s);
                        }
                    } catch (Exception e) {
                    	mainLog.exception("ServerLogDataManager: " + e.toString());
                    }
                }
            }
        }
        return result;
    }

    // Save all logs to files
    public synchronized void saveAllLogs() {
        institutionLogs.forEach((institution, classLogs) -> {
            classLogs.forEach((logClass, logs) -> {
                if (Boolean.TRUE.equals(modified.getOrDefault(institution, Collections.emptyMap()).get(logClass))) {
                    saveLogsByInstitutionAndType(institution, logClass, logs);
                    modified.get(institution).put(logClass, false); // Reset modification flag
                }
            });
        });
    }

    // Save logs for a specific institution and class type
    private synchronized void saveLogsByInstitutionAndType(Institutions institution, Class<?> logClass, List<String> logs) {
        String filePath = ServerManager.DB_FILE_PATH_PREFIX + institution + File.separator + LOG_FILE_PATH_SUFFIX;

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String log : logs) {
                writer.write(log + System.lineSeparator());
            }
            mainLog.println("ServerLogDataManager: Logs saved for Institution: " + institution + ", Class: " + logClass.getSimpleName());
        } catch (IOException e) {
            mainLog.exception("ServerLogDataManager: Error saving logs for Institution: " + institution + ", Class: " + logClass.getSimpleName());
            mainLog.exception("ServerLogDataManager: " + e.toString());
            e.printStackTrace();
        }
    }

    // Load all logs from files
    public synchronized void loadAllLogs() {
        File log = null;
        List<File> logFiles = new ArrayList<>();
        for (Institutions i : Institutions.values()) {
            log = new File(ServerManager.DB_FILE_PATH_PREFIX + i.name() + File.separator + LOG_FILE_PATH_SUFFIX);
            if (!log.exists() || !log.isDirectory()) {
                mainLog.warn("ServerLogDataManager: Log directory not found. Skipping load operation.");
                return;
            }
            logFiles.add(log);
        }

        if (logFiles.isEmpty()) {
        	mainLog.warn("ServerLogDataManager: No log files found. Skipping load operation.");
            return;
        }

        for (File logFile : logFiles) {
            String fileName = logFile.getName();
            String[] parts = fileName.replace(".log", "").split("_");
            if (parts.length != 2) {
                continue; // Skip invalid files
            }
            Institutions institution = Institutions.valueOf(parts[0]);
            try {
                Class<?> logClass = Class.forName(parts[1]);
                try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        addLog(institution, logClass, line);
                    }
                }
            } catch (ClassNotFoundException | IOException e) {
            	mainLog.exception("ServerLogDataManager: Error loading logs from file: " + logFile.getName());
            	mainLog.exception("ServerLogDataManager: " + e.toString());
                e.printStackTrace();
            }
        }
    }
}



