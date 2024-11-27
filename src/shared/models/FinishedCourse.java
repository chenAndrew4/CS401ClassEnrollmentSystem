package shared.models;

import shared.enums.Grade;
import shared.enums.GradingType;

import java.util.Objects;

public class FinishedCourse {
    String courseID;
    String sectionID;
    String facultyID;
    Grade grade;

    public FinishedCourse() {
        this.courseID = null;
        this.sectionID = null;
        this.facultyID = null;
        this.grade =null;
    }

    public FinishedCourse(FinishedCourse other) {
        this.courseID = other.courseID;
        this.sectionID = other.sectionID;
        this.facultyID = other.facultyID;
        this.grade = other.grade;
    }

    public FinishedCourse(String courseID, String sectionID, String facultyID, Grade grade) {
        this.courseID = courseID;
        this.sectionID = sectionID;
        this.facultyID = facultyID;
        this.grade = grade;
    }

    public String getCourseID() {
        return courseID;
    }

    public void setCourseID(String courseID) {
        this.courseID = courseID;
    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public String getFacultyID() {
        return facultyID;
    }

    public void setFacultyID(String facultyID) {
        this.facultyID = facultyID;
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        FinishedCourse that = (FinishedCourse) o;
        return Objects.equals(courseID, that.courseID) && Objects.equals(sectionID, that.sectionID) && Objects.equals(facultyID, that.facultyID) && grade == that.grade;
    }

    @Override
    public int hashCode() {
        return Objects.hash(courseID, sectionID, facultyID, grade);
    }

    @Override
    public String toString() {
        return "FinishedCourse{" +
                "courseID='" + courseID + '\'' +
                ", sectionID='" + sectionID + '\'' +
                ", facultyID='" + facultyID + '\'' +
                ", grade=" + grade +
                '}';
    }
}

