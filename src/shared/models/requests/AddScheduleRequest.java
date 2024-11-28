package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;

public class AddScheduleRequest extends BaseRequest {
    private String sectionId;
    private Schedule schedule;
    private String userID;

    public AddScheduleRequest(MessageType messageType, MessageStatus messageStatus, String userID, Institutions institutionID, String sessionToken, String sectionId, Schedule schedule, boolean isAuthenicated) {
        super(messageType, messageStatus,institutionID, sessionToken, isAuthenicated);
        this.sectionId = sectionId;
        this.schedule = schedule;
        this.userID = userID;
    }

    public String getSectionId() {
        return sectionId;
    }

    public Schedule getSchedule() {
        return schedule;
    }

    public String getUserID() {
        return userID;
    }

}
