package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateWaitlistResponse extends BaseResponse {
    private String courseId;
    private int waitlistPosition;

    public UpdateWaitlistResponse( MessageStatus messageStatus, MessageType messageType, String message, String courseId, int waitlistPosition) {
        super(message, messageStatus, messageType);
        this.courseId = courseId;
        this.waitlistPosition = waitlistPosition;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public int getWaitlistPosition() {
        return waitlistPosition;
    }

    public void setWaitlistPosition(int waitlistPosition) {
        this.waitlistPosition = waitlistPosition;
    }
}