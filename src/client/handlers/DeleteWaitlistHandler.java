package client.handlers;

import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

import client.Client;
import client.ClientConfig;
import client.gui.dashboard.BaseDashboardGUI;
import client.handlers.ResponseCallback;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.WaitList;
import shared.models.requests.DeleteWaitlistRequest;
import shared.models.responses.DeleteWaitlistResponse;

public class DeleteWaitlistHandler {
	public void handleDeleteWaitlist(Administrator admin, WaitList waitlist, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		DeleteWaitlistRequest deleteWaitlistRequest = new DeleteWaitlistRequest(MessageType.DELETE_WAITLIST, null, admin, waitlist);
		
		Client.getInstance().sendRequest(deleteWaitlistRequest, new ResponseCallback<DeleteWaitlistResponse, Void>() {
            @Override
            public Void onSuccess(DeleteWaitlistResponse deleteWaitlistResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Waitlist has been successfully added!", "Add Waitlist", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Waitlist could not be created: " + reason, "Add Waitlist Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
