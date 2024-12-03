package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import server.service.CourseService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.SearchCourseRequest;
import shared.models.responses.SearchCourseResponse;
import shared.models.*;
import java.util.List;
import java.util.Map;
import java.util.Objects;


public class SearchWaitlistResponse extends BaseResponse 
{
	private Map<String, WaitList> courses;
    public SearchWaitlistResponse(String message, MessageStatus messageStatus, MessageType messageType, Map<String, WaitList> courses) 
    {
        super(message,  messageStatus, messageType);
        this.courses = courses;
    }
    
    public Map<String, WaitList> getCourses()
    {
    	return courses;
    }
}

