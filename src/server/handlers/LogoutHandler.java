package server.handlers;

import server.SessionManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;

public class LogoutHandler {
    public static Message handleLogout(Message request) {
        SessionManager sessionManager = SessionManager.getInstance();
        User user = (User) request.getContent();
        if (user.isAuthenicated()) {
            String sessionToken = user.getSessionToken();
            sessionManager.terminateSession(sessionToken);
            return new Message(MessageType.LOGOUT, "Logout successful").setStatus(MessageStatus.SUCCESS);
        }
        return new Message(MessageType.LOGOUT, "Invalid credentials").setStatus(MessageStatus.FAILURE);
    }
}
