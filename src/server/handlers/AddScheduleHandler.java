package server.handlers;

import server.service.ScheduleService;
import server.service.SessionService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.AddScheduleRequest;
import shared.models.requests.BaseRequest;
import shared.models.responses.AddScheduleSectionResponse;

public class AddScheduleHandler {
    public static AddScheduleSectionResponse handleAddSchedule(BaseRequest request) {
        AddScheduleRequest addScheduleRequest = (AddScheduleRequest) request;
        if (addScheduleRequest.isAuthenticated() && SessionService.getInstance().validateSession(addScheduleRequest.getUserID(), addScheduleRequest.getSessionToken())) {
            boolean success = ScheduleService.getInstance().addOrUpdateSchedule(addScheduleRequest.getInstitutionID(), addScheduleRequest.getSchedule().getScheduleID(), addScheduleRequest.getSchedule(),addScheduleRequest.getSchedule().getSectionID());
            if (success) {
                return new AddScheduleSectionResponse(MessageStatus.SUCCESS, MessageType.ADD_SCHEDULE, "added");
            } else {
                return new AddScheduleSectionResponse( MessageStatus.FAILURE, MessageType.ADD_SCHEDULE, "not added");
            }
        } else {
            return new AddScheduleSectionResponse(MessageStatus.FAILURE, MessageType.ADD_SCHEDULE, "Invalid credentials");
        }
    }
}
