package shared.models.requests;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.WaitList;

public class UpdateWaitlistRequest extends BaseRequest {
    private WaitList newWaitlist;
    private WaitList oldWaitlist;

    public UpdateWaitlistRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, WaitList newWaitlist,  WaitList oldWaitlist) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.newWaitlist = newWaitlist;
        this.newWaitlist = oldWaitlist;
    }

    public WaitList getNewWaitlist() {
        return this.newWaitlist;
    }
    
    public WaitList getOldWaitlist() {
        return this.oldWaitlist;
    }
}