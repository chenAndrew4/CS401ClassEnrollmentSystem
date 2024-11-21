package server.handlers;

import server.SessionManager;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;
import server.UserManager;
import shared.models.requests.BaseRequest;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import java.io.IOException;

public class LoginHandler {
    public static LoginResponse handleLogin(BaseRequest request, Log log) throws IOException {
        LoginRequest loginRequest = (LoginRequest) request;
        User user = loginRequest.getUser();
        UserManager userManager = UserManager.getInstance(log, loginRequest.getInstitutionID());
        SessionManager sessionManager = SessionManager.getInstance();
        boolean isAuthenticated = authenticateUser(user, userManager);

        if (isAuthenticated) {
            String sessionToken = sessionManager.createSession(user.getUserId());
            // set user's token as sessionToken
            user = userManager.getUserByInstitution(user.getInstitutionID(), user.getUsername());
            return new LoginResponse(MessageType.LOGIN, MessageStatus.SUCCESS,"Log in success", sessionToken, true, user);
        } else {
            return new LoginResponse(MessageType.LOGIN, MessageStatus.FAILURE,"Invalid credentials");
        }
    }

    private static boolean authenticateUser(User user, UserManager userManager) {
        // Authentication logic (check user credentials in database)
//        return "user1".equals(user.getUsername()) && "password123".equals(user.getPassword());
        return userManager.doesUsernameAndPasswordEqualByInstitution(user.getInstitutionID() ,user.getUsername(), user.getPassword());
    }
}
