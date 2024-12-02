package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class EnrollWaitlistResponse extends BaseResponse {
    private String sectionId;
    private int waitlistPosition;

    public EnrollWaitlistResponse(String message, MessageStatus messageStatus, MessageType messageType, String sectionID, int waitlistPosition) {
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

