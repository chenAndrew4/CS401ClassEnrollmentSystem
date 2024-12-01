package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;
import shared.models.requests.AddUserRequest;
import shared.models.responses.AddUserResponse;

public class AddUserHandler {
	public void handleAddUser(Administrator currentUser, User userToAdd, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		if (userToAdd.getUsername().isBlank() || userToAdd.getPassword().isBlank()) {
			JOptionPane.showMessageDialog(parentGUI, "Username or password cannot be empty.", "Add User Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
			return;
		}
		AddUserRequest addUserRequest = new AddUserRequest(MessageType.ADD_USER, null, currentUser, userToAdd);
		
		Client.getInstance().sendRequest(addUserRequest, new ResponseCallback<AddUserResponse, Void>() {
            @Override
            public Void onSuccess(AddUserResponse addUserResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "User added successfully!", "Add User", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "User could not be added: " + reason, "Add User Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
