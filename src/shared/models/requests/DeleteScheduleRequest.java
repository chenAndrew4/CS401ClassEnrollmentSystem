package shared.models.requests;

import shared.enums.Institutes;

public class DeleteScheduleRequest extends BaseRequest {
    private String scheduleId;

    public DeleteScheduleRequest(Institutes instituteID, String sessionToken, String scheduleId) {
        super(instituteID, sessionToken);
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return scheduleId;
    }
}
