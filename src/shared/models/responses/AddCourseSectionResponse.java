package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class AddCourseSectionResponse extends BaseResponse{
    public AddCourseSectionResponse(MessageStatus messageStatus, MessageType messageType, String added) {
        super(added, messageStatus, messageType);
    }
}
