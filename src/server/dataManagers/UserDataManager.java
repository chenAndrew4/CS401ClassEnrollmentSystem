package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.User;

import java.io.*;
import java.rmi.MarshalledObject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ArrayList;

public class UserDataManager {

    private static UserDataManager instance;

    private UserDataManager() {
        userMap = new HashMap<>();
        imported = new HashMap<>();
        modified = new HashMap<>();
    }

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.USERS_DB_FILE_PATH_SUFFIX;

    private Map<Institutions, Boolean> imported;
    private Map<Institutions, Boolean> modified;
    private Map<Institutions, Map<String, User>> userMap;

    // Check if data for an institution is imported, and import if not
    private void isImported(Institutions institutionID) {
        if (!imported.getOrDefault(institutionID, false)) {
            Map<String, User> users = loadUsersByInstitution(institutionID);
            userMap.put(institutionID, users);
            imported.put(institutionID, true);
            modified.put(institutionID, false);
        }
    }

    // Add a user for a specific institution
    public boolean addUserByInstitution(Institutions institutionID, User user) {
        isImported(institutionID);
        userMap.get(institutionID).put(user.getUsername(), user);
        modified.put(institutionID, true);
        return true;
    }

    // Retrieve a user for a specific institution
    public User getUserByInstitution(Institutions institutionID, String username) {
        isImported(institutionID);
        return userMap.get(institutionID).get(username);
    }

    // Retrieve all users for a specific institution
//    public List<User> getUsersByInstitution(Institutions institutionID) {
    public Map<String, User> getUsersByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.get(institutionID);
//        return new ArrayList<>(userMap.get(institutionID).values());
    }

    // Remove a user for a specific institution
    public boolean removeUserByInstitution(Institutions institutionID, String username) {
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
    public void updateUserByInstitutions(Institutions institutionID, User user) {
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
    private void saveAllUsers(Map<Institutions, Map<String, User>> institutionUsers) {
        if (institutionUsers == null || institutionUsers.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return; // Exit if the map is empty or null
        }
        for (Map.Entry<Institutions, Map<String, User>> entry : institutionUsers.entrySet()) {
            saveUsersByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save users for a specific institution
    private void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return; // Exit if the map is empty or null
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
    private Map<Institutions, Map<String, User>> loadAllUsers(List<Institutions> institutionIDs) {
        Map<Institutions, Map<String, User>> institutionUsers = new HashMap<>();
        for (Institutions institutionID : institutionIDs) {
            institutionUsers.put(institutionID, loadUsersByInstitution(institutionID));
        }
        return institutionUsers;
    }

    // Load users for a specific institution
    @SuppressWarnings("unchecked")
    private Map<String, User> loadUsersByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        Map<String, User> users = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (Map<String, User>) ois.readObject();
            System.out.println("Users loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
            users = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading users for institution: " + institutionID);
            e.printStackTrace();
        }

        return users;
    }

    // Import user data for a specific institution
    public void importDBByInstitution(Institutions institutionID) {
        isImported(institutionID);
    }

    // Commit user data for a specific institution to file
    public void commitDBByInstitution(Institutions institutionID) {
        isImported(institutionID);
        if (modified.containsKey(institutionID) && modified.get(institutionID)) {
            saveUsersByInstitution(institutionID, userMap.get(institutionID));
            modified.put(institutionID, false);
        }
    }

    public void commitDBByInstitution(Institutions institutionID, Map<String, User> map) {
        saveUsersByInstitution(institutionID, map);
    }

    // Commit user data for a specific institution to file
    public void commitDBAllUsers() {
        saveAllUsers(userMap);
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
