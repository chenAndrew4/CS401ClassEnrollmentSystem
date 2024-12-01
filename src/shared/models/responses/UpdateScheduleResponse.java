package shared.models.responses;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateScheduleResponse extends BaseResponse {
    private String scheduleId;

    public UpdateScheduleResponse(MessageType messageType, MessageStatus messageStatus, String message, String scheduleId) {
        super(message, messageStatus, messageType);
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return this.scheduleId;
    }
}