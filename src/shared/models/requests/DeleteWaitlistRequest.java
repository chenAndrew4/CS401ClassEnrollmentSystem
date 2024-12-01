package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.WaitList;

public class DeleteWaitlistRequest extends BaseRequest {
    private WaitList waitlist;

    public DeleteWaitlistRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, WaitList waitlist) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.waitlist = waitlist;
    }

    public String getSectionId() {
        return this.waitlist.getSectionID();
    }
}
