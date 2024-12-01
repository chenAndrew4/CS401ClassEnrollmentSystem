package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Course;

public class UpdateCourseRequest extends BaseRequest {
    private Course courseToUpdate;  // Course to update
    private Administrator currentUser; // The user making the request

    public UpdateCourseRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, Course courseToUpdate) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated()); // Pass the current user (administrator)
        this.courseToUpdate = courseToUpdate;
        this.currentUser = currentUser;
    }

    public Course getCourseToUpdate() {
        return courseToUpdate;
    }

    public Administrator getCurrentUser() {
        return this.currentUser;
    }
}