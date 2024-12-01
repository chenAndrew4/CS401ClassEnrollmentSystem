package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DeleteCourseResponse extends BaseResponse {
    private String courseId;
    private String sectionId;

    public DeleteCourseResponse(MessageStatus messageStatus, MessageType messageType, String message, String courseId) {
        super(message,  messageStatus, messageType);
        this.courseId = courseId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }
}