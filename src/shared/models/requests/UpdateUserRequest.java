package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;

public class UpdateUserRequest extends BaseRequest {
    private User userToUpdate;  // User to update
    private Administrator currentUser; // The user making the request

    public UpdateUserRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, User userToUpdate) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated()); // Pass the current user (administrator)
        this.currentUser = currentUser;
        this.userToUpdate = userToUpdate;
    }

    public User getUserToUpdate() {
        return userToUpdate;
    }

    public Administrator getCurrentUser() {
        return this.currentUser;
    }
}