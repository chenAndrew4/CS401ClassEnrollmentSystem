package server.service;

import server.dataManagers.SessionDataManager;

import java.time.Instant;
import java.util.UUID;
import server.ServerManager;

public class SessionService {

    private static SessionService instance;
    private final SessionDataManager sessionDataManager;

    private SessionService() {
        this.sessionDataManager = SessionDataManager.getInstance();
    }

    public static synchronized SessionService getInstance() {
        if (instance == null) {
            instance = new SessionService();
        }
        return instance;
    }

    // Create a new session for a user and return the session token
    public synchronized String createSession(String userId) {
        String token = generateToken();
        int expiryTime = generateExpiryTime();

        String sessionData = token + "," + expiryTime; // Combine token and expiryTime as a string
        sessionDataManager.addOrUpdateSession(userId, sessionData);
        return token;
    }

    // Validate the session for a given userId and token
    public synchronized boolean validateSession(String userId, String token) {
        String sessionData = sessionDataManager.getSessionData(userId);
        if (sessionData == null) {
            return false;
        }

        String[] parts = sessionData.split(",");
        if (parts.length != 2) {
            return false; // Invalid session data
        }

        String storedToken = parts[0];
        int expiryTime = Integer.parseInt(parts[1]);

        return storedToken.equals(token) && Instant.now().getEpochSecond() <= expiryTime;
    }

    // Terminate the session for a given userId
    public synchronized void terminateSession(String userId) {
        sessionDataManager.removeSession(userId);
    }

    // Check if the session for a given userId is expired
    public synchronized boolean isSessionExpired(String userId) {
        String sessionData = sessionDataManager.getSessionData(userId);
        if (sessionData == null) {
            return true;
        }

        String[] parts = sessionData.split(",");
        if (parts.length != 2) {
            return true;
        }

        int expiryTime = Integer.parseInt(parts[1]);
        return Instant.now().getEpochSecond() > expiryTime;
    }

    // Helper method to generate a unique token
    private String generateToken() {
        return UUID.randomUUID().toString();
    }

    // Helper method to generate an expiry time (current time + 30 minutes)
    private int generateExpiryTime() {
        return (int) (Instant.now().getEpochSecond() + ServerManager.getSessionTimeout() * 60);
    }
}

