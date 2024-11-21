package server.handlers;

import server.SessionManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;
import shared.models.requests.BaseRequest;
import shared.models.requests.LogoutRequest;
import shared.models.responses.LogoutResponse;

public class LogoutHandler {
    public static LogoutResponse handleLogout(BaseRequest request) {
        SessionManager sessionManager = SessionManager.getInstance();
        LogoutRequest logoutRequest = (LogoutRequest) request;
        String sessionToken = logoutRequest.getSessionToken();
        if (logoutRequest.isAuthenicated()) {
            sessionManager.terminateSession(sessionToken);
            return new LogoutResponse(true,  "Logout successful", MessageStatus.SUCCESS);
        }
        return new LogoutResponse(false, "Invalid credentials", MessageStatus.FAILURE);
    }
}
