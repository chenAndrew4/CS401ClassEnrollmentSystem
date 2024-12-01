package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.User;
import shared.models.requests.UpdateUserRequest;
import shared.models.responses.AddUserResponse;
import shared.models.responses.UpdateUserResponse;

public class UpdateUserHandler {
	public void handleUpdateUser(Administrator currentUser, User userToUpdate, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions to control what we can update and what cannot be updated.
		// e.g. We cannot update a user's id or date of creation since it's generally agreed upon that those pieces of information never change throughout the user lifecycle.
		// if (){}
		UpdateUserRequest updateUserRequest = new UpdateUserRequest(MessageType.UPDATE_USER, null, currentUser, userToUpdate);
        
		Client.getInstance().sendRequest(updateUserRequest, new ResponseCallback<UpdateUserResponse, Void>() {
            @Override
            public Void onSuccess(UpdateUserResponse updateUserResponse) {
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