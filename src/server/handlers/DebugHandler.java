package server.handlers;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public class DebugHandler {
    public static Message handleDebug(Message request) {
        String debugInfo = (String) request.getContent();
        System.out.println("DEBUG: " + debugInfo);
        return new Message(MessageType.DEBUG, "Debug info received").setStatus(MessageStatus.SUCCESS);
    }
}
