package server;

import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;

import java.util.HashMap;
import java.util.Map;

import java.util.*;

public class CourseManager {

    private static CourseManager instance;

    // Map to store courses per institution, with the institution as the key
    private Map<Institutions, Map<String, Course>> institutionCourses;

    private CourseManager() {
        institutionCourses = new HashMap<>();
    }

    public static synchronized CourseManager getInstance() {
        if (instance == null) {
            instance = new CourseManager();
        }
        return instance;
    }

    // Add a course for a specific institution
    public boolean addCourse(Institutions institution, Course course) {
        if (institution == null || course == null) {
            return false; // Invalid input
        }

        institutionCourses.putIfAbsent(institution, new HashMap<>());

        Map<String, Course> courses = institutionCourses.get(institution);
        if (courses.containsKey(course.getCourseID())) {
            return false; // Course already exists
        }

        courses.put(course.getCourseID(), course);
        return true;
    }

    // Remove a course by its ID for a specific institution
    public boolean removeCourse(Institutions institution, String courseID) {
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
    public Course getCourseByCourseID(Institutions institution, String courseID) {
        if (institution == null || courseID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }

        return institutionCourses.get(institution).get(courseID);
    }

    // Retrieve all courses for a specific institution
    public Map<String, Course> getAllCourses(Institutions institution) {
        if (institution == null || !institutionCourses.containsKey(institution)) {
            return Collections.emptyMap(); // Invalid input or institution not found
        }

        return new HashMap<>(institutionCourses.get(institution)); // Return a copy
    }

    // Get a section by its ID for a specific institution
    public CourseSection getSectionById(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }

        for (Course course : institutionCourses.get(institution).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return section;
                }
            }
        }

        return null; // Section not found
    }

    // Get the course containing a specific section ID for a specific institution
    public Course getCourseBySectionId(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null || !institutionCourses.containsKey(institution)) {
            return null; // Invalid input or institution not found
        }

        for (Course course : institutionCourses.get(institution).values()) {
            for (CourseSection section : course.getSections()) {
                if (section.getSectionID().equals(sectionID)) {
                    return course;
                }
            }
        }

        return null; // Course not found
    }

    // Commit courses for all institutions to a database or file
    public void commitToDb() {
        for (Map.Entry<Institutions, Map<String, Course>> entry : institutionCourses.entrySet()) {
            Institutions institution = entry.getKey();
            Map<String, Course> courses = entry.getValue();

            // Logic to save courses for this institution
            System.out.println("Courses for " + institution + " committed to database.");
        }
    }

    // Load courses for all institutions from a database or file
    public void loadFromDb() {
        // Logic to load courses for each institution
        System.out.println("Courses loaded from database.");
    }
}
