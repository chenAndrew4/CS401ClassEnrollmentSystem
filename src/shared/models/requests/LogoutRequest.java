package shared.models.requests;

import shared.enums.Institutes;

public class LogoutRequest extends BaseRequest {
    private String userID;
    public LogoutRequest(Institutes instituteID, String sessionToken,String userID) {
        super(instituteID, sessionToken);
        this.userID = userID;
    }
}
