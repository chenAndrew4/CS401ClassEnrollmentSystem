package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;

public class AddUserRequest extends BaseRequest {
	private Administrator admin; // The user making the request
    private User user;  // User to update

    public AddUserRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, User user) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated()); // Pass the current user (administrator)
        this.admin = admin;
        this.user = user;
    }

    public String getUserId() {
    	return this.admin.getUserId();
    }
    
    public User getUser() {
        return this.user;
    }
    
    public String toString() {
    	return this.user.toString() + " " + this.admin.toString();
    }
}

