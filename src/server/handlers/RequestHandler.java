package server.handlers;

import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.requests.BaseRequest;
import shared.models.responses.BaseResponse;

import java.io.IOException;

public class RequestHandler {
    public static BaseResponse handleRequest(BaseRequest request, Log log) throws IOException {
        switch (request.getType()) {
            case LOGIN:
                return LoginHandler.handleLogin(request, log);
            case LOGOUT:
                return LogoutHandler.handleLogout(request);
            case ENROLL_COURSE:
//                return EnrollCourseHandler.handleEnrollCourse(request);
            case DROP_COURSE:
//                return DropCourseHandler.handleDropCourse(request, courseManager, sessionManager);
            case DELETE:
//                return DeleteHandler.handleDelete(request);
            case DEBUG:
                return DebugHandler.handleDebug(request);
            default:
                log.println("Unknown message type received: " + request.getType());
                return new BaseResponse("Unknown message type", MessageStatus.ERROR ,MessageType.DEFUALT);
        }
    }
}
