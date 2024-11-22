package shared.models;

import server.dataManagers.CoursesDataManager;
import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.Institutions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Student extends User implements Serializable {
    private List<CourseSection> enrolledCourses;  // List of section IDs for enrolled courses
    private List<CourseSection> waitlistedCourses;  // List of section IDs for waitlisted courses

    public Student(){
        super();
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
    }

    public Student(String userID, String username, String firstName, String lastName, String password, Institutions institution, AccountType accountType, Department department) {
        super(userID, username, firstName, lastName, password, institution, department,accountType);
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
        CourseSection section = CoursesDataManager.getInstance().getSectionById(getInstitutionID(),sectionID);

        if (section == null) {
            return false; // Invalid sectionID
        }

        if (section.isFullyEnrolled()) {
            // Add to waitlist if full
            boolean addedToWaitlist = section.getWaitlist().addToWaitlist(this);
            if (addedToWaitlist) {
                waitlistedCourses.add(section);
                return true; // Added to waitlist
            } else {
                return false; // Waitlist full
            }
        } else {
            // Enroll directly
            boolean enrolled = section.getClassRoster().addStudent(this);
            if (enrolled) {
                enrolledCourses.add(section);
                return true; // Successfully enrolled
            } else {
                return false; // Enrollment failed
            }
        }
    }

    // Drop a course
    public void dropCourse(CourseSection section) {
        if (enrolledCourses.remove(section)) {
            // Remove from enrolled courses
            CourseSection remvSection = CoursesDataManager.getInstance().getSectionById(getInstitutionID(),section.getSectionID());
            if (remvSection != null) {
                section.getClassRoster().removeStudent(this);
            }
        } else if (waitlistedCourses.remove(section)) {
            // Remove from waitlisted courses
            CourseSection remvSection = CoursesDataManager.getInstance().getSectionById(getInstitutionID(),section.getCourseID());
            if (remvSection != null) {
                section.getWaitlist().removeFromWaitlist(this);
            }
        }
    }

    // View schedule
    public List<Schedule> viewSchedule() {
//        List<Schedule> scheduleList = new ArrayList<>();
//        for (CourseSection section : enrolledCourses) {
//            Schedule schedule = section.getSchedule();
//            if (schedule != null) {
//                scheduleList.add(schedule);
//            }
//        }
//        return scheduleList;
        return null;
    }

    // Get position on waitlist
    public int getWaitlistPositions(String sectionID) {
        CourseSection section = CoursesDataManager.getInstance().getSectionById(getInstitutionID(),sectionID);
        if (section != null) {
            return section.getWaitlist().getPosition(this);
        }
        return -1; // Not on waitlist
    }

    // Getters for enrolledCourses and waitlistedCourses
    public List<CourseSection> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<CourseSection> getWaitlistedCourses() {
        return waitlistedCourses;
    }
}
