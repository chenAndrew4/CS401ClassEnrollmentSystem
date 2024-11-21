package shared.models.requests;

import shared.enums.Institutes;
import shared.models.Schedule;

public class AddScheduleRequest extends BaseRequest {
    private String sectionId;
    private Schedule schedule;

    public AddScheduleRequest(Institutes instituteID, String sessionToken, String sectionId, Schedule schedule) {
        super(instituteID, sessionToken);
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
