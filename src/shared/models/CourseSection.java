package shared.models;

import server.service.WaitlistService;
import shared.enums.*;
import shared.utils.IDGenerator;

import java.io.Serializable;

public class CourseSection implements Serializable {
    private String sectionID;     // Unique identifier for each section
//    private String courseID;
    private int enrollmentLimit;  // Max students for this section
    private String waitlistID;    // Section's waitlist
    private ClassRoster classRoster;  // Students enrolled in the section
    private GradingType grading;
    private InstructionModeType instructionMode;
    private String scheduleID;    // Schedule for this section

    public CourseSection(Institutions institutionID, String courseID) {
        this.sectionID = IDGenerator.getInstance().generateUniqueSectionID(institutionID,courseID);
    }

    // Copy constructor
    public CourseSection(CourseSection other) {
        this.sectionID = other.sectionID;
        this.enrollmentLimit = other.enrollmentLimit;
        this.waitlistID = other.waitlistID; // Assuming WaitList has a copy constructor
        this.classRoster = other.classRoster != null ? new ClassRoster(other.classRoster) : null;
        this.grading = other.grading;
        this.instructionMode = other.instructionMode;
        this.scheduleID = other.scheduleID;
    }

    public String getScheduleID() {
        return scheduleID;
    }

    public void setScheduleID(String scheduleID) {
        this.scheduleID = scheduleID;
    }

    public String getwaitlistID() {
        return waitlistID;
    }

    public boolean addStudent(Institutions institutionID, Student student) {
        if (classRoster.getEnrollmentCount() < enrollmentLimit) {
            classRoster.addStudent(student);
            return true;
        } else {
            WaitList cur = WaitlistService.getInstance().getWaitlist(institutionID, sectionID);
            cur.addToWaitlist(student);
            return WaitlistService.getInstance().addOrUpdateWaitlist(institutionID, sectionID, cur);
        }
    }

    // Drop a student from the section
    public boolean dropStudent(Institutions institutionID, Student student) {
        if (classRoster.removeStudent(student)) {
            // Notify next student on waitlist, if applicable
            WaitList cur = WaitlistService.getInstance().getWaitlist(institutionID, sectionID);
            Student nextStudent = cur.getNextStudent();
            if (nextStudent != null) {
                classRoster.addStudent(nextStudent);
            }
            return true;
        }
        return false;
    }

//    public String getCourseID() {
//        return courseID;
//    }
//
//    public void setCourseID(String courseID) {
//        this.courseID = courseID;
//    }

    public String getSectionID() {
        return sectionID;
    }

    public void setSectionID(String sectionID) {
        this.sectionID = sectionID;
    }

    public int getEnrollmentLimit() {
        return enrollmentLimit;
    }

    public void setEnrollmentLimit(int enrollmentLimit) {
        this.enrollmentLimit = enrollmentLimit;
    }

    public void setWaitlistID(String waitlistID) {
        this.waitlistID = waitlistID;
    }

    public ClassRoster getClassRoster() {
        return classRoster;
    }

    public void setClassRoster(ClassRoster classRoster) {
        this.classRoster = classRoster;
    }

    public GradingType getGrading() {
        return grading;
    }

    public void setGrading(GradingType grading) {
        this.grading = grading;
    }

    public InstructionModeType getInstructionMode() {
        return instructionMode;
    }

    public void setInstructionMode(InstructionModeType instructionMode) {
        this.instructionMode = instructionMode;
    }

//    public Location getLocation() {
//        return location;
//    }
//
//    public void setLocation(Location location) {
//        this.location = location;
//    }
//
//    public Campus getCampus() {
//        return campus;
//    }
//
//    public void setCampus(Campus campus) {
//        this.campus = campus;
//    }
//
//    public Room getRoom() {
//        return room;
//    }
//
//    public void setRoom(Room room) {
//        this.room = room;
//    }
//
//    public String getInstructor() {
//        return instructor;
//    }
//
//    public void setInstructor(String instructor) {
//        this.instructor = instructor;
//    }


    public boolean isFullyEnrolled() {
        return classRoster.getEnrollmentCount() >= enrollmentLimit;
    }
}
