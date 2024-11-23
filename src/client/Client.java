package client;

import client.handlers.ResponseCallback;
import shared.enums.MessageStatus;
import shared.models.requests.BaseRequest;
import shared.models.responses.BaseResponse;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class Client {

    private static Client instance;

    private Client() {}

    public static synchronized Client getInstance() {
        if (instance == null) {
            instance = new Client();
        }
        return instance;
    }

    // Generic method to send a request
    public <T extends BaseResponse, R> void sendRequest(BaseRequest request, ResponseCallback<T, R> callback) {
        new Thread(() -> {
            try (Socket socket = new Socket(ClientConfig.SERVER_ADDRESS, ClientConfig.SERVER_PORT);
                 ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
                 ObjectInputStream in = new ObjectInputStream(socket.getInputStream())) {

                // Send the request
                out.writeObject(request);

                // Process the response
                Object response = in.readObject();

                if (response instanceof BaseResponse) {
                    if (((BaseResponse) response).getStatus() == MessageStatus.SUCCESS) {
                        @SuppressWarnings("unchecked")
                        T typedResponse = (T) response;
                        R returnValue = callback.onSuccess(typedResponse);
                    } else {
                        callback.onFailure(((BaseResponse) response).getMessage());
                    }
                } else {
                    callback.onFailure("Unexpected response type.");
                }
            } catch (IOException | ClassNotFoundException e) {
                callback.onFailure("Error: " + e.getMessage());
            }
        }).start();
    }
}
//public class ThreadClient {
//    private final String SERVER_ADDRESS = "localhost";
//    private final int SERVER_PORT = 25800;
//
//    private static ThreadClient instance; // Singleton instance of ThreadClient
//
//    private Student loggedInStudent;// Store the authenticated user
//    private Faculty loggedInfaculty;
//    private Administrator loggedInAdmin;
//
//    private ThreadClient() {
//        // Private constructor for Singleton
//    }
//
//    // Get the singleton instance of ThreadClient
//    public static synchronized ThreadClient getInstance() {
//        if (instance == null) {
//            instance = new ThreadClient();
//        }
//        return instance;
//    }
//
//    public void login(String username, String password, Institutions institutionID, Callback callback) {
//        new Thread(() -> {
//            try (Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT)) {
//                ObjectOutputStream out = new ObjectOutputStream(socket.getOutputStream());
//                ObjectInputStream in = new ObjectInputStream(socket.getInputStream());
//                User curUser = new User();
//                curUser.setUsername(username);
//                curUser.setPassword(password);
//                curUser.setInstitutionID(institutionID);
//
//                // Create and send a login request
//                LoginRequest loginRequest = new LoginRequest(MessageType.LOGIN, null, institutionID, curUser);
//                out.writeObject(loginRequest);
//
//                // Wait for and process the response
//                LoginResponse response = (LoginResponse) in.readObject();
//                if (MessageStatus.SUCCESS == response.getStatus()) {
//                    if (AccountType.Student == response.getUser().getAccountType()) {
//                        loggedInStudent = (Student) response.getUser();
//                        callback.onLoginSuccess(loggedInStudent);
//                    } else if (AccountType.Faculty == response.getUser().getAccountType()) {
//                        loggedInfaculty = (Faculty) response.getUser();
//                        callback.onLoginSuccess(loggedInfaculty);
//                    } else if (AccountType.Administrator == response.getUser().getAccountType()) {
//                        loggedInAdmin = (Administrator) response.getUser();
//                        callback.onLoginSuccess(loggedInAdmin);
//                    } else {
//                        callback.onLoginFailure("Invalid username or password.");
//                    }
//                }
//            } catch (IOException | ClassNotFoundException e) {
//                e.printStackTrace();
//            }
//        }).start();
//    }
//}
