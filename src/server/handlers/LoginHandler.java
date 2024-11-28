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
        
        if (isAuthenticated) {
            // set user's token as sessionToken
            String sessionToken = sessionService.createSession(user.getUserId());
            user = userService.getUserByInstitutionAndUsername(user.getInstitutionID(), user.getUsername());
            user.setSessionToken(sessionToken);
            user.setAuthenticated(true);
            return new LoginResponse(MessageType.LOGIN, MessageStatus.SUCCESS,"Log in success", sessionToken, true, user);
        } else {
            return new LoginResponse(MessageType.LOGIN, MessageStatus.FAILURE, "Invalid credentials");
        }
    }

    private static boolean authenticateUser(User user, UserService userService) {
        return userService.doesUsernameAndPasswordEqualByInstitution(user.getInstitutionID() ,user.getUsername(), user.getPassword());
    }
}
