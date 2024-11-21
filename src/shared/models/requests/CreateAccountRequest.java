package shared.models.requests;

import shared.enums.Institutes;
import shared.models.User;

public class CreateAccountRequest extends BaseRequest {
    private User newUser;

    public CreateAccountRequest(Institutes instituteID, User newUser) {
        super(instituteID, null);
        this.newUser = newUser;
    }

    public User getNewUser() {
        return newUser;
    }
}
