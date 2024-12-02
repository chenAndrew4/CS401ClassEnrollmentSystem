package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.DropCourseRequest;
import shared.models.responses.DropCourseResponse;

public class DropCourseHandler {
    public static DropCourseResponse handleDropCourse(BaseRequest request, CourseService courseService, SessionService sessionService) 
    {
        DropCourseRequest dropCourseRequest = (DropCourseRequest) request;
        if (sessionService.validateSession(dropCourseRequest.getStudent().getUserId(), dropCourseRequest.getSessionToken()))
        {
        	boolean success = courseService.dropCourse(dropCourseRequest.getStudent(), dropCourseRequest.getSectionId(), true, dropCourseRequest.getStudent().getGrade());
        	if (success ) 
        	{
                return new DropCourseResponse(MessageStatus.SUCCESS, MessageType.DROP_COURSE, "dropped", dropCourseRequest.getCourseId(), dropCourseRequest.getSectionId());
            } 
        	else 
        	{
        		return new DropCourseResponse(MessageStatus.FAILURE, MessageType.DROP_COURSE, "not dropped", dropCourseRequest.getCourseId(), dropCourseRequest.getSectionId());
            }
        } 
        else 
        {
            return new DropCourseResponse(MessageStatus.SUCCESS, MessageType.DROP_COURSE, "Error", dropCourseRequest.getCourseId(), dropCourseRequest.getSectionId());
        }
    }
}
