package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.UpdateAssignedCourseRequest;
import shared.models.responses.UpdateAssignedCourseResponse;

public class UpdateAssignedCourseHandler {
    public UpdateAssignedCourseResponse handleUpdateAssignedCourse(BaseRequest request, Log log) {
        UpdateAssignedCourseRequest updateAssignedCourseRequest = (UpdateAssignedCourseRequest) request;

        if (updateAssignedCourseRequest.isAuthenticated() && SessionService.getInstance().validateSession(updateAssignedCourseRequest.getUserId(), updateAssignedCourseRequest.getSessionToken())) {
//            boolean success = CourseService.getInstance().addOrUpdateCourse(updateAssignedCourseRequest.getInstitutionID(), updateAssignedCourseRequest.getCourse());
            boolean success = false;
            if (success){
                return new UpdateAssignedCourseResponse("updated", MessageStatus.SUCCESS, MessageType.UPDATE_COURSE);
            }else{
                return new UpdateAssignedCourseResponse("not updated", MessageStatus.FAILURE, MessageType.UPDATE_COURSE);
            }
        }else{
            return new UpdateAssignedCourseResponse("Invalid credentials", MessageStatus.FAILURE, MessageType.UPDATE_COURSE);
        }
    }
}
