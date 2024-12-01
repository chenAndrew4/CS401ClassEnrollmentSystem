package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class AddUserResponse extends BaseResponse {
    public AddUserResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message,  messageStatus, messageType);
    }
}

