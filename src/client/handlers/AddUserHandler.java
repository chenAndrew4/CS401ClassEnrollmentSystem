package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import client.gui.login.BaseLoginGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Student;
import shared.models.User;
import shared.models.requests.AddUserRequest;
import shared.models.responses.LoginResponse;

public class AddUserHandler {
	public void handleAddUser(Administrator currentUser, Institutions institution, User userToAdd, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		if (userToAdd.getUsername().isBlank() || userToAdd.getPassword().isBlank()) {
			JOptionPane.showMessageDialog(parentGUI, "Username or password cannot be empty.", "Add User Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
			return;
		}
		AddUserRequest addUserRequest = new AddUserRequest(MessageType.ADD_USER, null, currentUser.getUserId(), institution, currentUser.getSessionToken(), currentUser.isAuthenticated(), userToAdd, currentUser);
        
//		Client.getInstance().sendRequest(addUserRequest, new ResponseCallback<AddUserRequest, Void>() {
//            @Override
//            public Void onSuccess(AddUserRequest addUserResponse) {
//            	JOptionPane.showMessageDialog(parentGUI, "User added successfully!", "Add User", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
//                return null;
//            }
//
//            @Override
//            public void onFailure(String reason) {
//                SwingUtilities.invokeLater(() -> {
//                	JOptionPane.showMessageDialog(parentGUI, "User could not be added: " + reason, "Add User Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
//                });
//            }
//        });
	}
	
}
