package server.dataManagers;

import server.FileDataManager;
import server.ServerManager;
import shared.enums.Institutes;
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

    // Save the entire map of institute users to their respective files
    public void saveAllUsers(Map<Institutes, Map<String, User>> instituteUsers) {
        for (Map.Entry<Institutes, Map<String, User>> entry : instituteUsers.entrySet()) {
            Institutes instituteID = entry.getKey();
            Map<String, User> users = entry.getValue();
            saveUsersByInstitute(instituteID, users);
        }
    }

    // Save users for a specific institute
    public void saveUsersByInstitute(Institutes instituteID, Map<String, User> users) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully for institute: " + instituteID);
        } catch (Exception e) {
            System.err.println("Error saving users for institute: " + instituteID);
            e.printStackTrace();
        }
    }

    // Load all institute users from their files
    public Map<Institutes, Map<String, User>> loadAllUsers(List<Institutes> instituteIDs) {
        Map<Institutes, Map<String, User>> instituteUsers = new HashMap<>();
        for (Institutes instituteID : instituteIDs) {
            instituteUsers.put(instituteID, loadUsersByInstitute(instituteID));
        }
        return instituteUsers;
    }

    // Load users for a specific institute
    @SuppressWarnings("unchecked")
    public Map<String, User> loadUsersByInstitute(Institutes instituteID) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;
        Map<String, User> users = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (Map<String, User>) ois.readObject();
            System.out.println("Users loaded successfully for institute: " + instituteID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institute: " + instituteID + ". Returning empty map.");
            users = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading users for institute: " + instituteID);
            e.printStackTrace();
        }

        return users;
    }

}
