package server.handlers;

import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;
import server.UserManager;

import java.io.IOException;

public class CreateAccountHandler {
    public static Message handleCreateAccount(Message request, Log log) throws IOException {
        // create a user
        // *** to do
        User newUser = (User) request.getContent();
        UserManager userManager = UserManager.getInstance(log, newUser.getInstitutionID());

        // Check if the username already exists
//        Object MessageType;
        if (userManager.doesUsernameExistByInstitution(newUser.getInstitutionID(), newUser.getUsername())) {
            log.println("Attempt to create an account with an existing username: " + newUser.getUsername());
            return new Message(MessageType.CREATE_ACCOUNT, MessageStatus.FAILURE, "Username already exists");
        }

        // Add user to database
        boolean success = userManager.addUserByInstitution(newUser.getInstitutionID(), newUser);
        if (success) {
            log.println("Account created for user: " + newUser.getUsername());
            return new Message(MessageType.CREATE_ACCOUNT, MessageStatus.SUCCESS, "Account created successfully");
        } else {
            log.println("Failed to create account for user: " + newUser.getUsername());
            return new Message(MessageType.CREATE_ACCOUNT, MessageStatus.ERROR, "Failed to create account");
        }

    }
}