package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;

public class DeleteUserRequest extends BaseRequest {
    private User userToDelete;  // User to delete
    private Administrator currentUser; // The user making the request

    public DeleteUserRequest(MessageType messageType, MessageStatus messageStatus, Administrator currentUser, User userToDelete) {
        super(messageType, messageStatus, currentUser.getInstitutionID(), currentUser.getSessionToken(), currentUser.isAuthenticated()); // Pass the current user (administrator)
        this.currentUser = currentUser;
        this.userToDelete = userToDelete;
    }

    public User getUserToDelete() {
        return this.userToDelete;
    }

    public Administrator getCurrentUser() {
        return this.currentUser;
    }
}

