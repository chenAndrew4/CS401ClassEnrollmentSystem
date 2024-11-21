package server;

import shared.enums.AccountType;
import shared.enums.Institutes;
import shared.models.Course;
import shared.models.User;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileDataManager {
    private static FileDataManager instance;

    private FileDataManager() {}

    public static synchronized FileDataManager getInstance() {
        if (instance == null) {
            instance = new FileDataManager();
        }
        return instance;
    }

    // Save users to their respective institute's database file
    public void saveUsersByInstitute(Institutes instituteID, List<User> users) {
        String filePath = ServerManager.DB_FILE_PATH_PREFIX + instituteID + "/" + ServerManager.USERS_DB_FILE_PATH_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(users);
            System.out.println("Users saved successfully for institute: " + instituteID);
        } catch (Exception e) {
            System.err.println("Error saving users for institute: " + instituteID);
            e.printStackTrace();
        }
    }

    // Load users from a specific institute's database file
    @SuppressWarnings("unchecked")
    public List<User> loadUsersByInstitute(Institutes instituteID) {
        String filePath = ServerManager.DB_FILE_PATH_PREFIX + instituteID + "/" + ServerManager.USERS_DB_FILE_PATH_SUFFIX;
        List<User> users = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            users = (List<User>) ois.readObject();
            System.out.println("Users loaded successfully for institute: " + instituteID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institute: " + instituteID + ". Returning empty list.");
            users = new ArrayList<>();
        } catch (Exception e) {
            System.err.println("Error loading users for institute: " + instituteID);
            e.printStackTrace();
        }

        return users;
    }

//    @SuppressWarnings("unchecked")
//    public List<User> loadUsers(String filePath) throws IOException {
//        ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));
//        try {
//            return (List<User>) ois.readObject();
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//
//    public void saveUser(List<User> users, String filePath) {
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            oos.writeObject(users);
//            System.out.println("Users saved successfully.");
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }

    @SuppressWarnings("unchecked")
    public List<Course> loadCourses(String filePath) {
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            return (List<Course>) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public void saveCourses(List<Course> courses, String filePath) {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(courses);
            System.out.println("Courses saved successfully.");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }





}
