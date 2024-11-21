package shared.models.requests;

import shared.enums.Institutes;

public class DeleteWaitlistRequest extends BaseRequest {
    private String sectionId;

    public DeleteWaitlistRequest(Institutes instituteID, String sessionToken, String sectionId) {
        super(instituteID, sessionToken);
        this.sectionId = sectionId;
    }

    public String getSectionId() {
        return sectionId;
    }
}
