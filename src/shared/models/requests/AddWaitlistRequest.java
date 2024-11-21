package shared.models.requests;

import shared.enums.Institutes;

public class AddWaitlistRequest extends BaseRequest {
    private String sectionId;
    private int maxWaitlistSize;

    public AddWaitlistRequest(Institutes instituteID, String sessionToken, String sectionId, int maxWaitlistSize) {
        super(instituteID, sessionToken);
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
