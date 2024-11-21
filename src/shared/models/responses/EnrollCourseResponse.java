package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;

public class EnrollCourseResponse extends BaseResponse {
    private String courseId;
    private String sectionId;
    private Schedule schedule; // Schedule of the enrolled course
    private int waitlistPosition; // Position in the waitlist (optional)

    public EnrollCourseResponse(String message, MessageStatus messageStatus, MessageType messageType) {
        super(message,  messageStatus, messageType);
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
    }

    public int getWaitlistPosition() {
        return waitlistPosition;
    }

    public void setWaitlistPosition(int waitlistPosition) {
        this.waitlistPosition = waitlistPosition;
    }
}

