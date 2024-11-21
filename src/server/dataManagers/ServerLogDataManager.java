package server.dataManagers;

import server.ServerManager;

import java.io.*;
import java.util.*;

public class ServerLogDataManager {
    private static ServerLogDataManager instance;

    private ServerLogDataManager() {}

    public static synchronized ServerLogDataManager getInstance() {
        if (instance == null) {
            instance = new ServerLogDataManager();
        }
        return instance;
    }

    private static final String LOG_FILE_PATH = ServerManager.LOGS_FILE_PATH;

    // Append a log entry to the log file
    public void appendLog(String logEntry) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(LOG_FILE_PATH, true))) {
            writer.write(logEntry + System.lineSeparator());
        } catch (IOException e) {
            System.err.println("Error writing log entry.");
            e.printStackTrace();
        }
    }

    // Retrieve all logs
    public List<String> getLogs() {
        List<String> logs = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(LOG_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                logs.add(line);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Log file not found. Returning empty list.");
        } catch (IOException e) {
            System.err.println("Error reading log file.");
            e.printStackTrace();
        }

        return logs;
    }
}
