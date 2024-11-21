package shared.models.requests;

import shared.enums.Institutions;

public class DeleteScheduleRequest extends BaseRequest {
    private String scheduleId;

    public DeleteScheduleRequest(Institutions institutionID, String sessionToken, String scheduleId) {
        super(institutionID, sessionToken);
        this.scheduleId = scheduleId;
    }

    public String getScheduleId() {
        return scheduleId;
    }
}
