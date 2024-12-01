package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Student;

public class DropCourseRequest extends BaseRequest {
    private String courseId;
    private String sectionId;
    private Student student;

    public DropCourseRequest(MessageType messageType, MessageStatus messageStatus, String sessionToken, String courseId, String sectionId, Student student) {
        super(messageType, messageStatus, student.getInstitutionID(), student.getSessionToken(), student.isAuthenticated());
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.student = student;
    }

    public String getCourseId() {
        return this.courseId;
    }

    public String getSectionId() {
        return this.sectionId;
    }
    
    public Student getStudent() {
    	return this.student;
    }
}
