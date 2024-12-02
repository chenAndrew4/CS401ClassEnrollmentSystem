package server.handlers;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;
import shared.models.requests.BaseRequest;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import java.io.IOException;

public class LoginHandler {
    public static LoginResponse handleLogin(BaseRequest request, Log log) throws IOException {
        LoginRequest loginRequest = (LoginRequest) request;
        User user = loginRequest.getUser();
        UserService userService = UserService.getInstance();
        SessionService sessionService = SessionService.getInstance();
        boolean isAuthenticated = authenticateUser(user, userService);
        User actualUser = userService.getUserByInstitutionAndUsername(user.getInstitutionID(), user.getUsername());
        
        if (isAuthenticated) {
            // set user's token as sessionToken
            String sessionToken = sessionService.createSession(actualUser.getUserId());
            actualUser.setSessionToken(sessionToken);
            actualUser.setAuthenticated(true);
            return new LoginResponse(MessageType.LOGIN, MessageStatus.SUCCESS,"Log in success", sessionToken, true, actualUser);
        } else {
            return new LoginResponse(MessageType.LOGIN, MessageStatus.FAILURE, "Invalid credentials");
        }
    }

    private static boolean authenticateUser(User user, UserService userService) {
        return userService.doesUsernameAndPasswordEqualByInstitution(user.getInstitutionID() ,user.getUsername(), user.getPassword());
    }
}
