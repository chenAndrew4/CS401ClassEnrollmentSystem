package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class AddUserResponse extends BaseResponse {
    public AddUserResponse(String message, MessageStatus messageStatus, MessageType messageType) {
        super(message,  messageStatus, messageType);
    }
}

