package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class AddWaitlistRequest extends BaseRequest {
    private String sectionId;
    private int maxWaitlistSize;

    public AddWaitlistRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String sectionId, int maxWaitlistSize, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.sectionId = sectionId;
        this.maxWaitlistSize = maxWaitlistSize;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getMaxWaitlistSize() {
        return maxWaitlistSize;
    }
}
