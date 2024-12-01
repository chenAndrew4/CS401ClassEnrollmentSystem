package shared.models.responses;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DeleteScheduleResponse extends BaseResponse {
    private String scheduleId;

    public DeleteScheduleResponse(MessageType messageType, MessageStatus messageStatus, String message, String scheduleId) {
        super(message, messageStatus, messageType);
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return this.scheduleId;
    }
}