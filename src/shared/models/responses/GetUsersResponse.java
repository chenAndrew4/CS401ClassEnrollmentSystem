package shared.models.responses;

import java.util.List;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Schedule;
import shared.models.User;

public class GetUsersResponse extends BaseResponse {
    List<User> users;
    
    public GetUsersResponse(MessageType messageType, MessageStatus messageStatus, List<User> users, String message) {
        super(message, messageStatus, messageType);
        this.users = users;
    }
    public List<User> getUsers() {
        return users;
    }
}
