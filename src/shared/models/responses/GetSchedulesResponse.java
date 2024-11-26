package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;

import java.util.List;

public class GetSchedulesResponse extends BaseResponse {
    List<Schedule> schedules;
    public GetSchedulesResponse(MessageType messageType, MessageStatus messageStatus, List<Schedule> schedules, String message) {
        super(message, messageStatus, messageType);
        this.schedules = schedules;
    }
    public List<Schedule> getSchedules() {
        return schedules;
    }
}
