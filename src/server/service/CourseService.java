package server.service;

import server.dataManagers.CoursesDataManager;
import shared.enums.*;
import shared.models.*;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public class CourseService {
    private static CourseService instance;
    private final CoursesDataManager coursesDataManager;

    // Constructor
    private CourseService() {
        this.coursesDataManager = CoursesDataManager.getInstance();
    }

    public static CourseService getInstance() {
        if (instance == null) {
            instance = new CourseService();
        }
        return instance;
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

    // Get the course containing a specific section ID
    public Course getCourseBySectionId(Institutions institution, String sectionID) {
        if (institution == null || sectionID == null) {
            System.err.println("Invalid institution or section ID.");
            return null;
        }
        Course course = coursesDataManager.getCourseBySectionID(institution, sectionID);
        if (course != null) {
            System.out.println("Course retrieved for section ID " + sectionID + ": " + course.getName());
        } else {
            System.err.println("Course not found for section ID: " + sectionID);
        }
        return course;
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

    // admin

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

    // student

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

        List<CourseSection> enrolledCourses = student.getEnrolledCourses();
        List<CourseSection> waitlistedCourses = student.getWaitlistedCourses();
        List<FinishedCourse> finishedCourses = student.getFinishedCourses();

        for (CourseSection c : enrolledCourses) {
            if (Objects.equals(c.getSectionID(), sectionID)) {
                return false;
            }
        }
        for (CourseSection c : waitlistedCourses) {
            if (Objects.equals(c.getSectionID(), sectionID)) {
                return false;
            }
        }
        for (FinishedCourse f : finishedCourses) {
            if (Objects.equals(f.getSectionID(), sectionID)) {
                return false;
            }
        }

        // add waitlist
        if (section.isFullyEnrolled()) {
            boolean addedToWaitlist = WaitlistService.getInstance().addToWaitlist(institution, student, sectionID);
            if (addedToWaitlist) {
                waitlistedCourses.add(section);
                return true;
            }
        } else {// add student to course class roster
            boolean enrolled = section.getClassRoster().addStudent(student);
            if (enrolled) {
                System.out.println("Student " + student.getUsername() + " enrolled in section: " + sectionID);
                enrolledCourses.add(section);
                return true;
            }
        }
        return false;
    }

    // Drop a student from a course section with student completed course flag
    public boolean dropCourse(Student student, String sectionID, boolean completed, Grade grade) {
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

        List<CourseSection> enrolledCourses = student.getEnrolledCourses();
        List<CourseSection> waitlistedCourses = student.getWaitlistedCourses();
        List<FinishedCourse> finishedCourses = student.getFinishedCourses();

        if (enrolledCourses.remove(section)) {
            section.getClassRoster().removeStudent(student);
            if (completed) {
                FinishedCourse finishedCourse = new FinishedCourse();
                finishedCourse.setSectionID(section.getSectionID());
                finishedCourse.setCourseID(CourseService.getInstance().getCourseByCourseID(institution, section.getSectionID()).getCourseID());
                finishedCourse.setGrade(grade);
                finishedCourses.add(finishedCourse);
            }
            System.out.println("Student " + student.getUsername() + " dropped section: " + sectionID);
            return true;
        } else if (waitlistedCourses.remove(section)) {
            WaitlistService.getInstance().getWaitlist(institution, section.getSectionID()).removeFromWaitlist(student);
            System.out.println("Student " + student.getUsername() + " dropped section: " + sectionID + "waitlist");
            return true;
        }
        return false;
    }

    // Add a course to the faculty's assigned courses
    public boolean assignCourse(Institutions institutionID,  Faculty faculty, CourseSection section) {
        List<CourseSection> assignedCourses = faculty.getAssignedCourses();
        if (!assignedCourses.contains(section)) {
            ScheduleService.getInstance().getSchedule(institutionID, section.getScheduleID()).setFacultyID(faculty.getUserId());
            assignedCourses.add(section);
            return true;
        }
        return false;
    }

    // Remove a course from the faculty's assigned courses
    public boolean unassignCourse(Institutions institutionID, Faculty faculty, CourseSection section) {
        List<CourseSection> assignedCourses = faculty.getAssignedCourses();
        if (assignedCourses.contains(section)) {
            ScheduleService.getInstance().getSchedule(institutionID, section.getScheduleID()).setFacultyID(null);
            assignedCourses.remove(section);
            return true;
        }
        return false;
    }

}