package server.handlers;

import server.CourseManager;
import server.SessionManager;
import server.utils.Log;
import shared.enums.MessageType;
import shared.models.Message;
import server.UserManager;

public class MessageHandler {
    public static Message handleMessage(Message request, Log log, UserManager userManager, CourseManager courseManager, SessionManager sessionManager) {
        switch (request.getType()) {
            case CREATE_ACCOUNT:
                return CreateAccountHandler.handleCreateAccount(request, userManager, log);
            case LOGIN:
                return LoginHandler.handleLogin(request, userManager, sessionManager);
            case LOGOUT:
                return LogoutHandler.handleLogout(request, sessionManager);
            case ENROLL_COURSE:
                return EnrollCourseHandler.handleEnrollCourse(request, courseManager, sessionManager);
            case DROP_COURSE:
                return DropCourseHandler.handleDropCourse(request, courseManager, sessionManager);
            case DELETE:
                return DeleteHandler.handleDelete(request);
            case DEBUG:
                return DebugHandler.handleDebug(request);
            default:
                log.println("Unknown message type received: " + request.getType());
                return new Message(MessageType.DEBUG, "Unknown message type");
        }
    }
}
