package server.handlers;

import java.io.IOException;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.DeleteUserRequest;
import shared.models.responses.DeleteUserResponse;

public class DeleteUserHandler {
    public static DeleteUserResponse handleDeleteUser(BaseRequest request, Log log) throws IOException {
        DeleteUserRequest deleteUserRequest = (DeleteUserRequest) request;
        if (deleteUserRequest.isAuthenticated() && SessionService.getInstance().validateSession(deleteUserRequest.getUserId(), deleteUserRequest.getSessionToken())) {
            boolean success = UserService.getInstance().removeUserByInstitution(deleteUserRequest.getUser().getInstitutionID(), deleteUserRequest.getUser().getUsername());
            if (success) {
                return new DeleteUserResponse(MessageStatus.SUCCESS, MessageType.DELETE_USER, "User has been deleted!");
            } else {
                return new DeleteUserResponse(MessageStatus.FAILURE, MessageType.DELETE_USER, "User could not be deleted!");
            }
        } else {
            return new DeleteUserResponse(MessageStatus.FAILURE, MessageType.DELETE_USER, "Invalid credentials");
        }
    }
}
