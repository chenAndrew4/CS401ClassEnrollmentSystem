package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Schedule;

public class DeleteScheduleRequest extends BaseRequest {
    private Administrator admin;
	private Schedule schedule;

    public DeleteScheduleRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Schedule schedule) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.schedule = schedule;
    }

    public String getScheduleId() {
        return this.schedule.getSectionID();
    }
}
