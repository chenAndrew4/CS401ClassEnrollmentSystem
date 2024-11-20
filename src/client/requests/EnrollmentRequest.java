package client.requests;

import shared.models.User;

import java.io.Serializable;

public class EnrollmentRequest implements Serializable {
    private String courseId;
    private String sessionToken; // To authenticate the user
    private String userId;

    public EnrollmentRequest(String courseId, String userId, String sessionToken) {
        this.courseId = courseId;
        this.sessionToken = sessionToken;
        this.userId = userId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCourseId() {
        return courseId;
    }

    public void setCourseId(String courseId) {
        this.courseId = courseId;
    }

    public String getSessionToken() {
        return sessionToken;
    }

    public void setSessionToken(String sessionToken) {
        this.sessionToken = sessionToken;
    }
}
