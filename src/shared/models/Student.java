package shared.models;

import server.dataManagers.CoursesDataManager;
import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.GenderIdentity;
import shared.enums.Institutions;

import java.util.ArrayList;
import java.util.List;

public class Student extends User {
    private List<CourseSection> enrolledCourses;
    private List<CourseSection> waitlistedCourses;
    private List<CourseSection> finishedCourses; // List of finished courses

    public Student(Institutions institutionID) {
        super(institutionID);
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
        this.finishedCourses = new ArrayList<>();
    }

    public Student(Student other) {
        super(other);
        this.enrolledCourses = other.enrolledCourses;
        this.waitlistedCourses = other.waitlistedCourses;
        this.finishedCourses = other.finishedCourses;
    }

    public Student(String username, String firstName, String lastName, String password,
                   Institutions institution, AccountType accountType, Department department,
                   GenderIdentity genderIdentity) {
        super(username, firstName, lastName, password, institution, department, accountType, genderIdentity);
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
        this.finishedCourses = new ArrayList<>();
    }
    // Enroll in a course
    public boolean enrollInCourse(String sectionID) {
        if (enrolledCourses.contains(sectionID) || waitlistedCourses.contains(sectionID)) {
            return false; // Already enrolled or waitlisted
        }

        CourseSection section = CoursesDataManager.getInstance().getSectionById(getInstitutionID(), sectionID);

        if (section == null || finishedCourses.contains(section)) {
            return false; // Invalid sectionID or course already completed
        }

        if (section.isFullyEnrolled()) {
            boolean addedToWaitlist = section.getWaitlist().addToWaitlist(this);
            if (addedToWaitlist) {
                waitlistedCourses.add(section);
                return true; // Added to waitlist
            }
        } else {
            boolean enrolled = section.getClassRoster().addStudent(this);
            if (enrolled) {
                enrolledCourses.add(section);
                return true; // Successfully enrolled
            }
        }
        return false;
    }

    // Drop a course
    public void dropCourse(CourseSection section, boolean completed) {
        if (enrolledCourses.remove(section)) {
            section.getClassRoster().removeStudent(this);
            if (completed) {
                finishedCourses.add(section); // Add to finished courses if completed
            }
        } else if (waitlistedCourses.remove(section)) {
            section.getWaitlist().removeFromWaitlist(this);
        }
    }

    // Getters for finishedCourses
    public List<CourseSection> getFinishedCourses() {
        return finishedCourses;
    }

    public void addFinishedCourse(CourseSection section) {
        if (!finishedCourses.contains(section)) {
            finishedCourses.add(section);
        }
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
