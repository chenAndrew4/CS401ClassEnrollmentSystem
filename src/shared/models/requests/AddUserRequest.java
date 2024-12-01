package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;

public class AddUserRequest extends BaseRequest {
    private User userToAdd;  // User to add
    private Administrator currentUser; // The user making the request

    public AddUserRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, User userToAdd) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated()); // Pass the current user (administrator)
        this.currentUser = currentUser;
        this.userToAdd = userToAdd;
    }

    public User getUserToAdd() {
        return this.userToAdd;
    }

    public Administrator getCurrentUser() {
        return this.currentUser;
    }
}

