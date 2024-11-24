package shared.models;

import server.dataManagers.CoursesDataManager;
import shared.enums.*;

import java.util.ArrayList;
import java.util.List;

public class Administrator extends User {
    private List<User> managedUsers; // All users managed by the administrator

    public Administrator(Institutions institutionID) {
        super(institutionID);
        this.managedUsers = new ArrayList<>();
    }

    public Administrator(Administrator other) {
        super(other);
        this.managedUsers = other.managedUsers;
    }


    // Constructor
    public Administrator(String username,String firstName, String lastName, String password, Institutions institutionID, Department department, GenderIdentity genderIdentity) {
        super(username, firstName, lastName, password, institutionID, department,AccountType.Administrator, genderIdentity);
        this.managedUsers = new ArrayList<>();
    }

    // Add a student
    public boolean addStudent(Student student) {
        if (!managedUsers.contains(student)) {
            managedUsers.add(student);
            return true;
        }
        return false;
    }

    // Add a faculty
    public boolean addFaculty(Faculty faculty) {
        if (!managedUsers.contains(faculty)) {
            managedUsers.add(faculty);
            return true;
        }
        return false;
    }

    // Assign a faculty to a course
    public boolean assignCourseToFaculty(String courseID, String sectionID, String facultyID) {
        Course course = CoursesDataManager.getInstance().getCourseByCourseID(getInstitutionID(), courseID);
        if (course != null) {
            for (CourseSection s : course.getSections()) {
                if (s.getSectionID().equals(sectionID)) {
//                    s.setInstructor(facultyID);
                    return true;
                }
            }

        }
        return false;
    }


    // Create a course
    public Course createCourse(String courseID, String name, String description, String notes, LevelType level, AcademicProgramType academicProgramType, float units, Department department) {
        Institutions institutionID = this.getInstitutionID(); // Retrieve the administrator's institution
        Course newCourse = new Course(courseID, name, description,institutionID, notes, level, academicProgramType, units, department);

        // Add the course to the institution's course manager
        boolean success = CoursesDataManager.getInstance().addOrUpdateCourse(institutionID, newCourse);
        if (success) {
            System.out.println("Course created successfully for institution: " + institutionID);
            return newCourse;
        } else {
            System.err.println("Failed to create course. Course with ID " + courseID + " may already exist.");
            return null;
        }
    }

    // Delete a course
    public boolean deleteCourse(String courseID) {
        Institutions institutionID = this.getInstitutionID(); // Retrieve the administrator's institution

        // Attempt to remove the course from the institution's course manager
        boolean success = CoursesDataManager.getInstance().removeCourse(institutionID, courseID);
        if (success) {
            System.out.println("Course deleted successfully for institution: " + institutionID);
        } else {
            System.err.println("Failed to delete course. Course with ID " + courseID + " may not exist in institution: " + institutionID);
        }
        return success;
    }


    // Update an existing schedule
    public boolean updateSchedule(String courseID, Schedule updatedSchedule) {
        Course course = CoursesDataManager.getInstance().getCourseByCourseID(getInstitutionID(), courseID);
        if (course != null) {
            course.getSections().forEach(section -> {
                if (section.getScheduleID().equals(updatedSchedule.getScheduleID())) {
//                    section.setScheduleID(updatedSchedule);
                }
            });
            return true;
        }
        return false;
    }

    // Delete a schedule from a course
    public boolean deleteSchedule(String courseID, String scheduleID) {
        Course course = CoursesDataManager.getInstance().getCourseByCourseID(getInstitutionID(), courseID);
        if (course != null) {
            course.getSections().forEach(section -> {
                if (section.getScheduleID().equals(scheduleID)) {
//                    section.setSchedule(null); // Remove the schedule
                }
            });
            return true;
        }
        return false;
    }

//    // Generate a report
//    public Report generateReport(ReportType reportType) {
//        // Logic to generate a report based on the report type
//        System.out.println("Generating " + reportType + " report...");
//        return new Report(reportType, getInstitution());
//    }
}

