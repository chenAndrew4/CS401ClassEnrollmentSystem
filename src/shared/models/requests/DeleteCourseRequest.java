package shared.models.requests;

import shared.enums.Institutions;

public class DeleteCourseRequest extends BaseRequest {
    private String sectionId;

    public DeleteCourseRequest(Institutions institutionID, String sessionToken, String sectionId) {
        super(institutionID, sessionToken);
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
