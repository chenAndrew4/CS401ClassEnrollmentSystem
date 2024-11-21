package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class CreateAccountRequest extends BaseRequest {
    private User newUser;

    public CreateAccountRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, User newUser, boolean isAuthenicated) {
        super(messageType, messageStatus,institutionID, null, isAuthenicated);
        this.newUser = newUser;
    }

    public User getNewUser() {
        return newUser;
    }
}
