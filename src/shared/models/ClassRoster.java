package shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassRoster implements Serializable {
    private String sectionID;
    private List<String> enrolledStudents; // studentID list

    public ClassRoster() {
    }

    // Copy constructor
    public ClassRoster(ClassRoster other) {
        this.sectionID = other.sectionID;
        this.enrolledStudents = new ArrayList<>(other.enrolledStudents); // Deep copy of the list
    }

    public ClassRoster(String sectionID) {
        this.sectionID = sectionID;
        enrolledStudents = new ArrayList<>();
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public List<String> getEnrolledStudents() {
        return enrolledStudents;
    }

    public void setEnrolledStudents(List<String> enrolledStudents) {
        this.enrolledStudents = enrolledStudents;
    }

    public int getEnrollmentCount() {
        return enrolledStudents.size();
    }

    public boolean addStudent(Student student) {
        return enrolledStudents.add(student.getUserId());
    }

    public boolean removeStudent(Student student) {
        return enrolledStudents.remove(student.getUserId());
    }
}
