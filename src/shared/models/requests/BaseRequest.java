package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public abstract class BaseRequest extends Message {
    private Institutions institutionID;
    private String sessionToken;
    private boolean isAuthenticated;

    public BaseRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, boolean isAuthenticated) {
        super(messageType, messageStatus);
        this.institutionID = institutionID;
        this.sessionToken = sessionToken;
        this.isAuthenticated = isAuthenticated;
    }

    public boolean isAuthenticated() {
        return isAuthenticated;
    }

    public Institutions getInstitutionID() {
        return institutionID;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}