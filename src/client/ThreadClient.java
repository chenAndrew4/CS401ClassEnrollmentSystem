package client;

import client.gui.CSUEB.LoginGUI;
import shared.enums.AccountType;
import shared.enums.Institutions;
import shared.enums.MessageStatus;
import shared.enums.MessageType;
import shared.models.*;
import shared.models.requests.BaseRequest;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.StubNotFoundException;

public class ThreadClient {
    private final String SERVER_ADDRESS = "localhost";
    private final int SERVER_PORT = 25800;

    private static ThreadClient instance; // Singleton instance of ThreadClient

    private Student loggedInStudent;// Store the authenticated user
    private Faculty loggedInfaculty;
    private Administrator loggedInAdmin;

    private ThreadClient() {
        // Private constructor for Singleton
    }

    // Get the singleton instance of ThreadClient
    public static synchronized ThreadClient getInstance() {
        if (instance == null) {
            instance = new ThreadClient();
        }
        return instance;
    }

    public void login(String username, String password, Institutions institutionID, Callback callback) {
        new Thread(() -> {
            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
                User curUser = new User();
                curUser.setUsername(username);
                curUser.setPassword(password);
                curUser.setInstitutionID(institutionID);

                // Create and send a login request
                LoginRequest loginRequest = new LoginRequest(MessageType.LOGIN, null, institutionID, curUser);
                out.writeObject(loginRequest);

                // Wait for and process the response
                LoginResponse response = (LoginResponse) in.readObject();
                if (MessageStatus.SUCCESS == response.getStatus()) {
                    if (AccountType.Student == response.getUser().getAccountType()) {
                        loggedInStudent = (Student) response.getUser();
                        callback.onLoginSuccess(loggedInStudent);
                    } else if (AccountType.Faculty == response.getUser().getAccountType()) {
                        loggedInfaculty = (Faculty) response.getUser();
                        callback.onLoginSuccess(loggedInfaculty);
                    } else if (AccountType.Administrator == response.getUser().getAccountType()) {
                        loggedInAdmin = (Administrator) response.getUser();
                        callback.onLoginSuccess(loggedInAdmin);
                    } else {
                        callback.onLoginFailure("Invalid username or password.");
                    }
                }
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }).start();
    }
}
