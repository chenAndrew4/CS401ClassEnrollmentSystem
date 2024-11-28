package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Course;
import shared.models.requests.AddCourseSectionRequest;
import shared.models.requests.BaseRequest;
import shared.models.responses.AddCourseSectionResponse;

public class AddCourseSectionHandler {
    public static AddCourseSectionResponse handleAddCourseSection(BaseRequest request) {
        AddCourseSectionRequest addCourseSectionRequest = (AddCourseSectionRequest) request;
        if (addCourseSectionRequest.isAuthenticated() && SessionService.getInstance().validateSession(addCourseSectionRequest.getUserID(), addCourseSectionRequest.getSessionToken())) {
            Course course = CourseService.getInstance().getCourseByCourseID(addCourseSectionRequest.getInstitutionID(), addCourseSectionRequest.getCourseID());
            course.getSections().add(addCourseSectionRequest.getSection());
            boolean success = CourseService.getInstance().addOrUpdateCourse(addCourseSectionRequest.getInstitutionID(), course);
            if (success ) {
                return new AddCourseSectionResponse( MessageStatus.SUCCESS, MessageType.ADD_COURSESECTION, "added");
            } else {
                return new AddCourseSectionResponse( MessageStatus.FAILURE, MessageType.ADD_COURSE, "not added");
            }
        } else {
            return new AddCourseSectionResponse(MessageStatus.FAILURE, MessageType.ENROLL_COURSE, "Invalid credentials");
        }
    }
}
