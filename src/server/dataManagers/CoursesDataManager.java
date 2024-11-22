package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;

import java.io.*;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CoursesDataManager {

    private static CoursesDataManager instance;

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.COURSES_DB_FILE_PATH_SUFFIX;

    // In-memory storage for courses by institution
    private Map<Institutions, Map<String, Course>> institutionCourses;

    private CoursesDataManager() {
        institutionCourses = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllCourses);
    }

    public static synchronized CoursesDataManager getInstance() {
        if (instance == null) {
            instance = new CoursesDataManager();
        }
        return instance;
    }

    // Add a course for a specific institution
    public synchronized boolean addOrUpdateCourse(Institutions institution, Course course) {
        if (institution == null || course == null) {
            return false; // Invalid input
        }

        institutionCourses.putIfAbsent(institution, new HashMap<>());

        Map<String, Course> courses = institutionCourses.get(institution);

        courses.put(course.getCourseID(), course);
        return true;
    }

    // Remove a course by its ID for a specific institution
    public synchronized boolean removeCourse(Institutions institution, String courseID) {
        if (institution == null || courseID == null || !institutionCourses.containsKey(institution)) {
            return false; // Invalid input or institution not found
        }

        Map<String, Course> courses = institutionCourses.get(institution);
        if (!courses.containsKey(courseID)) {
            return false; // Course does not exist
        }

        courses.remove(courseID);
        return true;
    }

    // Get a course by its ID for a specific institution
    public synchronized Course getCourseByCourseID(Institutions institution, String courseID) {
        if (institution == null || courseID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }

        return new Course(institutionCourses.get(institution).get(courseID));
    }

    // Retrieve all courses for a specific institution
    public synchronized Map<String, Course> getAllCourses(Institutions institution) {
        if (institution == null || !institutionCourses.containsKey(institution)) {
            return Collections.emptyMap(); // Invalid input or institution not found
        }

        return new HashMap<>(institutionCourses.get(institution)); // Return a copy
    }

    // Get a section by its ID for a specific institution
    public synchronized CourseSection getSectionById(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }

        for (Course course : institutionCourses.get(institution).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return new CourseSection(section);
                }
            }
        }

        return null; // Section not found
    }

    // Get the course containing a specific section ID for a specific institution
    public synchronized Course getCourseBySectionId(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }
        for (Course course : institutionCourses.get(institution).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return new Course(course);
                }
            }
        }

        return null; // Course not found
    }

    // Save all courses to files
    public synchronized void saveAllCourses() {
        for (Map.Entry<Institutions, Map<String, Course>> entry : institutionCourses.entrySet()) {
            saveCoursesByInstitution(entry.getKey(), entry.getValue());
        }
    }

    // Save courses for a specific institution
    public synchronized void saveCoursesByInstitution(Institutions institutionID, Map<String, Course> courses) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(courses);
            System.out.println("Courses saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving courses for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    // Load all courses from files
    public synchronized void loadAllCourses(List<Institutions> institutionIDs) {
        for (Institutions institutionID : institutionIDs) {
            institutionCourses.put(institutionID, loadCoursesByInstitution(institutionID));
        }
    }

    // Load courses for a specific institution
    @SuppressWarnings("unchecked")
    public synchronized Map<String, Course> loadCoursesByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
        Map<String, Course> courses = null;

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            courses = (Map<String, Course>) ois.readObject();
            System.out.println("Courses loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
            courses = new HashMap<>();
        } catch (Exception e) {
            System.err.println("Error loading courses for institution: " + institutionID);
            e.printStackTrace();
        }

        return courses;
    }
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
//        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
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
//        String filePath = FILE_PREFIX + institutionID + "/" + FILE_SUFFIX;
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



