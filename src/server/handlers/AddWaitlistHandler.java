package server.handlers;

import java.io.IOException;

import server.service.SessionService;
import server.service.WaitlistService;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.requests.AddWaitlistRequest;
import shared.models.requests.BaseRequest;
import shared.models.responses.AddWaitlistResponse;

public class AddWaitlistHandler {
    public static AddWaitlistResponse handleAddWaitlist(BaseRequest request, Log log) throws IOException {
        AddWaitlistRequest addWaitlistRequest = (AddWaitlistRequest) request;
        if (addWaitlistRequest.isAuthenticated() && SessionService.getInstance().validateSession(addWaitlistRequest.getAdmin().getUserId(), addWaitlistRequest.getSessionToken())) {
            boolean success = WaitlistService.getInstance().addOrUpdateWaitlist(addWaitlistRequest.getAdmin().getInstitutionID(), addWaitlistRequest.getWaitlist().getSectionID(), addWaitlistRequest.getWaitlist());
            if (success) {
                return new AddWaitlistResponse(MessageStatus.SUCCESS, MessageType.ADD_WAITLIST, "Waitlist has been created!");
            } else {
                return new AddWaitlistResponse(MessageStatus.FAILURE, MessageType.ADD_WAITLIST, "Waitlist could not be created!");
            }
        } else {
            return new AddWaitlistResponse(MessageStatus.FAILURE, MessageType.ADD_WAITLIST, "Invalid credentials");
        }
    }
}
