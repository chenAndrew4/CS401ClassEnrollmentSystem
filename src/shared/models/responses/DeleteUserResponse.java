package shared.models.responses;

import shared.enums.MessageStatus;
import shared.enums.MessageType;

public class DeleteUserResponse extends BaseResponse {
    private String deletedUsername; // The username of the deleted user

    public DeleteUserResponse(MessageStatus messageStatus, MessageType messageType, String message, String deletedUsername) {
        super(message,  messageStatus, messageType);
        this.deletedUsername = deletedUsername;
    }

    public String getDeletedUsername() {
        return deletedUsername;
    }

    public void setDeletedUsername(String deletedUsername) {
        this.deletedUsername = deletedUsername;
    }
}

