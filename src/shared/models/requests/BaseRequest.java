package shared.models.requests;

import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.Message;

public abstract class BaseRequest extends Message {
    private Institutions institutionID;
    private String sessionToken;
    private boolean isAuthenicated;

    public BaseRequest(MessageType messageType, MessageStatus messageStatus, Institutions institutionID, String sessionToken, boolean isAuthenicated) {
        super(messageType, messageStatus);
        this.institutionID = institutionID;
        this.sessionToken = sessionToken;
        this.isAuthenicated = isAuthenicated;
    }

    public boolean isAuthenicated() {
        return isAuthenicated;
    }

    public Institutions getInstitutionID() {
        return institutionID;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}