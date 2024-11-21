package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class LogoutResponse extends BaseResponse{
    private boolean isLoggedOut; // Flag indicating if logout was successful

    public LogoutResponse() {
        super();
    }

    public LogoutResponse(boolean isLoggedOut, String message, MessageStatus messageStatus) {
        super(message, messageStatus, MessageType.LOGOUT);
        this.isLoggedOut = isLoggedOut;
    }

    public boolean isLoggedOut() {
        return isLoggedOut;
    }

    public void setLoggedOut(boolean isLoggedOut) {
        this.isLoggedOut = isLoggedOut;
    }
}
