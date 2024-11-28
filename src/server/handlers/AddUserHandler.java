package server.handlers;

import server.service.ScheduleService;
import server.service.SessionService;
import server.service.UserService;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.AddScheduleRequest;
import shared.models.requests.AddUserRequest;
import shared.models.requests.BaseRequest;
import shared.models.responses.AddScheduleSectionResponse;
import shared.models.responses.AddUserResponse;

import java.io.IOException;

public class AddUserHandler {
    public static AddUserResponse handleAddSchedule(BaseRequest request) throws IOException {
        AddUserRequest addUserRequest = (AddUserRequest) request;
        if (addUserRequest.isAuthenticated() && SessionService.getInstance().validateSession(addUserRequest.getUserID(), addUserRequest.getSessionToken())) {
            boolean success = UserService.getInstance().addUserByInstitution(addUserRequest.getInstitutionID(), addUserRequest.getUserToAdd());
            if (success) {
                return new AddUserResponse("added",MessageStatus.SUCCESS, MessageType.ADD_SCHEDULE);
            } else {
                return new AddUserResponse( "not added", MessageStatus.FAILURE, MessageType.ADD_SCHEDULE);
            }
        } else {
            return new AddUserResponse("Invalid credentials", MessageStatus.FAILURE, MessageType.ADD_SCHEDULE);
        }
    }
}
