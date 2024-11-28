package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class EnrollCourseResponse extends BaseResponse {
    private String sectionId;
    private int waitlistPosition;

    // if waitlistPosition == -1 enroll success otherwise added to waitlist
    public EnrollCourseResponse(String message, MessageStatus messageStatus, MessageType messageType, String sectionID, int waitlistPosition) {
        super(message,  messageStatus, messageType);
        this.sectionId = sectionID;
        this.waitlistPosition = waitlistPosition;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getWaitlistPosition() {
        return waitlistPosition;
    }
}

