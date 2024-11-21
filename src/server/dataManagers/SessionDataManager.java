package server.dataManagers;

import server.ServerManager;

import java.io.*;
import java.util.*;

public class SessionDataManager {
    private static SessionDataManager instance;

    private SessionDataManager() {}

    public static synchronized SessionDataManager getInstance() {
        if (instance == null) {
            instance = new SessionDataManager();
        }
        return instance;
    }

    private static final String SESSION_FILE_PATH = ServerManager.SESSION_FILE_PATH;

    // Save session data to a file
    public void saveSessionData(Map<String, String> sessionData) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(SESSION_FILE_PATH))) {
            oos.writeObject(sessionData);
            System.out.println("Session data saved successfully.");
        } catch (Exception e) {
            System.err.println("Error saving session data.");
            e.printStackTrace();
        }
    }

    // Load session data from a file
    @SuppressWarnings("unchecked")
    public Map<String, String> loadSessionData() {
        Map<String, String> sessionData = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(SESSION_FILE_PATH))) {
            sessionData = (Map<String, String>) ois.readObject();
            System.out.println("Session data loaded successfully.");
        } catch (FileNotFoundException e) {
            System.err.println("Session file not found. Returning empty map.");
            sessionData = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading session data.");
            e.printStackTrace();
        }

        return sessionData;
    }
}

