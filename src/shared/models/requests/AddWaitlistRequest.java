package shared.models.requests;

import shared.enums.Institutions;

public class AddWaitlistRequest extends BaseRequest {
    private String sectionId;
    private int maxWaitlistSize;

    public AddWaitlistRequest(Institutions institutionID, String sessionToken, String sectionId, int maxWaitlistSize) {
        super(institutionID, sessionToken);
        this.sectionId = sectionId;
        this.maxWaitlistSize = maxWaitlistSize;
    }

    public String getSectionId() {
        return sectionId;
    }

    public int getMaxWaitlistSize() {
        return maxWaitlistSize;
    }
}
