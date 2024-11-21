package shared.models;

import server.CourseManager;
import shared.enums.AccountType;
import shared.enums.Institutes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private List<String> enrolledCourses;  // List of section IDs for enrolled courses
    private List<String> waitlistedCourses;  // List of section IDs for waitlisted courses

    public Student(){
        super();
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
    }

    public Student(String userID, String username, String firstName, String lastName, String password, Institutes institutes, AccountType accountType) {
        super(userID, username, firstName, lastName, password, institutes, accountType);
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
    }

    // Enroll in a course
    public boolean enrollInCourse(String sectionID) {
        // Check if already enrolled or waitlisted
        if (enrolledCourses.contains(sectionID) || waitlistedCourses.contains(sectionID)) {
            return false; // Already enrolled or waitlisted
        }

        // Fetch the course section (example implementation using a CourseManager)
        CourseSection section = CourseManager.getInstance().getSectionById(sectionID);

        if (section == null) {
            return false; // Invalid sectionID
        }

        if (section.isFullyEnrolled()) {
            // Add to waitlist if full
            boolean addedToWaitlist = section.getWaitlist().addToWaitlist(this);
            if (addedToWaitlist) {
                waitlistedCourses.add(sectionID);
                return true; // Added to waitlist
            } else {
                return false; // Waitlist full
            }
        } else {
            // Enroll directly
            boolean enrolled = section.getClassRoster().addStudent(this);
            if (enrolled) {
                enrolledCourses.add(sectionID);
                return true; // Successfully enrolled
            } else {
                return false; // Enrollment failed
            }
        }
    }

    // Drop a course
    public void dropCourse(String sectionID) {
        if (enrolledCourses.remove(sectionID)) {
            // Remove from enrolled courses
            CourseSection section = CourseManager.getInstance().getSectionById(sectionID);
            if (section != null) {
                section.getClassRoster().removeStudent(this);
            }
        } else if (waitlistedCourses.remove(sectionID)) {
            // Remove from waitlisted courses
            CourseSection section = CourseManager.getInstance().getSectionById(sectionID);
            if (section != null) {
                section.getWaitlist().removeFromWaitlist(this);
            }
        }
    }

    // View schedule
    public List<Course> viewSchedule() {
        List<Course> schedule = new ArrayList<>();
        for (String sectionID : enrolledCourses) {
            CourseSection section = CourseManager.getInstance().getSectionById(sectionID);
            if (section != null) {
                Course course = CourseManager.getInstance().getCourseBySectionId(sectionID);
                schedule.add(course);
            }
        }
        return schedule;
    }

    // Get position on waitlist
    public int getWaitlistPositions(String sectionID) {
        CourseSection section = CourseManager.getInstance().getSectionById(sectionID);
        if (section != null) {
            return section.getWaitlist().getPosition(this);
        }
        return -1; // Not on waitlist
    }

    // Getters for enrolledCourses and waitlistedCourses
    public List<String> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<String> getWaitlistedCourses() {
        return waitlistedCourses;
    }
}
