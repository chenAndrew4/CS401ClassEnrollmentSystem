package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;

import java.io.*;
import java.util.*;

public class CoursesDataManager {
	
}
//public class CourseDataManager {
//    private static CourseDataManager instance;
//
//    private CourseDataManager() {}
//
//    public static synchronized CourseDataManager getInstance() {
//        if (instance == null) {
//            instance = new CourseDataManager();
//        }
//        return instance;
//    }
//
//    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
//    private static final String FILE_SUFFIX = ServerManager.COURSES_DB_FILE_PATH_SUFFIX;
//
//    // Save the entire map of institution courses to their respective files
//    public void saveAllCourses(Map<Institutions, Map<String, Course>> institutionCourses) {
//        for (Map.Entry<Institutions, Map<String, Course>> entry : institutionCourses.entrySet()) {
//            Institutions institutionID = entry.getKey();
//            Map<String, Course> courses = entry.getValue();
//            saveCoursesByInstitution(institutionID, courses);
//        }
//    }
//
//    // Save courses for a specific institution
//    public void saveCoursesByInstitution(Institutions institutionID, Map<String, Course> courses) {
//        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
//
//        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
//            oos.writeObject(courses);
//            System.out.println("Courses saved successfully for institution: " + institutionID);
//        } catch (Exception e) {
//            System.err.println("Error saving courses for institution: " + institutionID);
//            e.printStackTrace();
//        }
//    }
//
//    // Load all institution courses from their files
//    public Map<Institutions, Map<String, Course>> loadAllCourses(List<Institutions> institutionIDs) {
//        Map<Institutions, Map<String, Course>> institutionCourses = new HashMap<>();
//        for (Institutions institutionID : institutionIDs) {
//            institutionCourses.put(institutionID, loadCoursesByInstitution(institutionID));
//        }
//        return institutionCourses;
//    }
//
//    // Load courses for a specific institution
//    @SuppressWarnings("unchecked")
//    public Map<String, Course> loadCoursesByInstitution(Institutions institutionID) {
//        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
//        Map<String, Course> courses = null;
//
//        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
//            courses = (Map<String, Course>) ois.readObject();
//            System.out.println("Courses loaded successfully for institution: " + institutionID);
//        } catch (FileNotFoundException e) {
//            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
//            courses = new HashMap<>();
//        } catch (Exception e) {
//            System.err.println("Error loading courses for institution: " + institutionID);
//            e.printStackTrace();
//        }
//
//        return courses;
//    }
//}



