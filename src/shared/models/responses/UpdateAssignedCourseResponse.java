package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class UpdateAssignedCourseResponse extends BaseResponse{

    public UpdateAssignedCourseResponse(String message, MessageStatus messageStatus, MessageType messageType) {
        super(message, messageStatus, messageType);
    }
}
