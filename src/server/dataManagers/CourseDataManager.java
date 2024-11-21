package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutes;
import shared.models.Course;

import java.io.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CourseDataManager {
    private static CourseDataManager instance;

    private CourseDataManager() {}

    public static synchronized CourseDataManager getInstance() {
        if (instance == null) {
            instance = new CourseDataManager();
        }
        return instance;
    }

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.COURSES_DB_FILE_PATH_SUFFIX;

    // Save the entire map of institute courses to their respective files
    public void saveAllCourses(Map<Institutes, Map<String, Course>> instituteCourses) {
        for (Map.Entry<Institutes, Map<String, Course>> entry : instituteCourses.entrySet()) {
            Institutes instituteID = entry.getKey();
            Map<String, Course> courses = entry.getValue();
            saveCoursesByInstitute(instituteID, courses);
        }
    }

    // Save courses for a specific institute
    public void saveCoursesByInstitute(Institutes instituteID, Map<String, Course> courses) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(courses);
            System.out.println("Courses saved successfully for institute: " + instituteID);
        } catch (Exception e) {
            System.err.println("Error saving courses for institute: " + instituteID);
            e.printStackTrace();
        }
    }

    // Load all institute courses from their files
    public Map<Institutes, Map<String, Course>> loadAllCourses(List<Institutes> instituteIDs) {
        Map<Institutes, Map<String, Course>> instituteCourses = new HashMap<>();
        for (Institutes instituteID : instituteIDs) {
            instituteCourses.put(instituteID, loadCoursesByInstitute(instituteID));
        }
        return instituteCourses;
    }

    // Load courses for a specific institute
    @SuppressWarnings("unchecked")
    public Map<String, Course> loadCoursesByInstitute(Institutes instituteID) {
        String filePath = FILE_PREFIX + instituteID + "/" + FILE_SUFFIX;
        Map<String, Course> courses = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            courses = (Map<String, Course>) ois.readObject();
            System.out.println("Courses loaded successfully for institute: " + instituteID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institute: " + instituteID + ". Returning empty map.");
            courses = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading courses for institute: " + instituteID);
            e.printStackTrace();
        }

        return courses;
    }
}
