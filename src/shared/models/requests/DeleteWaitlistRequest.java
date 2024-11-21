package shared.models.requests;

import shared.enums.Institutions;

public class DeleteWaitlistRequest extends BaseRequest {
    private String sectionId;

    public DeleteWaitlistRequest(Institutions institutionID, String sessionToken, String sectionId) {
        super(institutionID, sessionToken);
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
