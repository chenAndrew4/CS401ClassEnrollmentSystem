package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateCourseResponse extends BaseResponse {
    public UpdateCourseResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message,  messageStatus, messageType);
    }
}