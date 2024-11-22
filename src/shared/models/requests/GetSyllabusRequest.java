package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class GetSyllabusRequest extends BaseRequest {
    private String sectionId;

    public GetSyllabusRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String sectionId, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
