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
import shared.models.requests.UpdateScheduleRequest;
import shared.models.responses.UpdateScheduleResponse;

public class UpdateScheduleHandler {
	public void handleUpdateSchedule(Administrator admin, Schedule oldSchedule, Schedule newSchedule, JPanel parentGUI, final BaseDashboardGUI parentDashboard) {
		// If conditions that we can do here at the client before sending a request to the server
		
		UpdateScheduleRequest updateScheduleRequest = new UpdateScheduleRequest(MessageType.UPDATE_SCHEDULE, null, admin, oldSchedule, newSchedule);
		
		Client.getInstance().sendRequest(updateScheduleRequest, new ResponseCallback<UpdateScheduleResponse, Void>() {
            @Override
            public Void onSuccess(UpdateScheduleResponse updateScheduleResponse) {
            	JOptionPane.showMessageDialog(parentGUI, "Schedule has been successfully updated!", "Update Schedule", JOptionPane.INFORMATION_MESSAGE, new ImageIcon(ClientConfig.SUCCESS_ICON));
                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                	JOptionPane.showMessageDialog(parentGUI, "Schedule could not be updated: " + reason, "Update Schedule Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
                });
            }
        });
	}
}
