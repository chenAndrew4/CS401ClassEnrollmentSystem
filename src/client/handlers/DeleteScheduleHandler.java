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
import shared.models.Schedule;
import shared.models.requests.DeleteScheduleRequest;
import shared.models.responses.DeleteScheduleResponse;

public class DeleteScheduleHandler {
	public void handleDeleteSchedule(Administrator admin, Schedule schedule, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		DeleteScheduleRequest deleteScheduleRequest = new DeleteScheduleRequest(MessageType.DELETE_SCHEDULE, null, admin, schedule);
		
		Client.getInstance().sendRequest(deleteScheduleRequest, new ResponseCallback<DeleteScheduleResponse, Void>() {
            @Override
            public Void onSuccess(DeleteScheduleResponse deleteScheduleResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Schedule has been successfully deleted!", "Delete Schedule", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Schedule could not be deleted: " + reason, "Delete Schedule Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
