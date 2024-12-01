package server.handlers;

import java.io.IOException;
import java.util.List;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;
import shared.models.requests.BaseRequest;
import shared.models.requests.GetUsersRequest;
import shared.models.responses.GetUsersResponse;

public class GetUsersHandler{
    public static GetUsersResponse handleGetUsers(BaseRequest request, Log log) throws IOException {
        GetUsersRequest getUsersRequest = (GetUsersRequest) request;
        List<User> users = UserService.getInstance().getUsersByInstitution(getUsersRequest.getAdministrator().getInstitutionID());
        if (getUsersRequest.isAuthenticated() && SessionService.getInstance().validateSession(getUsersRequest.getAdministrator().getUserId(), getUsersRequest.getSessionToken())) {
            return new GetUsersResponse(MessageType.GET_USERS, MessageStatus.SUCCESS, users, "Here are the users");
        } else {
            return new GetUsersResponse(MessageType.GET_USERS, MessageStatus.FAILURE, null, "Invalid credentials");
        }
    }
}