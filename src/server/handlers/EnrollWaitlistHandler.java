package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.EnrollWaitlistRequest;
import shared.models.responses.EnrollWaitlistResponse;

public class EnrollWaitlistHandler {
    public static EnrollWaitlistResponse handleEnrollCourse(BaseRequest request, CourseService courseService, SessionService sessionService) 
    {
        EnrollWaitlistRequest enrollCourseRequest = (EnrollWaitlistRequest) request;
        if (sessionService.validateSession(enrollCourseRequest.getStudent().getUserId(), enrollCourseRequest.getSessionToken()))
        {
        	boolean success = courseService.enrollInCourse(enrollCourseRequest.getStudent(), enrollCourseRequest.getSectionId());
        	if (success ) 
        	{
                return new EnrollWaitlistResponse("enrolled", MessageStatus.SUCCESS, MessageType.ENROLL_WAITLIST, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
            } 
        	else 
        	{
        		return new EnrollWaitlistResponse("not enrolled", MessageStatus.FAILURE, MessageType.ENROLL_WAITLIST, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
            }
        } 
        else 
        {
        	return new EnrollWaitlistResponse("Error", MessageStatus.FAILURE, MessageType.ENROLL_WAITLIST, enrollCourseRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollCourseRequest.getStudent().getInstitutionID(), enrollCourseRequest.getSectionId(), enrollCourseRequest.getStudent()));
        }
    }
}