package shared.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class ClassRoster implements Serializable {
    private String sectionID;
    private List<String> enrolledStudents; // studentID list

    public ClassRoster() {
        enrolledStudents = new ArrayList<>();
    }

    public ClassRoster(ClassRoster other) {
        this.sectionID = other.sectionID;
        if (other.enrolledStudents == null || other.enrolledStudents.isEmpty()) {
            this.enrolledStudents = new ArrayList<>();
        } else {
            this.enrolledStudents = new ArrayList<>(other.enrolledStudents);
        }
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

	@Override
	public String toString() {
		return "ClassRoster {sectionID=" + sectionID + ", enrolledStudents=" + enrolledStudents + "}";
	}
    
    
}
