package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.enums.MessageType;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class ServerLogDataManager {
    private static ServerLogDataManager instance;

    private ServerLogDataManager() {
        institutionLogs = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllLogs);
    }

    public static synchronized ServerLogDataManager getInstance() {
        if (instance == null) {
            instance = new ServerLogDataManager();
        }
        return instance;
    }

    private static final String LOG_FILE_PATH = ServerManager.LOGS_FILE_PATH;
    private static final DateTimeFormatter DATE_TIME_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss:SS");

    // Map to store logs by Institutions and Type
    private final Map<Institutions, Map<MessageType, List<String>>> institutionLogs;

    // Add a log entry to the map
    public synchronized void addLog(Institutions institution, MessageType type, String logEntry) {
        institutionLogs.putIfAbsent(institution, new HashMap<>());
        Map<MessageType, List<String>> typeLogs = institutionLogs.get(institution);
        typeLogs.putIfAbsent(type, new ArrayList<>());
        typeLogs.get(type).add(logEntry);
    }

    // Retrieve all logs for an institution
    public synchronized List<String> getAllLogs(Institutions institution) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }
        return institutionLogs.get(institution).values().stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    // Retrieve logs by type for an institution
    public synchronized List<String> getLogsByType(Institutions institution, MessageType type) {
        if (!institutionLogs.containsKey(institution) || !institutionLogs.get(institution).containsKey(type)) {
            return Collections.emptyList();
        }
        return new ArrayList<>(institutionLogs.get(institution).get(type));
    }

    // Retrieve logs by content for an institution
    public synchronized List<String> getLogsByContent(Institutions institution, String content) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }
        return institutionLogs.get(institution).values().stream()
                .flatMap(Collection::stream)
                .filter(log -> log.toLowerCase().contains(content.toLowerCase()))
                .collect(Collectors.toList());
    }

    // Retrieve logs by date
    public synchronized List<String> getLogsByDate(String date) {
        return institutionLogs.values().stream()
                .flatMap(map -> map.values().stream())
                .flatMap(Collection::stream)
                .filter(log -> log.startsWith("[" + date))
                .collect(Collectors.toList());
    }

    // Retrieve logs by institutionID and date
    public synchronized List<String> getLogsByInstitutionIDAndDate(Institutions institutionID, String date) {
        return institutionLogs.values().stream()
                .flatMap(map -> map.values().stream())
                .flatMap(Collection::stream)
                .filter(log -> log.contains(institutionID.name()) && log.startsWith("[" + date))
                .collect(Collectors.toList());
    }

    // Retrieve logs for an institution between two times
    public synchronized List<String> getLogsByTimeRange(Institutions institution, String startTime, String endTime) {
        if (!institutionLogs.containsKey(institution)) {
            return Collections.emptyList();
        }

        LocalDateTime start = LocalDateTime.parse(startTime, DATE_TIME_FORMATTER);
        LocalDateTime end = LocalDateTime.parse(endTime, DATE_TIME_FORMATTER);

        return institutionLogs.get(institution).values().stream()
                .flatMap(Collection::stream)
                .filter(log -> {
                    String timestamp = log.substring(1, log.indexOf("]")).trim();
                    try {
                        LocalDateTime logTime = LocalDateTime.parse(timestamp, DATE_TIME_FORMATTER);
                        return !logTime.isBefore(start) && !logTime.isAfter(end);
                    } catch (Exception e) {
                        return false;
                    }
                })
                .collect(Collectors.toList());
    }

    // Save all logs to files
    public synchronized void saveAllLogs() {
        institutionLogs.forEach((institution, typeLogs) -> {
            typeLogs.forEach((type, logs) -> {
                saveLogsByInstitutionAndType(institution, type, logs);
            });
        });
    }

    // Save logs for a specific institution and type
    private void saveLogsByInstitutionAndType(Institutions institution, MessageType type, List<String> logs) {
        String filePath = LOG_FILE_PATH + institution + "_" + type + ".log";

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            for (String log : logs) {
                writer.write(log + System.lineSeparator());
            }
            System.out.println("Logs saved for Institution: " + institution + ", Type: " + type);
        } catch (IOException e) {
            System.err.println("Error saving logs for Institution: " + institution + ", Type: " + type);
            e.printStackTrace();
        }
    }

    // Load all logs from files
    public synchronized void loadAllLogs() {
        File logDirectory = new File(LOG_FILE_PATH);
        if (!logDirectory.exists() || !logDirectory.isDirectory()) {
            System.err.println("Log directory not found. Skipping load operation.");
            return;
        }

        File[] logFiles = logDirectory.listFiles((dir, name) -> name.endsWith(".log"));
        if (logFiles == null || logFiles.length == 0) {
            System.err.println("No log files found. Skipping load operation.");
            return;
        }

        for (File logFile : logFiles) {
            String fileName = logFile.getName();
            String[] parts = fileName.replace(".log", "").split("_");
            if (parts.length != 2) {
                continue; // Skip invalid files
            }
            Institutions institution = Institutions.valueOf(parts[0]);
            MessageType type = MessageType.valueOf(parts[1]);

            try (BufferedReader reader = new BufferedReader(new FileReader(logFile))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    addLog(institution, type, line);
                }
            } catch (IOException e) {
                System.err.println("Error loading logs from file: " + logFile.getName());
                e.printStackTrace();
            }
        }
    }
}


