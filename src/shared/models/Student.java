package shared.models;

import server.dataManagers.CoursesDataManager;
import server.service.CourseService;
import server.service.WaitlistService;
import shared.enums.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Student extends User {
    private Grade grade;
    private List<CourseSection> enrolledCourses;
    private List<CourseSection> waitlistedCourses;
    private List<FinishedCourse> finishedCourses; // List of finished courses

    public Student(Institutions institutionID) {
        super(institutionID);
        this.grade = null;
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
        this.finishedCourses = new ArrayList<>();
    }

    public Student(Student other) {
        super(other);
        this.grade = other.grade;
        this.enrolledCourses = new ArrayList<>(other.enrolledCourses);
        this.waitlistedCourses = new ArrayList<>(other.waitlistedCourses);
        this.finishedCourses = new ArrayList<>(other.finishedCourses);
    }

    public Student(String username, String firstName, String lastName, String password,
                   Institutions institution, AccountType accountType, Department department,
                   GenderIdentity genderIdentity, String phone, String address, Grade grade) {
        super(username, firstName, lastName, password, institution, department, accountType, genderIdentity, phone, address);
        this.grade = grade;
        this.enrolledCourses = new ArrayList<>();
        this.waitlistedCourses = new ArrayList<>();
        this.finishedCourses = new ArrayList<>();
    }

    // Getters for finishedCourses
    public List<FinishedCourse> getFinishedCourses() {
        return finishedCourses;
    }

    public void addFinishedCourse(FinishedCourse finishedCourse) {
        if (!finishedCourses.contains(finishedCourse)) {
            finishedCourses.add(finishedCourse);
        }
    }

    public Grade getGrade() {
        return grade;
    }

    public void setGrade(Grade grade) {
        this.grade = grade;
    }

    public void setEnrolledCourses(List<CourseSection> enrolledCourses) {
        this.enrolledCourses = enrolledCourses;
    }

    public void setWaitlistedCourses(List<CourseSection> waitlistedCourses) {
        this.waitlistedCourses = waitlistedCourses;
    }

    public void setFinishedCourses(List<FinishedCourse> finishedCourses) {
        this.finishedCourses = finishedCourses;
    }

    // Getters for enrolledCourses and waitlistedCourses
    public List<CourseSection> getEnrolledCourses() {
        return enrolledCourses;
    }

    public List<CourseSection> getWaitlistedCourses() {
        return waitlistedCourses;
    }
}
