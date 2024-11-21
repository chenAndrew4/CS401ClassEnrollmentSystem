package shared.models.requests;

import shared.enums.Institutes;

import java.io.Serializable;

public abstract class BaseRequest implements Serializable {
    private Institutes instituteID;
    private String sessionToken;


    public BaseRequest(Institutes instituteID, String sessionToken) {
        this.instituteID = instituteID;
        this.sessionToken = sessionToken;
    }

    public Institutes getInstituteID() {
        return instituteID;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}