package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;

public class AddCourseRequest extends BaseRequest {
    private Course course;
    private String userID;
    
    public AddCourseRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, Course courseToAdd) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated());
        this.course = courseToAdd;
    }
    public Course getCourse() {
        return course;
    }
}
