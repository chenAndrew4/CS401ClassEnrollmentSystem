package shared.models.requests;

import shared.enums.AccountType;
import shared.enums.Institutions;
import shared.models.User;

public class AddUserRequest extends BaseRequest {
    private User userToAdd; // The user object to be added
    private AccountType accountType;

    public AddUserRequest(Institutions institutionID, String session, User userToAdd, User currentUser) {
        super(institutionID, session); // Pass the current user (administrator)
        this.userToAdd = userToAdd;
        this.accountType = currentUser.getAccountType();
    }

    public User getUserToAdd() {
        return userToAdd;
    }

    public void setUserToAdd(User userToAdd) {
        this.userToAdd = userToAdd;
    }

    public AccountType getAccountType() {
        return accountType;
    }
}

