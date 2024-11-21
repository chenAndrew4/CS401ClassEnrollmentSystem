package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DropCourseResponse extends BaseResponse {
    private String courseId;
    private String sectionId;

    public DropCourseResponse(MessageStatus messageStatus, MessageType messageType, String message, String courseId, String sectionId) {
        super(message,  messageStatus, messageType);
        this.courseId = courseId;
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }

    public void setSectionId(String sectionId) {
        this.sectionId = sectionId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}

