package shared.models.requests;

import shared.enums.AccountType;
import shared.enums.Institutions;
import shared.models.User;

public class DeleteUserRequest extends BaseRequest {
    private String usernameToDelete;  // Username of the user to delete
    private AccountType accountType; // The session token for authentication

    public DeleteUserRequest(String usernameToDelete, Institutions institutionID, String sessionToken, User currentUser) {
        super(institutionID, sessionToken); // Pass the current user (administrator)
        this.usernameToDelete = usernameToDelete;
        this.accountType = currentUser.getAccountType();
    }

    public String getUsernameToDelete() {
        return usernameToDelete;
    }

    public void setUsernameToDelete(String usernameToDelete) {
        this.usernameToDelete = usernameToDelete;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public void setAccountType(AccountType accountType) {
        this.accountType = accountType;
    }
}

