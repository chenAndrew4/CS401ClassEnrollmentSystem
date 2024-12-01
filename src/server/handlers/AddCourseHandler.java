package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Student;
import shared.models.requests.AddCourseRequest;
import shared.models.requests.BaseRequest;
import shared.models.requests.EnrollCourseRequest;
import shared.models.responses.AddCourseResponse;
import shared.models.responses.EnrollCourseResponse;

public class AddCourseHandler {
    public static AddCourseResponse handleAddCourse(BaseRequest request) {
        AddCourseRequest addCourseRequest = (AddCourseRequest) request;
        if (addCourseRequest.isAuthenticated() && SessionService.getInstance().validateSession(addCourseRequest.getUserId(), addCourseRequest.getSessionToken())) {
            boolean success = CourseService.getInstance().addOrUpdateCourse(addCourseRequest.getInstitutionID(), addCourseRequest.getCourse());
            if (success ) {
                return new AddCourseResponse( MessageStatus.SUCCESS, MessageType.ADD_COURSE, "added");
            } else {
                return new AddCourseResponse( MessageStatus.FAILURE, MessageType.ADD_COURSE, "not added");
            }
        } else {
            return new AddCourseResponse(MessageStatus.FAILURE, MessageType.ENROLL_COURSE, "Invalid credentials");
        }
    }
}
