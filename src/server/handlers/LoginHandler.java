package server.handlers;

import server.service.SessionService;
import server.service.UserService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Faculty;
import shared.models.User;
import shared.models.requests.BaseRequest;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import java.io.IOException;

public class LoginHandler {
    public static LoginResponse handleLogin(BaseRequest request, Log log) throws IOException {
        LoginRequest loginRequest = (LoginRequest) request;
        User user = loginRequest.getUser();
        UserService userService = UserService.getInstance(log,user.getInstitutionID());
        SessionService sessionService = SessionService.getInstance();
        boolean isAuthenticated = authenticateUser(user, userService);

        if (isAuthenticated) {
            // set user's token as sessionToken
            String sessionToken = sessionService.createSession(user.getUserId());
            user = userService.getUserByInstitution(user.getInstitutionID(), user.getUsername());
            user.setSessionToken(sessionToken);
            user.setAuthenticated(true);
            return new LoginResponse(MessageType.LOGIN, MessageStatus.SUCCESS,"Log in success", sessionToken, true, user);
        } else {
            return new LoginResponse(MessageType.LOGIN, MessageStatus.FAILURE,"Invalid credentials");
        }
    }

    private static boolean authenticateUser(User user, UserService userService) {
        // Authentication logic (check user credentials in database)
//        return "user1".equals(user.getUsername()) && "password123".equals(user.getPassword());
        return userService.doesUsernameAndPasswordEqualByInstitution(user.getInstitutionID() ,user.getUsername(), user.getPassword());
    }
}
