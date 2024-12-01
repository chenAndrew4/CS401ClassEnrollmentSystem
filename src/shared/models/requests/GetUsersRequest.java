package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;

public class GetUsersRequest extends BaseRequest {
	private Administrator admin; // The user making the request

    public GetUsersRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated()); // Pass the current user (administrator)
        this.admin = admin;
    }
    
    public Administrator getAdministrator() {
        return this.admin;
    }
}