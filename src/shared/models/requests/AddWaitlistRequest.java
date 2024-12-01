package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.WaitList;

public class AddWaitlistRequest extends BaseRequest {
	private Administrator admin;
    private WaitList waitlist;

    public AddWaitlistRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, WaitList waitlist) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.waitlist = waitlist;
    }

    public Administrator getAdmin() {
        return this.admin;
    }

    public WaitList getWaitlist() {
        return this.waitlist;
    }
}
