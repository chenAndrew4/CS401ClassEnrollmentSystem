package server.handlers;

import server.SessionService;
import shared.enums.MessageStatus;
import shared.models.requests.BaseRequest;
import shared.models.requests.LogoutRequest;
import shared.models.responses.LogoutResponse;

public class LogoutHandler {
    public static LogoutResponse handleLogout(BaseRequest request) {
        SessionService sessionService = SessionService.getInstance();
        LogoutRequest logoutRequest = (LogoutRequest) request;
        String sessionToken = logoutRequest.getSessionToken();
        if (logoutRequest.isAuthenicated()) {
            sessionService.terminateSession(sessionToken);
            return new LogoutResponse(true,  "Logout successful", MessageStatus.SUCCESS);
        }
        return new LogoutResponse(false, "Invalid credentials", MessageStatus.FAILURE);
    }
}
