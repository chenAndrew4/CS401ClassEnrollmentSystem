package shared.models.responses;

import server.gui.ServerGUI;
import server.utils.Log;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

public class AddUserResponse extends BaseResponse {
    public AddUserResponse(MessageStatus messageStatus, MessageType messageType, String message) {
        super(message,  messageStatus, messageType);
        Log log = Log.getInstance(ServerGUI.logTextArea);
        log.println("AddUserResponse: " + message);
    }
}

