package shared.models.requests;

import shared.enums.Institutions;
import shared.models.User;

public class LoginRequest extends BaseRequest {
    private User user;

    public LoginRequest(Institutions institutionID, User user) {
        super(institutionID, null);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}