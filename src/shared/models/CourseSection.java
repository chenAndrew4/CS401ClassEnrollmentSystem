package shared.models;

import server.service.WaitlistService;
import shared.enums.*;
import shared.utils.IDGenerator;

import java.io.Serializable;

public class CourseSection implements Serializable {
    private String sectionID;     // Unique identifier for each section
    private int enrollmentLimit;  // Max students for this section
    private String waitlistID;    // Section's waitlist
    private ClassRoster classRoster;  // Students enrolled in the section
    private GradingType grading;
    private InstructionModeType instructionMode;
    private String scheduleID;    // Schedule for this section
    private String notes;

    public CourseSection(Institutions institutionID, String courseID) {
        this.sectionID = IDGenerator.getInstance().generateUniqueSectionID(institutionID,courseID);
    }

    // Copy constructor
    public CourseSection(CourseSection other) {
        this.sectionID = other.sectionID;
        this.enrollmentLimit = other.enrollmentLimit;
        this.waitlistID = other.waitlistID; // Assuming WaitList has a copy constructor
        this.classRoster = other.classRoster != null ? new ClassRoster(other.classRoster) : new ClassRoster(sectionID);
        this.grading = other.grading;
        this.instructionMode = other.instructionMode;
        this.scheduleID = other.scheduleID;
        this.notes = other.notes;
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

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getWaitlistID() {
        return waitlistID;
    }

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

    public boolean isFullyEnrolled() {
        return classRoster.getEnrollmentCount() >= enrollmentLimit;
    }

	@Override
	public String toString() {
		return "CourseSection {sectionID=" + sectionID + ", enrollmentLimit=" + enrollmentLimit + ", waitlistID="
				+ waitlistID + ", classRoster=" + classRoster + ", grading=" + grading + ", instructionMode="
				+ instructionMode + ", scheduleID=" + scheduleID + ", notes=" + notes + "}";
	}
    
    
}
