package server;

import shared.models.Course;
import shared.models.CourseSection;

import java.util.HashMap;
import java.util.Map;

public class CourseManager {

    private static CourseManager instance;

    // Map to store courses with courseID as the key
    private Map<String, Course> courses;

    private CourseManager() {
        courses = new HashMap<>();
    }

    public static synchronized CourseManager getInstance() {
        if (instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    // Add a course to the manager
    public boolean addCourse(Course course) {
        if (course == null || courses.containsKey(course.getCourseID())) {
            return false; // Course already exists or invalid course
        }
        courses.put(course.getCourseID(), course);
        return true;
    }

    // Remove a course by its ID
    public boolean removeCourse(String courseID) {
        if (courseID == null || !courses.containsKey(courseID)) {
            return false; // Course does not exist
        }
        courses.remove(courseID);
        return true;
    }

    // Get a course by its ID
    public Course getCourseByID(String courseID) {
        return courses.get(courseID);
    }

    // Retrieve all courses
    public Map<String, Course> getAllCourses() {
        return new HashMap<>(courses); // Return a copy to ensure immutability
    }

    // Get a section by its ID
    public CourseSection getSectionById(String sectionID) {
        for (Course course : courses.values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return section;
                }
            }
        }
        return null; // Section not found
    }

    // Get the course containing a specific section ID
    public Course getCourseBySectionId(String sectionID) {
        for (Course course : courses.values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return course;
                }
            }
        }
        return null; // Course not found
    }

    // Commit courses to a database or file
    public void commitToDb() {
        // Logic to save courses to a file or database
        System.out.println("Courses committed to database.");
    }

    // Load courses from a database or file
    public void loadFromDb() {
        // Logic to load courses from a file or database
        System.out.println("Courses loaded from database.");
    }
}