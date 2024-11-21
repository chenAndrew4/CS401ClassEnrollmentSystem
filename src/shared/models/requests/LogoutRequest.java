package shared.models.requests;

import shared.enums.Institutions;

public class LogoutRequest extends BaseRequest {
    private String userID;
    public LogoutRequest(Institutions institutionID, String sessionToken,String userID) {
        super(institutionID, sessionToken);
        this.userID = userID;
    }
}
