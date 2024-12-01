package server.handlers;

import java.io.IOException;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.AddUserRequest;
import shared.models.requests.BaseRequest;
import shared.models.requests.UpdateUserRequest;
import shared.models.responses.UpdateUserResponse;

public class UpdateUserHandler{
    public static UpdateUserResponse handleUpdateUser(BaseRequest request, Log log) throws IOException {
        UpdateUserRequest updateUserRequest = (UpdateUserRequest) request;
        if (updateUserRequest.isAuthenticated() && SessionService.getInstance().validateSession(updateUserRequest.getUserId(), updateUserRequest.getSessionToken())) {
            boolean success = UserService.getInstance().updateUserByInstitutions(updateUserRequest.getUser().getInstitutionID(), updateUserRequest.getUser());
            if (success) {
                return new UpdateUserResponse(MessageStatus.SUCCESS, MessageType.UPDATE_USER, "User has been updated!");
            } else {
                return new UpdateUserResponse(MessageStatus.FAILURE, MessageType.UPDATE_USER, "User could not be updated!");
            }
        } else {
            return new UpdateUserResponse(MessageStatus.FAILURE, MessageType.UPDATE_USER, "Invalid credentials");
        }
    }
}