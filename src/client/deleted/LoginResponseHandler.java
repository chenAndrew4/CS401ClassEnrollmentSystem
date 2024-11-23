package client.deleted;

import shared.enums.MessageStatus;
import shared.models.User;
import shared.models.responses.BaseResponse;
import shared.models.responses.LoginResponse;


// after remove client class, no more usage
public class LoginResponseHandler implements ResponseHandler<User>{
    @Override
    public User handleResponse(BaseResponse response) {
        if (response instanceof LoginResponse loginResponse) {
            if (loginResponse.getStatus() == MessageStatus.SUCCESS) {
                System.out.println("Login Successful: " + loginResponse.getMessage());
                User loggedInUser = loginResponse.getUser();
                System.out.println("Welcome, " + loggedInUser.getUsername());
                return loggedInUser;
            } else {
                System.out.println("Login Failed: " + loginResponse.getMessage());
            }
        } else {
            System.out.println("Invalid response type for LoginResponseHandler.");
        }
        return null;
    }
}
