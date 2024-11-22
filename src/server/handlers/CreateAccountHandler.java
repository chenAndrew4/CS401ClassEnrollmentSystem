package server.handlers;

import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;
import server.UserService;
import shared.models.requests.BaseRequest;
import shared.models.requests.CreateAccountRequest;
import shared.models.responses.CreateAccountResponse;

import java.io.IOException;

public class CreateAccountHandler {
    public static CreateAccountResponse handleCreateAccount(BaseRequest request, Log log) throws IOException {
        // create a user
        // *** to do
        CreateAccountRequest createAccountRequest = (CreateAccountRequest) request;
        User newUser = createAccountRequest.getNewUser();
        UserService userService = UserService.getInstance(log, newUser.getInstitutionID());

        // Check if the username already exists
        if (userService.doesUsernameExistByInstitution(newUser.getInstitutionID(), newUser.getUsername())) {
            log.println("Attempt to create an account with an existing username: " + newUser.getUsername());
            return new CreateAccountResponse(MessageType.CREATE_ACCOUNT, MessageStatus.FAILURE, "Username already exists", newUser.getUserId());
        }

        // Add user to database
        boolean success = userService.addUserByInstitution(newUser.getInstitutionID(), newUser);
        if (success) {
            log.println("Account created for user: " + newUser.getUsername());
            return new CreateAccountResponse(MessageType.CREATE_ACCOUNT, MessageStatus.SUCCESS, "Account created successfully",  newUser.getUserId());
        } else {
            log.println("Failed to create account for user: " + newUser.getUsername());
            return new CreateAccountResponse(MessageType.CREATE_ACCOUNT, MessageStatus.ERROR, "Failed to create account",  newUser.getUserId());
        }

    }
}