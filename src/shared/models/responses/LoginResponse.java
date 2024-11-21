package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class LoginResponse extends BaseResponse {
    private User user;

    public LoginResponse() {}

    public LoginResponse(MessageType messageType, MessageStatus messageStatus, String message) {
        super(message, messageStatus, messageType);
        this.user = null;
    }

    public LoginResponse(MessageType messageType, MessageStatus messageStatus, String message, String sessionToken, Boolean authen, User user) {
        super(message, messageStatus, messageType);
        this.user = user;
        user.setSessionToken(sessionToken);
        user.setAuthenicated(authen);
    }
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
