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
        EnrollWaitlistRequest enrollWaitlistRequest = (EnrollWaitlistRequest) request;
        if (sessionService.validateSession(enrollWaitlistRequest.getStudent().getUserId(), enrollWaitlistRequest.getSessionToken()))
        {
        	boolean success = courseService.enrollInCourse(enrollWaitlistRequest.getStudent(), enrollWaitlistRequest.getSectionId());
        	if (success ) 
        	{
                return new EnrollWaitlistResponse("enrolled", MessageStatus.SUCCESS, MessageType.ENROLL_WAITLIST, enrollWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollWaitlistRequest.getStudent().getInstitutionID(), enrollWaitlistRequest.getSectionId(), enrollWaitlistRequest.getStudent()));
            } 
        	else 
        	{
        		return new EnrollWaitlistResponse("not enrolled", MessageStatus.FAILURE, MessageType.ENROLL_WAITLIST, enrollWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollWaitlistRequest.getStudent().getInstitutionID(), enrollWaitlistRequest.getSectionId(), enrollWaitlistRequest.getStudent()));
            }
        } 
        else 
        {
        	return new EnrollWaitlistResponse("Error", MessageStatus.FAILURE, MessageType.ENROLL_WAITLIST, enrollWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(enrollWaitlistRequest.getStudent().getInstitutionID(), enrollWaitlistRequest.getSectionId(), enrollWaitlistRequest.getStudent()));
        }
    }
}