package server.handlers;

import server.service.CourseService;
import server.service.ScheduleService;
import server.service.SessionService;
import server.service.WaitlistService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.requests.GetSchedulesRequest;
import shared.models.responses.GetSchedulesResponse;
import shared.models.*;
import java.util.List;

public class GetSchedulesHandler {
    public static GetSchedulesResponse handleGetSchedule(BaseRequest request, ScheduleService scheduleService, SessionService sessionService) 
    {
        GetSchedulesRequest getScheduleRequest = (GetSchedulesRequest) request;
        if (sessionService.validateSession(getScheduleRequest.getUserID(), getScheduleRequest.getSessionToken()))
        {
        	List<Schedule> schedule = scheduleService.getSchedulesByCourses(getScheduleRequest.getEnrolledCourses(), getScheduleRequest.getInstitutionID());
        	if (schedule != null) 
        	{
                return new GetSchedulesResponse(MessageType.GET_SCHEDULES, MessageStatus.SUCCESS, schedule, "Schedule Found");
            } 
        	else 
        	{
        		return new GetSchedulesResponse(MessageType.GET_SCHEDULES, MessageStatus.FAILURE, schedule, "No Schedule Found");
            }
        } 
        else 
        {
        	return new GetSchedulesResponse(MessageType.GET_SCHEDULES, MessageStatus.FAILURE, null, "Error");
        }
    }
}