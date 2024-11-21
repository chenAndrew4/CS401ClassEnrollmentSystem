package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class LoginRequest extends BaseRequest {
    private User user;

    public LoginRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, User user) {
        super(messageType, messageStatus, institutionID, null, false);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}