package server;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SessionService {
    private static SessionService instance;
    private final Map<String, Session> activeSessions;

    private SessionService() {
        this.activeSessions = new HashMap<>();
    }

    public static synchronized SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }


    // Create a new session for a user and return the session token
    public String createSession(String id) {
        String userId = id;
        String token = generateToken();
        int expiryTime = generateExpiryTime();

        activeSessions.put(userId, new Session(token, expiryTime));
        return token;
    }

    // Validate the session for a given userId and token
    public boolean validateSession(String userId, String token) {
        if (!activeSessions.containsKey(userId)) {
            return false;
        }

        Session session = activeSessions.get(userId);
        return session.token.equals(token) && !isSessionExpired(userId);
    }

    // Terminate the session for a given userId
    public void terminateSession(String userId) {
        activeSessions.remove(userId);
    }

    // Check if the session for a given userId is expired
    public boolean isSessionExpired(String userId) {
        if (!activeSessions.containsKey(userId)) {
            return true;
        }

        Session session = activeSessions.get(userId);
        return Instant.now().getEpochSecond() > session.expiryTime;
    }

    // Placeholder for saving session data to a database
    public void commitToDb() {
        // Save activeSessions to persistent storage
        // This is a placeholder for actual database interaction
    }

    // Helper method to generate a unique token
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Helper method to generate an expiry time (current time + 30 minutes)
    private int generateExpiryTime() {
        return (int) (Instant.now().getEpochSecond() + 30 * 60);
    }

    // Inner class to represent a session
    private static class Session {
        private final String token;
        private final int expiryTime;

        public Session(String token, int expiryTime) {
            this.token = token;
            this.expiryTime = expiryTime;
        }
    }
}

