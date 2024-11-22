package server.handlers;

import server.SessionService;
import server.dataManagers.CoursesDataManager;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.EnrollCourseRequest;
import shared.models.responses.EnrollCourseResponse;

public class EnrollCourseHandler {
    public static EnrollCourseResponse handleEnrollCourse(BaseRequest request) {
        SessionService sessionService = SessionService.getInstance();
        EnrollCourseRequest enrollCourseRequest = (EnrollCourseRequest) request;
        CoursesDataManager coursesDataManager = CoursesDataManager.getInstance();
        return new EnrollCourseResponse("enrolled", MessageStatus.SUCCESS, MessageType.ENROLL_COURSE);
    }
}
