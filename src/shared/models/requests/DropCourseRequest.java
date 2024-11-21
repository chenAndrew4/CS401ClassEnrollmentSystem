package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DropCourseRequest extends BaseRequest {
    private String courseId;
    private String sectionId;

    public DropCourseRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String courseId, String sectionId, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
