package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;

import java.io.*;
import java.util.*;

public class CoursesDataManager {

    private static CoursesDataManager instance;

    private static final String FILE_PREFIX = ServerManager.DB_FILE_PATH_PREFIX;
    private static final String FILE_SUFFIX = ServerManager.COURSES_DB_FILE_PATH_SUFFIX;

    private final Map<Institutions, Map<String, Course>> institutionCourses;
    private final Map<Institutions, Boolean> modified; // Track modified institutions

    private CoursesDataManager() {
        institutionCourses = new HashMap<>();
        modified = new HashMap<>();
        // Register save task for centralized data saving
        DataSaveManager.getInstance().registerSaveTask(this::saveAllCourses);
    }

    public static synchronized CoursesDataManager getInstance() {
        if (instance == null) {
            instance = new CoursesDataManager();
        }
        return instance;
    }

    private synchronized void ensureCoursesLoaded(Institutions institution) {
        if (!institutionCourses.containsKey(institution)) {
            institutionCourses.put(institution, loadCoursesByInstitution(institution));
            modified.putIfAbsent(institution, false);
        }
    }

    public synchronized void saveAllCourses() {
        if (institutionCourses.isEmpty()) {
            return;
        }
        for (Map.Entry<Institutions, Map<String, Course>> entry : institutionCourses.entrySet()) {
            if (Boolean.TRUE.equals(modified.get(entry.getKey()))) {
                saveCoursesByInstitution(entry.getKey(), entry.getValue());
                modified.put(entry.getKey(), false); // Reset modification flag
            }
        }
    }

    public synchronized void saveCoursesByInstitution(Institutions institutionID, Map<String, Course> courses) {
        if (courses == null || courses.isEmpty()) {
            return;
        }
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;

        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath))) {
            oos.writeObject(courses);
            System.out.println("Courses saved successfully for institution: " + institutionID);
        } catch (Exception e) {
            System.err.println("Error saving courses for institution: " + institutionID);
            e.printStackTrace();
        }
    }

    public synchronized void loadAllCourses() {
        for (Institutions institution : Institutions.values()) {
            ensureCoursesLoaded(institution);
        }
    }

    @SuppressWarnings("unchecked")
    public synchronized Map<String, Course> loadCoursesByInstitution(Institutions institutionID) {
        String filePath = FILE_PREFIX + institutionID + File.separator + FILE_SUFFIX;
        Map<String, Course> courses = new HashMap<>();

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath))) {
            courses = (Map<String, Course>) ois.readObject();
            System.out.println("Courses loaded successfully for institution: " + institutionID);
        } catch (FileNotFoundException e) {
            System.err.println("File not found for institution: " + institutionID + ". Returning empty map.");
        } catch (Exception e) {
            System.err.println("Error loading courses for institution: " + institutionID);
            e.printStackTrace();
        }
        return courses;
    }
}



