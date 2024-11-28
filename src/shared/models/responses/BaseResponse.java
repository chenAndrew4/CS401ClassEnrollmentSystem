package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public class BaseResponse extends Message {
    private String message;

    public BaseResponse() {
        super();
    }

    public BaseResponse(String message, MessageStatus messageStatus, MessageType messageType) {
        super(messageType, messageStatus);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
