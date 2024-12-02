package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.EnrollCourseRequest;
import shared.models.responses.EnrollCourseResponse;

public class EnrollCourseHandler {
    public static EnrollCourseResponse handleEnrollCourse(BaseRequest request, CourseService courseService, SessionService sessionService) 
    {
        EnrollCourseRequest enrollCourseRequest = (EnrollCourseRequest) request;
        if (sessionService.validateSession(enrollCourseRequest.getStudent().getUserId(), enrollCourseRequest.getSessionToken()))
        {
        	boolean success = courseService.enrollInCourse(enrollCourseRequest.getStudent(), enrollCourseRequest.getSectionId());
        	if (success ) 
        	{
                return new EnrollCourseResponse("enrolled", MessageStatus.SUCCESS, MessageType.ENROLL_COURSE, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
            } 
        	else 
        	{
        		return new EnrollCourseResponse("not enrolled", MessageStatus.FAILURE, MessageType.ENROLL_COURSE, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
            }
        } 
        else 
        {
        	return new EnrollCourseResponse("Error", MessageStatus.FAILURE, MessageType.ENROLL_COURSE, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
        }
    }
}