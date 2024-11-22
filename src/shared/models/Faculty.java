package shared.models;

import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.GenderIdentity;
import shared.enums.Institutions;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//design ScheduleManager and WaitListManager suitable for multiple institutes
public class Faculty extends User {
    private List<Course> assignedCourses; // Courses assigned to the faculty
    private List<Schedule> teachingSchedule;  // Faculty's schedule

    // Constructor
    public Faculty(String userID, String username, String firstName, String lastName, String password, Institutions institutionID, Department department, GenderIdentity genderIdentity) {
        super(userID, username,firstName, lastName, password, institutionID, department, AccountType.Faculty, genderIdentity);
        this.assignedCourses = new ArrayList<>();
        this.teachingSchedule = new ArrayList<>();
    }

    // Getter for assigned courses
    public List<Course> getAssignedCourses() {
        return assignedCourses;
    }

    // Add a course to the faculty's assigned courses
    public boolean assignCourse(Course course) {
        if (!assignedCourses.contains(course)) {
            assignedCourses.add(course);
            return true;
        }
        return false;
    }

    // Remove a course from the faculty's assigned courses
    public boolean unassignCourse(Course course) {
        return assignedCourses.remove(course);
    }

    // View class roster for a specific course
    public List<String> viewClassRoster(String sectionID) {
        for (Course course : assignedCourses) {
            for (CourseSection s : course.getSections()) {
                if (s.getSectionID().equals(sectionID)) {
                    return s.getClassRoster().getEnrolledStudents();
                }
            }

        }
        return new ArrayList<>(); // Return empty list if course not found
    }

    // Update the syllabus for a course
    public boolean updateSyllabus(Course course, String newSyllabus) {
        if (assignedCourses.contains(course)) {
            course.setNotes(newSyllabus); // Assuming `setNotes` can store syllabus
            return true;
        }
        return false;
    }

    // View faculty's teaching schedule
    public List<Schedule> viewSchedule() {
        return teachingSchedule;
    }

    // Add schedule for the faculty
    public boolean addTeachingSchedule(Schedule schedule) {
        if (teachingSchedule != null) {
            teachingSchedule.add(schedule);
            return true;
        }
        return false;
    }

    // Remove schedule for the faculty
    public boolean removeTeachingSchedule(Schedule schedule) {
        if (teachingSchedule != null) {
            teachingSchedule.remove(schedule);
            return true;
        }
        return false;
    }
}


