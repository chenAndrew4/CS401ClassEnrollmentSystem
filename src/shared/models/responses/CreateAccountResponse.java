package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class CreateAccountResponse extends BaseResponse {
    private String userId;

    public CreateAccountResponse( MessageType messageType,MessageStatus messageStatus,  String message, String userId) {
        super(message, messageStatus, messageType);
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
