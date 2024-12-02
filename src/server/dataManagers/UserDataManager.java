package server.dataManagers;

import server.Server;
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
	private static Log log;
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
        
        log = Log.getInstance(ServerGUI.logTextArea);
    }

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    private synchronized void isImported(Institutions institutionID) {
    	log.debug("isImported: institutionID = " + institutionID);
        if (!imported.getOrDefault(institutionID, false)) {
            Map<String, User> users = loadUsersByInstitution(institutionID);
            userMap.put(institutionID, users);
            imported.put(institutionID, true);
            modified.put(institutionID, false);
        }
    }

    public synchronized boolean addUserByInstitution(Institutions institutionID, User user) {
    	log.debug("addUserByInstitution: institutionID = " + institutionID);
        isImported(institutionID);
        userMap.get(institutionID).put(user.getUserId(), user); // Use userId as key
        modified.put(institutionID, true);
        return true;
    }

    public synchronized User getUserByInstitution(Institutions institutionID, String userId) {
        isImported(institutionID);
        User u = userMap.get(institutionID).get(userId);
        if (u == null) {
            return null;
        }
        return returnUser(institutionID, u);
    }

    private User returnUser(Institutions institutionID, User u) {
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
                log.error("UserDataManager: Error casting user for institution: " + institutionID + " " + this.getClass().toString());
                throw new ClassCastException("Error casting user for institution: " + institutionID);
            }
        }
    }

    public synchronized User getUserByInstitutionAndUserName(Institutions institutionID, String username) {
        isImported(institutionID);
        for (User u : userMap.get(institutionID).values()) {
            if (username.equals(u.getUsername())) {
                return returnUser(institutionID, u);
            }
        }
        return null; // Retrieve by userId
    }

    public synchronized Map<String, User> getUsersByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.containsKey(institutionID)
                ? Map.copyOf(userMap.get(institutionID))
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

    public synchronized boolean updateUserByInstitutions(Institutions institutionID, User user) {
        isImported(institutionID);
        if (userMap.containsKey(institutionID)) {
            Map<String, User> curMap = userMap.getOrDefault(institutionID,new HashMap<>());
            if (curMap.containsKey(user.getUserId())) {
                curMap.put(user.getUserId(), user); // Update by userId
                modified.put(institutionID, true);
                return true;
            } else {
            	log.error("UserDataManager: User " + user.getUsername() + " does not exist.");
            	return false;
            }
        } else {
        	log.error("UserDataManager: institutionID " + institutionID + " does not exist.");
        	return false;
        }
    }

    public synchronized Set<String> getUserIDsByInstitution(Institutions institutionID) {
        isImported(institutionID);
        return userMap.containsKey(institutionID)
                ? Set.copyOf(userMap.get(institutionID).keySet())
                : new HashSet<>();
    }

    public synchronized void saveAllUsers() {
        if (userMap == null || userMap.isEmpty()) {
            log.warn("UserDataManager: No data to save. Skipping save operation.");
            return;
        }
        for (Map.Entry<Institutions, Map<String, User>> entry : userMap.entrySet()) {
            saveUsersByInstitution(entry.getKey(), entry.getValue());
        }
        userMap.clear();
    }

    private synchronized void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
        if (users == null || users.isEmpty()) {
            log.warn("UserDataManager: No data to save. Skipping save operation.");
            return;
        }
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            modified.put(institutionID, false);
            userMap.get(institutionID).clear();
            log.println("UserDataManager: Users saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            log.exception("UserDataManager: Error saving users for institution: " + institutionID + e.toString());
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private synchronized Map<String, User> loadUsersByInstitution(Institutions institutionID) {
    	log.debug("loadUsersByInstitution: institutionID = " + institutionID);
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
        Map<String, User> users = null;

        try (FileInputStream fis = new FileInputStream(filePath)) {
            if (fis.available() == 0) {
                log.warn("UserDataManager: File is empty for institution: " + institutionID + ". Returning empty map.");
                return new HashMap<>();
            }

            try (ObjectInputStream ois = new ObjectInputStream(fis)) {
                users = (Map<String, User>) ois.readObject();
                log.println("UserDataManager: Users loaded successfully for institution: " + institutionID);
            }
        } catch (FileNotFoundException e) {
            log.exception("UserDataManager: File not found for institution: " + institutionID + ". Returning empty map.");
            users = new HashMap<>();
        } catch (Exception e) {
            log.exception("UserDataManager: Error loading users for institution: " + institutionID);
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