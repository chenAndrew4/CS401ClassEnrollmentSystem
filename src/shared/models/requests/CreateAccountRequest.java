package shared.models.requests;

import shared.enums.Institutions;
import shared.models.User;

public class CreateAccountRequest extends BaseRequest {
    private User newUser;

    public CreateAccountRequest(Institutions institutionID, User newUser) {
        super(institutionID, null);
        this.newUser = newUser;
    }

    public User getNewUser() {
        return newUser;
    }
}
