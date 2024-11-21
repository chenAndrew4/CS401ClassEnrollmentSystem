package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class SetSyllabusRequest extends BaseRequest {
    private String sectionId;
    private String syllabus;

    public SetSyllabusRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String sectionId, String syllabus, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.sectionId = sectionId;
        this.syllabus = syllabus;
    }

    public String getSectionId() {
        return sectionId;
    }

    public String getSyllabus() {
        return syllabus;
    }
}
