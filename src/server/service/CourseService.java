package server.service;

import server.dataManagers.CoursesDataManager;
import shared.enums.Institutions;
import shared.models.Course;
import shared.models.CourseSection;
import shared.models.Student;

import java.util.List;
import java.util.Map;

public class CourseService {

    private final CoursesDataManager coursesDataManager;

    // Constructor
    public CourseService() {
        this.coursesDataManager = CoursesDataManager.getInstance();
    }

    // Add or update a course
    public boolean addOrUpdateCourse(Institutions institution, Course course) {
        if (institution == null || course == null) {
            System.err.println("Invalid institution or course.");
            return false;
        }

        boolean result = coursesDataManager.addOrUpdateCourse(institution, course);
        if (result) {
            System.out.println("Course added/updated successfully: " + course.getCourseID());
        } else {
            System.err.println("Failed to add/update course: " + course.getCourseID());
        }
        return result;
    }

    // Remove a course by course ID
    public boolean removeCourse(Institutions institution, String courseID) {
        if (institution == null || courseID == null) {
            System.err.println("Invalid institution or course ID.");
            return false;
        }

        boolean result = coursesDataManager.removeCourse(institution, courseID);
        if (result) {
            System.out.println("Course removed successfully: " + courseID);
        } else {
            System.err.println("Failed to remove course: " + courseID);
        }
        return result;
    }

    // Get a course by course ID
    public Course getCourseByCourseID(Institutions institution, String courseID) {
        if (institution == null || courseID == null) {
            System.err.println("Invalid institution or course ID.");
            return null;
        }

        Course course = coursesDataManager.getCourseByCourseID(institution, courseID);
        if (course != null) {
            System.out.println("Course retrieved: " + course.getCourseID());
        } else {
            System.err.println("Course not found: " + courseID);
        }
        return course;
    }

    // Get all courses for a specific institution
    public Map<String, Course> getAllCourses(Institutions institution) {
        if (institution == null) {
            System.err.println("Invalid institution.");
            return Map.of(); // Return an empty map
        }

        Map<String, Course> courses = coursesDataManager.getAllCourses(institution);
        System.out.println("Retrieved " + courses.size() + " courses for institution: " + institution);
        return courses;
    }

    // Get a course section by section ID
    public CourseSection getSectionById(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null) {
            System.err.println("Invalid institution or section ID.");
            return null;
        }

        CourseSection section = coursesDataManager.getSectionById(institution, sectionID);
        if (section != null) {
            System.out.println("Section retrieved: " + section.getSectionID());
        } else {
            System.err.println("Section not found: " + sectionID);
        }
        return section;
    }

    // Get the course containing a specific section ID
    public Course getCourseBySectionId(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null) {
            System.err.println("Invalid institution or section ID.");
            return null;
        }

        Course course = coursesDataManager.getCourseBySectionId(institution, sectionID);
        if (course != null) {
            System.out.println("Course retrieved for section ID " + sectionID + ": " + course.getCourseID());
        } else {
            System.err.println("Course not found for section ID: " + sectionID);
        }
        return course;
    }

    // Enroll a student in a course section
    public boolean enrollInCourse(Student student, String sectionID) {
        if (student == null || sectionID == null) {
            System.err.println("Invalid student or section ID.");
            return false;
        }

        Institutions institution = student.getInstitutionID();
        CourseSection section = coursesDataManager.getSectionById(institution, sectionID);

        if (section == null) {
            System.err.println("Section not found: " + sectionID);
            return false;
        }

        boolean enrolled = student.enrollInCourse(sectionID);
        if (enrolled) {
            System.out.println("Student " + student.getUsername() + " enrolled in section: " + sectionID);
        } else {
            System.err.println("Failed to enroll student " + student.getUsername() + " in section: " + sectionID);
        }

        return enrolled;
    }

    // Drop a student from a course section
    public boolean dropCourse(Student student, String sectionID, boolean completed) {
        if (student == null || sectionID == null) {
            System.err.println("Invalid student or section ID.");
            return false;
        }

        Institutions institution = student.getInstitutionID();
        CourseSection section = coursesDataManager.getSectionById(institution, sectionID);

        if (section == null) {
            System.err.println("Section not found: " + sectionID);
            return false;
        }

        student.dropCourse(section, completed);
        System.out.println("Student " + student.getUsername() + " dropped section: " + sectionID);
        return true;
    }


    // Save all courses
    public void saveAllCourses() {
        coursesDataManager.saveAllCourses();
        System.out.println("All courses saved successfully.");
    }

    // Load all courses for a list of institutions
    public void loadAllCourses(List<Institutions> institutionIDs) {
        if (institutionIDs == null || institutionIDs.isEmpty()) {
            System.err.println("Invalid institution list.");
            return;
        }

        coursesDataManager.loadAllCourses();
        System.out.println("Courses loaded for institutions: " + institutionIDs);
    }
}
