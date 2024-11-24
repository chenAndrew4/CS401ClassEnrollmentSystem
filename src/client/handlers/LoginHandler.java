package client.handlers;

import client.Client;
import client.gui.login.BaseLoginGUI;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.FacultyDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Faculty;
import shared.models.Student;
import shared.models.User;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import javax.swing.*;

public class LoginHandler {
    public void handleLogin(String username, String password, Institutions institution, JFrame loginGUI) {
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(loginGUI, "Username or password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        User currentUser = new User(institution);
        currentUser.setUsername(username);
        currentUser.setPassword(password);
        currentUser.setInstitutionID(institution);
        LoginRequest loginRequest = new LoginRequest(MessageType.LOGIN, null, institution, currentUser);

        Client.getInstance().sendRequest(loginRequest, new ResponseCallback<LoginResponse, Void>() {
            @Override
            public Void onSuccess(LoginResponse loginResponse) {
                SwingUtilities.invokeLater(() -> {
                    openDashboard(loginResponse.getUser());
                    loginGUI.dispose(); // Close LoginGUI
                });
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                    JLabel statusLabel = ((BaseLoginGUI) loginGUI).getStatusLabel();
                    statusLabel.setText("Login failed: " + reason);
                });
            }
        });
    }

    private void openDashboard(User user) {
        SwingUtilities.invokeLater(() -> {
            switch (user.getAccountType()) {
                case Student -> {
                    if (user instanceof Student) {
                        new StudentDashboardGUI((Student) user);
                    }
                }
                case Faculty -> {
                    if (user instanceof Faculty) {
                        new FacultyDashboardGUI((Faculty) user);
                    }
                }
                case Administrator -> {
                    if (user instanceof Administrator) {
                        new AdminDashboardGUI((Administrator) user);
                    }
                }
                default -> JOptionPane.showMessageDialog(null, "Unknown Account type: " + user.getAccountType(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

}
