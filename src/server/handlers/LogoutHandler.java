package server.handlers;

import server.SessionManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public class LogoutHandler {
    public static Message handleLogout(Message request, SessionManager sessionManager) {
        String sessionToken = (String) request.getContent();
        sessionManager.terminateSession(sessionToken);
        return new Message(MessageType.LOGOUT, "Logout successful").setStatus(MessageStatus.SUCCESS);
    }
}
