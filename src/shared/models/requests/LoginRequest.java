package shared.models.requests;

import shared.enums.Institutes;
import shared.models.User;

public class LoginRequest extends BaseRequest {
    private User user;

    public LoginRequest(Institutes instituteID, User user) {
        super(instituteID, null);
        this.user = user;
    }

    public User getUser() {
        return user;
    }
}