package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.DropWaitlistRequest;
import shared.models.responses.DropWaitlistResponse;

public class DropWaitlistHandler {
    public static DropWaitlistResponse handleDropWaitlist(BaseRequest request, WaitlistService waitlistService, SessionService sessionService) 
    {
        DropWaitlistRequest dropWaitlistRequest = (DropWaitlistRequest) request;
        if (sessionService.validateSession(dropWaitlistRequest.getStudent().getUserId(), dropWaitlistRequest.getSessionToken()))
        {
        	boolean success = waitlistService.removeFromWaitlist(dropWaitlistRequest.getStudent().getInstitutionID(), dropWaitlistRequest.getStudent(), dropWaitlistRequest.getSectionId());
        	if (success) 
        	{
                return new DropWaitlistResponse("Dropped Waitlist", MessageStatus.SUCCESS, MessageType.DROP_WAITLIST, dropWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(dropWaitlistRequest.getStudent().getInstitutionID(), dropWaitlistRequest.getSectionId(), dropWaitlistRequest.getStudent()));
            } 
        	else 
        	{
        		return new DropWaitlistResponse("Not Dropped Waitlist", MessageStatus.FAILURE, MessageType.DROP_WAITLIST, dropWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(dropWaitlistRequest.getStudent().getInstitutionID(), dropWaitlistRequest.getSectionId(), dropWaitlistRequest.getStudent()));
            }
        } 
        else 
        {
        	return new DropWaitlistResponse("Error", MessageStatus.FAILURE, MessageType.DROP_WAITLIST, dropWaitlistRequest.getSectionId(), WaitlistService.getInstance().getWaitlistPositions(dropWaitlistRequest.getStudent().getInstitutionID(), dropWaitlistRequest.getSectionId(), dropWaitlistRequest.getStudent()));
        }
    }
}