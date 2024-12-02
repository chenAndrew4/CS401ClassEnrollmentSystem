package server.handlers;

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

public class SearchCourseHandler {
    public static SearchCourseResponse handleSearchCourse(BaseRequest request, CourseService courseService, SessionService sessionService) 
    {
        SearchCourseRequest searchCourseRequest = (SearchCourseRequest) request;
        if (sessionService.validateSession(searchCourseRequest.getStudent().getUserId(), searchCourseRequest.getSessionToken()))
        {
        	Map <String, Course> courses = courseService.getAllCourses(searchCourseRequest.getStudent().getInstitutionID());
        	if (courses != null) 
        	{
                return new SearchCourseResponse("Courses Found", MessageStatus.SUCCESS, MessageType.SEARCH_COURSE, courses);
            } 
        	else 
        	{
        		return new SearchCourseResponse("No Courses Found", MessageStatus.FAILURE, MessageType.SEARCH_COURSE, courses);
            }
        } 
        else 
        {
        	return new SearchCourseResponse("Error", MessageStatus.FAILURE, MessageType.SEARCH_COURSE, null);
        }
    }
}