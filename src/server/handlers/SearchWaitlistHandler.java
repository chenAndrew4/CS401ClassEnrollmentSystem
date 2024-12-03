package server.handlers;

import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.SearchWaitlistRequest;
import shared.models.responses.SearchWaitlistResponse;
import shared.models.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SearchWaitlistHandler {
    public static SearchWaitlistResponse handleSearchCourse(BaseRequest request, WaitlistService waitlistService, SessionService sessionService) 
    {
        SearchWaitlistRequest searchWaitlistRequest = (SearchWaitlistRequest) request;
        if (sessionService.validateSession(searchWaitlistRequest.getStudent().getUserId(), searchWaitlistRequest.getSessionToken()))
        {
        	Map <String, WaitList> courses = waitlistService.getAllWaitlists(searchWaitlistRequest.getStudent().getInstitutionID());
        	if (courses != null) 
        	{
                return new SearchWaitlistResponse("Waitlists Found", MessageStatus.SUCCESS, MessageType.SEARCH_COURSE, courses);
            } 
        	else 
        	{
        		return new SearchWaitlistResponse("No Waitlists Found", MessageStatus.FAILURE, MessageType.SEARCH_COURSE, courses);
            }
        } 
        else 
        {
        	return new SearchWaitlistResponse("Error", MessageStatus.FAILURE, MessageType.SEARCH_COURSE, null);
        }
    }
}