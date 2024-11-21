package server.handlers;

import server.CourseManager;
import server.SessionManager;
import server.utils.Log;
import shared.enums.MessageType;
import shared.models.Message;
import server.UserManager;

import java.io.IOException;

public class MessageHandler {
    public static Message handleMessage(Message request, Log log) throws IOException {
        switch (request.getType()) {
            case CREATE_ACCOUNT:
                return CreateAccountHandler.handleCreateAccount(request, log);
            case LOGIN:
                return LoginHandler.handleLogin(request, log);
            case LOGOUT:
                return LogoutHandler.handleLogout(request);
            case ENROLL_COURSE:
                return EnrollCourseHandler.handleEnrollCourse(request);
            case DROP_COURSE:
//                return DropCourseHandler.handleDropCourse(request, courseManager, sessionManager);
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
