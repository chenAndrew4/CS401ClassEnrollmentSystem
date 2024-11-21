package shared.models.responses;

import shared.enums.MessageStatus;
import shared.models.User;

public class LoginResponse extends BaseResponse {
    private User user;

    public LoginResponse(String message, String sessionToken, Boolean authen, User user) {
        super(message);
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
