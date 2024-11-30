package shared.models.requests;

import shared.enums.AccountType;
import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;
import shared.models.User;

public class AddUserRequest extends BaseRequest {
    private User userToAdd; // The user object to be added
    private AccountType accountType;
    private String userID;

    public AddUserRequest(MessageType messageType, MessageStatus messageStatus, String userID, Institutions institutionID, String sessionToken, boolean isAuthenicated, User userToAdd, User currentUser) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated); // Pass the current user (administrator)
        this.userToAdd = userToAdd;
        this.accountType = currentUser.getAccountType();
        this.userID = userID;
    }

    public User getUserToAdd() {
        return userToAdd;
    }

    public AccountType getAccountType() {
        return accountType;
    }

    public String getUserID() {
        return userID;
    }
}

