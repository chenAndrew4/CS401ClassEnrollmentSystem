package shared.models.requests;

import shared.enums.Institutions;
import shared.models.Schedule;

public class AddScheduleRequest extends BaseRequest {
    private String sectionId;
    private Schedule schedule;

    public AddScheduleRequest(Institutions institutionID, String sessionToken, String sectionId, Schedule schedule) {
        super(institutionID, sessionToken);
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
