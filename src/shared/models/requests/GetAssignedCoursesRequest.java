package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Faculty;

public class GetAssignedCoursesRequest extends BaseRequest {
    Faculty currentUser;
    String userID;

    public GetAssignedCoursesRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, Faculty currentUser, String sessionToken, boolean isAuthenticated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenticated);
        this.currentUser = currentUser;
        this.userID = currentUser.getUserId();
    }

    public String getUserID() {
        return userID;
    }

    public Faculty getUser() {
        return currentUser;
    }
}
