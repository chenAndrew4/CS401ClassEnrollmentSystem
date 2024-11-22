package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.User;

import java.io.*;
import java.rmi.MarshalledObject;
import java.util.*;

public class UserDataManager {

    private static UserDataManager instance;

    private UserDataManager() {
        userMap = new HashMap<>();
        imported = new HashMap<>();
        modified = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllUsers);
    }

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.USERS_DB_FILE_PATH_SUFFIX;

    private final Map<Institutions, Boolean> imported;
    private final Map<Institutions, Boolean> modified;
    private final Map<Institutions, Map<String, User>> userMap;

    // Check if data for an institution is imported, and import if not
    private synchronized void isImported(Institutions institutionID) {
        if (!imported.getOrDefault(institutionID, false)) {
            Map<String, User> users = loadUsersByInstitution(institutionID);
            userMap.put(institutionID, users);
            imported.put(institutionID, true);
            modified.put(institutionID, false);
        }
    }

    // Add a user for a specific institution
    public synchronized boolean addUserByInstitution(Institutions institutionID, User user) {
        isImported(institutionID);
        userMap.get(institutionID).put(user.getUsername(), user);
        modified.put(institutionID, true);
        return true;
    }

    // Retrieve a user for a specific institution
    public synchronized User getUserByInstitution(Institutions institutionID, String username) {
        isImported(institutionID);
        return userMap.get(institutionID).get(username);
    }

    // Retrieve all users for a specific institution (Defensive Copy)
    public synchronized Map<String, User> getUsersByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.containsKey(institutionID)
                ? Collections.unmodifiableMap(new HashMap<>(userMap.get(institutionID)))
                : Collections.emptyMap();
    }

    // Remove a user for a specific institution
    public synchronized boolean removeUserByInstitution(Institutions institutionID, String username) {
        isImported(institutionID);
        if (userMap.containsKey(institutionID)) {
            Map<String, User> curMap = userMap.get(institutionID);
            if (curMap.containsKey(username)) {
                curMap.remove(username);
                modified.put(institutionID, true);
                return true;
            }
        }
        return false;
    }

    // Update a user's details for a specific institution
    public synchronized void updateUserByInstitutions(Institutions institutionID, User user) {
        isImported(institutionID);
        if (userMap.containsKey(institutionID)) {
            Map<String, User> curMap = userMap.get(institutionID);
            if (curMap.containsKey(user.getUsername())) {
                curMap.put(user.getUsername(), user);
                modified.put(institutionID, true);
            }
        }
    }

    // Save all users for all institutions to their respective files
    public synchronized void saveAllUsers() {
        if (userMap == null || userMap.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return;
        }
        for (Map.Entry<Institutions, Map<String, User>> entry : userMap.entrySet()) {
            saveUsersByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save users for a specific institution
    private synchronized void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return;
        }
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving users for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load users for all institutions from their files
    private synchronized Map<Institutions, Map<String, User>> loadAllUsers(List<Institutions> institutionIDs) {
        Map<Institutions, Map<String, User>> institutionUsers = new HashMap<>();
        for (Institutions institutionID : institutionIDs) {
            institutionUsers.put(institutionID, loadUsersByInstitution(institutionID));
        }
        return institutionUsers;
    }

    // Load users for a specific institution
    @SuppressWarnings("unchecked")
    private synchronized Map<String, User> loadUsersByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        Map<String, User> users = null;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            // Check if the file is empty or contains only whitespace
            if (fis.available() == 0) {
                System.err.println("File is empty for institution: " + institutionID + ". Returning empty map.");
                return new HashMap<>();
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {
                String content = reader.lines().reduce("", String::concat).trim();
                if (content.isEmpty()) {
                    System.err.println("File contains only whitespace for institution: " + institutionID + ". Returning empty map.");
                    return new HashMap<>();
                }
            }

            // Deserialize the object if the file is not empty or whitespace
            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                users = (Map<String, User>) ois.readObject();
                System.out.println("Users loaded successfully for institution: " + institutionID);
            }
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
            users = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading users for institution: " + institutionID);
            e.printStackTrace();
            users = new HashMap<>();
        }
        return users;
    }

    // Commit user data for a specific institution to file
    public synchronized void commitDBByInstitution(Institutions institutionID) {
        isImported(institutionID);
        if (modified.getOrDefault(institutionID, false)) {
            saveUsersByInstitution(institutionID, userMap.get(institutionID));
            modified.put(institutionID, false);
        }
    }
}
//public class UserDataManager {
//    private static UserDataManager instance;
//
//    private UserDataManager() {}
//
//    public static synchronized UserDataManager getInstance() {
//        if (instance == null) {
//            instance = new UserDataManager();
//        }
//        return instance;
//    }
//
//    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
//    private static final String FILE_SUFFIX = ServerManager.USERS_DB_FILE_PATH_SUFFIX;
//
//    // Save the entire map of institution users to their respective files
//    public void saveAllUsers(Map<Institutions, Map<String, User>> institutionUsers) {
//        for (Map.Entry<Institutions, Map<String, User>> entry : institutionUsers.entrySet()) {
//            Institutions institutionID = entry.getKey();
//            Map<String, User> users = entry.getValue();
//            saveUsersByInstitution(institutionID, users);
//        }
//    }
//
//    // Save users for a specific institution
//    public void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
//        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            oos.writeObject(users);
//            System.out.println("Users saved successfully for institution: " + institutionID);
//        } catch (Exception e) {
//            System.err.println("Error saving users for institution: " + institutionID);
//            e.printStackTrace();
//        }
//    }
//
//    // Load all institution users from their files
//    public Map<Institutions, Map<String, User>> loadAllUsers(List<Institutions> institutionIDs) {
//        Map<Institutions, Map<String, User>> institutionUsers = new HashMap<>();
//        for (Institutions institutionID : institutionIDs) {
//            institutionUsers.put(institutionID, loadUsersByInstitution(institutionID));
//        }
//        return institutionUsers;
//    }
//
//    // Load users for a specific institution
//    @SuppressWarnings("unchecked")
//    public Map<String, User> loadUsersByInstitution(Institutions institutionID) {
//        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
//        Map<String, User> users = null;
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
//            users = (Map<String, User>) ois.readObject();
//            System.out.println("Users loaded successfully for institution: " + institutionID);
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
//            users = new HashMap<>();
//        } catch (Exception e) {
//            System.err.println("Error loading users for institution: " + institutionID);
//            e.printStackTrace();
//        }
//
//        return users;
//    }
//
//}
