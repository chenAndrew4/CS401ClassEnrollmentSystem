package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateUserResponse extends BaseResponse {
    public UpdateUserResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message,  messageStatus, messageType);
    }
}