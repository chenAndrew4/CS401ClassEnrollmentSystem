package shared.models.requests;

import shared.enums.Institutions;

import java.io.Serializable;

public abstract class BaseRequest implements Serializable {
    private Institutions institutionID;
    private String sessionToken;


    public BaseRequest(Institutions institutionID, String sessionToken) {
        this.institutionID = institutionID;
        this.sessionToken = sessionToken;
    }

    public Institutions getInstitutionID() {
        return institutionID;
    }

    public String getSessionToken() {
        return sessionToken;
    }
}