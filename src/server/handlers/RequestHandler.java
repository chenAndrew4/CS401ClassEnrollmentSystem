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
            case GET_ASSIGNED_COURSES:
                return GetAssignedCoursesHandler.handleGetAssignedCourses(request, log);
        	case ADD_USER:
        		return AddUserHandler.handleAddUser(request, log);
            case DEBUG:
                return DebugHandler.handleDebug(request);
            case DELETE:
//              return DeleteHandler.handleDelete(request);
            case DROP_COURSE:
//              return DropCourseHandler.handleDropCourse(request, courseManager, sessionManager);
            case ENROLL_COURSE:
//              return EnrollCourseHandler.handleEnrollCourse(request);
            case GET_USERS:
//            	return GetUsersHandler.handleGetUsers(request, log);
            case LOGIN:
                return LoginHandler.handleLogin(request, log);
            case LOGOUT:
                return LogoutHandler.handleLogout(request);
            default:
                log.println("Unknown message type received: " + request.getType());
                return new BaseResponse("Unknown message type", MessageStatus.ERROR, MessageType.DEFUALT);
        }
    }
}
