package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class AddCourseResponse extends BaseResponse {

    public AddCourseResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message,  messageStatus, messageType);
    }
}

