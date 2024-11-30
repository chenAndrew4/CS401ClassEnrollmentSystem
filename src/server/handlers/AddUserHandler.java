package server.handlers;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.AddUserRequest;
import shared.models.requests.BaseRequest;
import shared.models.responses.AddUserResponse;

import java.io.IOException;

public class AddUserHandler {
    public static AddUserResponse handleAddUser(BaseRequest request, Log log) throws IOException {
        AddUserRequest addUserRequest = (AddUserRequest) request;
        if (addUserRequest.isAuthenticated() && SessionService.getInstance().validateSession(addUserRequest.getUserID(), addUserRequest.getSessionToken())) {
            boolean success = UserService.getInstance().addUserByInstitution(addUserRequest.getInstitutionID(), addUserRequest.getUserToAdd());
            if (success) {
                return new AddUserResponse("added", MessageStatus.SUCCESS, MessageType.ADD_USER);
            } else {
                return new AddUserResponse("not added", MessageStatus.FAILURE, MessageType.ADD_USER);
            }
        } else {
            return new AddUserResponse("Invalid credentials", MessageStatus.FAILURE, MessageType.ADD_USER);
        }
    }
}
