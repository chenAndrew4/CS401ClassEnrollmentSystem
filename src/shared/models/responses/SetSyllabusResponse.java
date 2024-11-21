package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class SetSyllabusResponse extends BaseResponse {
    private String courseId;
    private String sectionId;
    private String syllabus;

    public SetSyllabusResponse(MessageStatus messageStatus, MessageType messageType, String message, String courseId, String sectionId, String syllabus) {
        super(message,  messageStatus, messageType);
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.syllabus = syllabus;
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

    public String getSyllabus() {
        return syllabus;
    }

    public void setSyllabus(String syllabus) {
        this.syllabus = syllabus;
    }
}

