package client;

import shared.enums.AccountType;
import shared.models.User;

public interface Callback {
    void onLoginSuccess(User user); // Pass the user's role (e.g., Student, Faculty, Admin)
    void onLoginFailure(String reason); // Pass the failure reason
}
