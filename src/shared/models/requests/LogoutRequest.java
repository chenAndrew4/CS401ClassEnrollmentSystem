package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class LogoutRequest extends BaseRequest {
    private String userID;
    public LogoutRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, String userID, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
        this.userID = userID;
    }
}
