package server.handlers;

import server.CourseManager;
import server.SessionManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.User;
import shared.models.requests.BaseRequest;
import shared.models.requests.EnrollCourseRequest;
import shared.models.responses.EnrollCourseResponse;

public class EnrollCourseHandler {
    public static EnrollCourseResponse handleEnrollCourse(BaseRequest request) {
        SessionManager sessionManager = SessionManager.getInstance();
        EnrollCourseRequest enrollCourseRequest = (EnrollCourseRequest) request;
        CourseManager courseManager = CourseManager.getInstance();
        return new EnrollCourseResponse("enrolled", MessageStatus.SUCCESS, MessageType.ENROLL_COURSE);
    }
}
