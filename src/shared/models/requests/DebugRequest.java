package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DebugRequest extends BaseRequest{
    public DebugRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, boolean isAuthenicated) {
        super(messageType, messageStatus, institutionID, sessionToken, isAuthenicated);
    }
}
