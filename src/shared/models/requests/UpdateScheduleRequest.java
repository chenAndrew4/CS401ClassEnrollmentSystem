package shared.models.requests;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Schedule;

public class UpdateScheduleRequest extends BaseRequest {
    private Administrator admin;
	private Schedule newSchedule;
	private Schedule oldSchedule;

    public UpdateScheduleRequest(MessageType messageType, MessageStatus messageStatus, Administrator admin, Schedule newSchedule, Schedule oldSchedule) {
        super(messageType, messageStatus, admin.getInstitutionID(), admin.getSessionToken(), admin.isAuthenticated());
        this.admin = admin;
        this.newSchedule = newSchedule;
        this.oldSchedule = oldSchedule;
    }

    public Schedule getNewSchedule() {
        return this.newSchedule;
    }
    
    public Schedule getOldSchedule() {
        return this.oldSchedule;
    }
}