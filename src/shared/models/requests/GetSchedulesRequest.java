package shared.models.requests;

import client.Client;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.User;

import javax.swing.*;

public class GetSchedulesRequest extends BaseRequest {
    private User user;
    public GetSchedulesRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, User user) {
        super(messageType, messageStatus, institutionID, null, false);
        this.user = user;
    }
//    public GetSchedulesRequest(MessageType messageType, Object o, Institutions institution, User currentUser) {
//    }
    public User getUser() {
    	return this.user;
    }
}