package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;
import shared.models.Student;


public class AdminEnrollRequest extends BaseRequest {
    private Administrator admin;
    private Student student;
    private Course course;
    private String sectionId;

    public AdminEnrollRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Student student, Course course, String sectionId) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.student = student;
        this.course = course;
        this.sectionId = sectionId;
    }

    public Course getCourse() {
        return this.course;
    }

    public String getSectionId() {
        return this.sectionId;
    }

    public Student getStudent() {
        return this.student;
    }
}