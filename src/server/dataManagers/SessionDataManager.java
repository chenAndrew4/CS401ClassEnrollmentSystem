package server.dataManagers;

import server.ServerManager;
import server.gui.ServerGUI;
import server.utils.Log;

import java.io.*;
import java.util.*;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

public class SessionDataManager {
	private static Log log = Log.getInstance(ServerGUI.logTextArea);
    private static SessionDataManager instance;

    private final Map<String, String> sessionData;

    private static final String SESSION_FILE_PATH = ServerManager.SESSION_FILE_PATH;

    private SessionDataManager() {
        sessionData = new HashMap<>();
        loadSessionData(); // Automatically load data when the manager is initialized
        DataSaveManager.getInstance().registerSaveTask(this::saveSessionData);// Register save task for centralized data saving
    }

    public static synchronized SessionDataManager getInstance() {
        if (instance == null) {
            instance = new SessionDataManager();
        }
        return instance;
    }

    // Save session data to a file
    public synchronized void saveSessionData() {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE_PATH))) {
            oos.writeObject(new HashMap<>(sessionData)); // Write a copy to preserve encapsulation
            log.println("SessionDataManager: Session data saved successfully.");
        } catch (Exception e) {
            log.exception("SessionDataManager: Error saving session data.");
            e.printStackTrace();
        }
    }

    // Load session data from a file
    @SuppressWarnings("unchecked")
    private synchronized void loadSessionData() {
        File file = new File(SESSION_FILE_PATH);

        // Check if the file exists and is non-empty
        if (!file.exists() || file.length() == 0) {
            log.warn("SessionDataManager: Session file not found or empty. Initializing with empty data.");
            sessionData.clear();
            return;
        }

        // Attempt to load session data from file
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(file))) {
            Object loadedData = ois.readObject();

            // Validate the loaded data
            if (loadedData instanceof Map) {
                sessionData.clear();
                sessionData.putAll((Map<String, String>) loadedData);
                log.println("SessionDataManager: Session data loaded successfully.");
                //toLog();
            } else {
                throw new IOException("Invalid data format in session file.");
            }
        } catch (EOFException e) {
            log.exception("SessionDataManager: Session file is empty or incomplete. Initializing with empty data.");
            sessionData.clear();
        } catch (FileNotFoundException e) {
            log.exception("SessionDataManager: Session file not found. Initializing with empty data.");
            sessionData.clear();
        } catch (Exception e) {
            log.exception("SessionDataManager: Error loading session data: " + e.toString());
            e.printStackTrace();
            sessionData.clear();
        }
    }

    // Retrieve session data for a specific user
    public synchronized String getSessionData(String userId) {
        return sessionData.get(userId);
    }

    // Add or update a session for a user
    public synchronized void addOrUpdateSession(String userId, String sessionInfo) {
        sessionData.put(userId, sessionInfo);
    }

    // Remove a session for a user
    public synchronized void removeSession(String userId) {
        sessionData.remove(userId);
    }

    // Retrieve all session data
    public synchronized Map<String, String> getAllSessionData() {
        return Map.copyOf(sessionData);
    }
    
    public synchronized void toLog() {
    	sessionData.forEach((k, v) -> log.println("SessionDataManager: " + k + ": " + v));
    }
}


