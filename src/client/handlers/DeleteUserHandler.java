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
import shared.models.requests.DeleteUserRequest;
import shared.models.responses.DeleteUserResponse;

public class DeleteUserHandler {
	public void handleDeleteUser(Administrator currentUser, User userToDelete, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions to control what we can delete and what cannot be deleted. e.g. we cannot delete an admin since we can lock ourself out from the app
		// if (){}
		DeleteUserRequest deleteUserRequest = new DeleteUserRequest(MessageType.DELETE_USER, null, currentUser, userToDelete);
        
		Client.getInstance().sendRequest(deleteUserRequest, new ResponseCallback<DeleteUserResponse, Void>() {
            @Override
            public Void onSuccess(DeleteUserResponse deleteUserResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "User deleted successfully!", "Delete User", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "User could not be deleted: " + reason, "Delete User Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
