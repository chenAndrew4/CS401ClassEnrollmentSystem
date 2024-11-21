package server.handlers;

import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.requests.BaseRequest;
import shared.models.requests.DebugRequest;
import shared.models.responses.BaseResponse;

public class DebugHandler {
    public static BaseResponse handleDebug(BaseRequest request) {
        DebugRequest debugRequest = (DebugRequest) request;
//        System.out.println("DEBUG: " + debugInfo);
        return null;
    }
}
