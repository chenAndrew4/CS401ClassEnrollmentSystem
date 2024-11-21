package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DeleteScheduleRequest extends BaseRequest {
    private String scheduleId;

    public DeleteScheduleRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String scheduleId, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return scheduleId;
    }
}
