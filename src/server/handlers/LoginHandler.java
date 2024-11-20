package server.handlers;

import server.SessionManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;
import server.UserManager;

public class LoginHandler {
    public static Message handleLogin(Message request, UserManager userManager, SessionManager sessionManager) {
        User user = (User) request.getContent();
        boolean isAuthenticated = authenticateUser(user, userManager);

        if (isAuthenticated) {
            String sessionToken = sessionManager.createSession(user.getUserId());
            // set user's token as sessionToken
            user = userManager.getUser(user.getUsername());
            user.setAuthenicated(true);
            user.setSessionToken(sessionToken);
            return new Message(MessageType.LOGIN, user).setStatus(MessageStatus.SUCCESS);
        } else {
            return new Message(MessageType.LOGIN, "Invalid credentials").setStatus(MessageStatus.FAILURE);
        }
    }

    private static boolean authenticateUser(User user, UserManager userManager) {
        // Authentication logic (check user credentials in database)
//        return "user1".equals(user.getUsername()) && "password123".equals(user.getPassword());
        return userManager.doesUsernameAndPasswordEqual(user.getUsername(), user.getPassword());
    }
}
