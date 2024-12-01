package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Administrator admin; // The user making the request
    private Course course; // Course to be deleted
    
    public AddCourseRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Course course) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.course = course;
    }
    
    public String getUserId() {
    	return this.admin.getUserId();
    }
    
    public Course getCourse() {
        return this.course;
    }
}
