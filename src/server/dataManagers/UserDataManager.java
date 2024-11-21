package server.dataManagers;

import server.FileDataManager;
import server.ServerManager;
import shared.enums.Institutions;
import shared.models.User;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UserDataManager {
    private static UserDataManager instance;

    private UserDataManager() {}

    public static synchronized UserDataManager getInstance() {
        if (instance == null) {
            instance = new UserDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.USERS_DB_FILE_PATH_SUFFIX;

    // Save the entire map of institution users to their respective files
    public void saveAllUsers(Map<Institutions, Map<String, User>> institutionUsers) {
        for (Map.Entry<Institutions, Map<String, User>> entry : institutionUsers.entrySet()) {
            Institutions institutionID = entry.getKey();
            Map<String, User> users = entry.getValue();
            saveUsersByInstitution(institutionID, users);
        }
    }

    // Save users for a specific institution
    public void saveUsersByInstitution(Institutions institutionID, Map<String, User> users) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving users for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all institution users from their files
    public Map<Institutions, Map<String, User>> loadAllUsers(List<Institutions> institutionIDs) {
        Map<Institutions, Map<String, User>> institutionUsers = new HashMap<>();
        for (Institutions institutionID : institutionIDs) {
            institutionUsers.put(institutionID, loadUsersByInstitution(institutionID));
        }
        return institutionUsers;
    }

    // Load users for a specific institution
    @SuppressWarnings("unchecked")
    public Map<String, User> loadUsersByInstitution(Institutions institutionID) {
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

}
