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
        log.debug("AddUserHandler: sessionToken=" + request.getSessionToken() + " isAuth=" + request.isAuthenticated());
        //log.debug("AddUserHandler: Trying to add: " + addUserRequest.getUser().toString() );
        if (addUserRequest.isAuthenticated() && SessionService.getInstance().validateSession(addUserRequest.getUserId(), addUserRequest.getSessionToken())) {
            boolean success = UserService.getInstance().addUserByInstitution(addUserRequest.getUser().getInstitutionID(), addUserRequest.getUser());
            
            if (success) {
                return new AddUserResponse(MessageStatus.SUCCESS, MessageType.ADD_USER, "User has been created!");
            } else {
                return new AddUserResponse(MessageStatus.FAILURE, MessageType.ADD_USER, "User could not be created!");
            }
        } else {
            return new AddUserResponse(MessageStatus.FAILURE, MessageType.ADD_USER, "Invalid credentials");
        }
    }
}
