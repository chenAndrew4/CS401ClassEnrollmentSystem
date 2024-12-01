package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Schedule;

public class AddScheduleRequest extends BaseRequest {
    private Administrator admin;
	private Schedule schedule;

    public AddScheduleRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Schedule schedule) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.schedule = schedule;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getUserID() {
        return admin.getUserId();
    }

}
