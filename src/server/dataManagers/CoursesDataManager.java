package server.dataManagers;

import server.ServerManager;
import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;

import java.io.*;
import java.util.*;

// all method return a copy of data object to prevent directly data access
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

    // return a course map<ID, Course>
    public synchronized Map<String, Course> getAllCourses(Institutions institution) {
        if (institution == null) {
            return Collections.emptyMap();
        }
        ensureCoursesLoaded(institution);

        return Map.copyOf(institutionCourses.getOrDefault(institution, Collections.emptyMap()));
    }

    public synchronized Course getCourseByCourseID(Institutions institutionID, String courseID) {
        if (institutionID == null || courseID == null) {
            return null;
        }
        ensureCoursesLoaded(institutionID);

        Course course = institutionCourses.getOrDefault(institutionID, Collections.emptyMap()).get(courseID);
        return course != null ? new Course(course) : null;
    }

    public synchronized Course getCourseBySectionID(Institutions institutionID, String sectionID) {
        if (institutionID == null || sectionID == null) {
            return null;
        }
        ensureCoursesLoaded(institutionID);
        for (Course course : institutionCourses.get(institutionID).values()) {
            for (CourseSection s : course.getSections()) {
                if (s.getSectionID().equals(sectionID)) {
                    return new Course(course);
                }
            }
        }
        return null;
    }

    // return a courseID set
    public synchronized Set<String> getCourseIDsByInstitution(Institutions institution) {
        if (institution == null) {
            return Collections.emptySet();
        }
        ensureCoursesLoaded(institution);

        return Set.copyOf(institutionCourses.getOrDefault(institution, Collections.emptyMap()).keySet());
    }

    // return a sectionID set
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

    // return a courseSection
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



