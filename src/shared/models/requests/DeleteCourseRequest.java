package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;

public class DeleteCourseRequest extends BaseRequest {
    private Course courseToDelete; // Course to be deleted
    private Administrator currentUser; // The user making the request

    public DeleteCourseRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, Course courseToDelete) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated());
        this.courseToDelete = courseToDelete;
        this.currentUser = currentUser;
    }

    public Course getCourseToDelete() {
        return courseToDelete;
    }
    
    public Administrator getCurrentUser() {
        return currentUser;
    }
}
