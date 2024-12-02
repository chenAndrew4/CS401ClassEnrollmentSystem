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


public class SearchCourseResponse extends BaseResponse 
{
	private Map<String, Course> courses;
    public SearchCourseResponse(String message, MessageStatus messageStatus, MessageType messageType, Map<String, Course> courses) 
    {
        super(message,  messageStatus, messageType);
        this.courses = courses;
    }
    
    public Map<String, Course> getCourses()
    {
    	return courses;
    }
}

