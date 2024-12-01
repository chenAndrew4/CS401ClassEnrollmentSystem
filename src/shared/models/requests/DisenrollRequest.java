package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Student;

public class DisenrollRequest extends BaseRequest {
    private String courseId;
    private String sectionId;
    private Student student;
    private Administrator admin;

    public DisenrollRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Student student, String courseId, String sectionId) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.courseId = courseId;
        this.sectionId = sectionId;
        this.student = student;
        this.admin = admin;
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