package server.service;

import server.dataManagers.ServerLogDataManager;
import shared.enums.Institutions;
import shared.enums.MessageType;

import java.util.List;

public class LogService {
    private static LogService instance;
    private final ServerLogDataManager logDataManager;

    private LogService() {
        this.logDataManager = ServerLogDataManager.getInstance();
    }

    public static synchronized LogService getInstance() {
        if (instance == null) {
            instance = new LogService();
        }
        return instance;
    }

    // Save a log entry
    public synchronized void saveLog(Institutions institutionID, MessageType type, String logEntry) {
        logDataManager.addLog(institutionID, type, logEntry);
    }

    // Retrieve all logs
    public List<String> getAllLogs(Institutions institutionID) {
        return logDataManager.getAllLogs(institutionID);
    }

    // Retrieve logs by date
    public List<String> getLogsByDate(String date) {
        return logDataManager.getLogsByDate(date);
    }

    // Retrieve logs by date
    public List<String> getLogsByInstitutionIDAndDate(Institutions institutionID, String date) {
        return logDataManager.getLogsByInstitutionIDAndDate(institutionID, date);
    }

    // Retrieve logs by type
    public List<String> getLogsByType(Institutions institutionID, MessageType type) {
        return logDataManager.getLogsByType(institutionID, type);
    }

    // Retrieve logs by content
    public List<String> getLogsByContent(Institutions institutionID, String content) {
        return logDataManager.getLogsByContent(institutionID, content);
    }

    // Retrieve logs within a specific time range
    public List<String> getLogsByTimeRange(Institutions institutionID, String startTime, String endTime) {
        return logDataManager.getLogsByTimeRange(institutionID, startTime, endTime);
    }
}
