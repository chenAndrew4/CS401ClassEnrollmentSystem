package shared.models.responses;

import shared.enums.MessageStatus;

import java.io.Serializable;

public class BaseResponse implements Serializable {
    private String message; // Human-readable message for the client

    public BaseResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
