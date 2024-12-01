package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class AddWaitlistResponse extends BaseResponse {
    private String courseId;
    private int waitlistPosition;

    public AddWaitlistResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message, messageStatus, messageType);
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

