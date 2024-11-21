package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;

public class AddScheduleRequest extends BaseRequest {
    private String sectionId;
    private Schedule schedule;

    public AddScheduleRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String sectionId, Schedule schedule, boolean isAuthenicated) {
        super(messageType, messageStatus,institutionID, sessionToken, isAuthenicated);
        this.sectionId = sectionId;
        this.schedule = schedule;
    }

    public String getSectionId() {
        return sectionId;
    }

    public Schedule getSchedule() {
        return schedule;
    }
}
