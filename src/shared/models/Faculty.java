package shared.models;

import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.GenderIdentity;
import shared.enums.Institutions;

import java.util.ArrayList;
import java.util.List;

//design ScheduleManager and WaitListManager suitable for multiple institutes
public class Faculty extends User {
    private List<CourseSection> assignedCourses; // Courses assigned to the faculty
    private List<Schedule> teachingSchedule;  // Faculty's schedule


    public Faculty(Institutions institutionID){
        super(institutionID);
        assignedCourses = new ArrayList<>();
        teachingSchedule = new ArrayList<>();
    }
    public Faculty(Faculty other){
        super(other);
        assignedCourses = new ArrayList<>(other.assignedCourses);
        teachingSchedule =  new ArrayList<>(other.teachingSchedule);
    }

    // Constructor
    public Faculty(String username, String firstName, String lastName, String password, Institutions institutionID, Department department, GenderIdentity genderIdentity, String phone, String address) {
        super(username,firstName, lastName, password, institutionID, department, AccountType.Faculty, genderIdentity, phone, address);
        this.assignedCourses = new ArrayList<>();
        this.teachingSchedule = new ArrayList<>();
    }

    // Getter for assigned course list
    public List<CourseSection> getAssignedCourses() {
        return assignedCourses;
    }

    // View class roster for a specific course, return a list of studentID
    public List<String> viewClassRoster(String sectionID) {
        for (CourseSection s : assignedCourses) {
            if (s.getSectionID().equals(sectionID)) {
                return s.getClassRoster().getEnrolledStudents();
            }
        }
        return new ArrayList<>();
    }

    // Update the syllabus for a course
    public boolean updateSyllabus(CourseSection section, String newSyllabus) {
        if (assignedCourses.contains(section)) {
            section.setNotes(newSyllabus);
            return true;
        }
        return false;
    }

    // View faculty's teaching schedule
    public List<Schedule> getSchedule() {
        return teachingSchedule;
    }

}


