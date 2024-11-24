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

    public synchronized boolean addOrUpdateCourse(Institutions institution, Course course) {
        if (institution == null || course == null) {
            return false;
        }
        ensureCoursesLoaded(institution);

        institutionCourses.putIfAbsent(institution, new HashMap<>());
        institutionCourses.get(institution).put(course.getCourseID(), course);
        modified.put(institution, true); // Mark as modified
        return true;
    }

    public synchronized boolean removeCourse(Institutions institution, String courseID) {
        if (institution == null || courseID == null) {
            return false;
        }
        ensureCoursesLoaded(institution);

        Map<String, Course> courses = institutionCourses.get(institution);
        if (courses == null || !courses.containsKey(courseID)) {
            return false;
        }

        courses.remove(courseID);
        modified.put(institution, true); // Mark as modified
        return true;
    }

    public synchronized Course getCourseByCourseID(Institutions institution, String courseID) {
        if (institution == null || courseID == null) {
            return null;
        }
        ensureCoursesLoaded(institution);

        Course course = institutionCourses.getOrDefault(institution, Collections.emptyMap()).get(courseID);
        return course != null ? new Course(course) : null;
    }

    public synchronized Map<String, Course> getAllCourses(Institutions institution) {
        if (institution == null) {
            return Collections.emptyMap();
        }
        ensureCoursesLoaded(institution);

        return Collections.unmodifiableMap(new HashMap<>(institutionCourses.getOrDefault(institution, Collections.emptyMap())));
    }

    public synchronized Set<String> getCourseIDsByInstitution(Institutions institution) {
        if (institution == null) {
            return Collections.emptySet();
        }
        ensureCoursesLoaded(institution);

        return Collections.unmodifiableSet(new HashSet<>(institutionCourses.getOrDefault(institution, Collections.emptyMap()).keySet()));
    }

    public synchronized Set<String> getSectionIDsByInstitution(Institutions institution) {
        if (institution == null) {
            return Collections.emptySet();
        }
        ensureCoursesLoaded(institution);

        Set<String> sectionIDs = new HashSet<>();
        for (Course course : institutionCourses.getOrDefault(institution, Collections.emptyMap()).values()) {
            for (CourseSection section : course.getSections()) {
                sectionIDs.add(section.getSectionID());
            }
        }

        return Collections.unmodifiableSet(sectionIDs);
    }

    public synchronized CourseSection getSectionById(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null) {
            return null;
        }
        ensureCoursesLoaded(institution);

        for (Course course : institutionCourses.getOrDefault(institution, Collections.emptyMap()).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return new CourseSection(section);
                }
            }
        }
        return null;
    }

    public synchronized Course getCourseBySectionId(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null) {
            return null;
        }
        ensureCoursesLoaded(institution);

        for (Course course : institutionCourses.getOrDefault(institution, Collections.emptyMap()).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return new Course(course);
                }
            }
        }
        return null;
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



