package server.dataManagers;

import server.ServerManager;
import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.Institutions;
import shared.models.Administrator;
import shared.models.Faculty;
import shared.models.Student;
import shared.models.User;

import java.io.*;
import java.util.*;

public class UserDataManager {
    private static UserDataManager instance;

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.USERS_DB_FILE_PATH_SUFFIX;

    private final Map<Institutions, Boolean> imported;
    private final Map<Institutions, Boolean> modified;
    private final Map<Institutions, Map<String, User>> userMap;

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

    private synchronized void isImported(Institutions institutionID) {
        if (!imported.getOrDefault(institutionID, false)) {
            Map<String, User> users = loadUsersByInstitution(institutionID);
            userMap.put(institutionID, users);
            imported.put(institutionID, true);
            modified.put(institutionID, false);
        }
    }

    public synchronized boolean addUserByInstitution(Institutions institutionID, User user) {
        isImported(institutionID);
        userMap.get(institutionID).put(user.getUserId(), user); // Use userId as key
        modified.put(institutionID, true);
        return true;
    }

    public synchronized User getUserByInstitution(Institutions institutionID, String userId) {
        isImported(institutionID);
        return new User(userMap.get(institutionID).get(userId)); // Retrieve by userId
    }

    public synchronized User getUserByInstitutionAndUserName(Institutions institutionID, String username) {
        isImported(institutionID);
        for (User u : userMap.get(institutionID).values()) {
            if (username.equals(u.getUsername())) {
                switch (u.getAccountType()){
                    case Student -> {
                        return new Student((Student) u);
                    }
                    case Faculty -> {
                        return new Faculty((Faculty) u);
                    }
                    case Administrator -> {
                        return new Administrator((Administrator) u);
                    }
                    default -> {
                        System.err.println("Error casting user for institution: " + institutionID);
                        Log.getInstance(ServerGUI.logTextArea).error("Error casting user for institution: ", institutionID, this.getClass());
                        throw new ClassCastException("Error casting user for institution: " + institutionID);
                    }
                }
            }
        }
        return null; // Retrieve by userId
    }

    public synchronized Map<String, User> getUsersByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.containsKey(institutionID)
                ? Collections.unmodifiableMap(new HashMap<>(userMap.get(institutionID)))
                : Collections.emptyMap();
    }

    public synchronized boolean removeUserByInstitution(Institutions institutionID, String userId) {
        isImported(institutionID);
        if (userMap.containsKey(institutionID)) {
            Map<String, User> curMap = userMap.get(institutionID);
            if (curMap.containsKey(userId)) {
                curMap.remove(userId); // Remove by userId
                modified.put(institutionID, true);
                return true;
            }
        }
        return false;
    }

    public synchronized void updateUserByInstitutions(Institutions institutionID, User user) {
        isImported(institutionID);
        if (userMap.containsKey(institutionID)) {
            Map<String, User> curMap = userMap.getOrDefault(institutionID,new HashMap<>());
            if (curMap.containsKey(user.getUserId())) {
                curMap.put(user.getUserId(), user); // Update by userId
                modified.put(institutionID, true);
            }
        }
    }

    public synchronized Set<String> getUserIDsByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.containsKey(institutionID)
                ? Collections.unmodifiableSet(userMap.get(institutionID).keySet())
                : Collections.emptySet();
    }

    public synchronized void saveAllUsers() {
        if (userMap == null || userMap.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return;
        }
        for (Map.Entry<Institutions, Map<String, User>> entry : userMap.entrySet()) {
            saveUsersByInstitution(entry.getKey(), entry.getValue());
        }
        userMap.clear();
    }

    private synchronized void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
        if (users == null || users.isEmpty()) {
            System.out.println("No data to save. Skipping save operation.");
            return;
        }
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            modified.put(institutionID, false);
            userMap.get(institutionID).clear();
            System.out.println("Users saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving users for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized Map<String, User> loadUsersByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
        Map<String, User> users = null;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            if (fis.available() == 0) {
                System.err.println("File is empty for institution: " + institutionID + ". Returning empty map.");
                return new HashMap<>();
            }

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

    public synchronized void commitDBByInstitution(Institutions institutionID) {
        isImported(institutionID);
        if (modified.getOrDefault(institutionID, false)) {
            saveUsersByInstitution(institutionID, userMap.get(institutionID));
            modified.put(institutionID, false);
        }
    }

    // Method to clear the DB file
    public synchronized void deleteDB() throws IOException {
        for (Institutions i : Institutions.values()) {
            String filePath = FILE_PREFIX + i.name() + File.separator + FILE_SUFFIX;
            File dbFile = new File(filePath);
            if (dbFile.exists()) {
                // Overwrite the file with an empty file
                FileWriter writer = new FileWriter(dbFile, false); // 'false' ensures overwrite
                writer.write(""); // Write nothing to clear the file
                writer.close();
            } else {
                // If the file doesn't exist, create an empty file
                dbFile.createNewFile();
            }
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
//        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
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
//        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
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
