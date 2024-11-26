package client.handlers;

import client.Client;
import client.gui.ViewSchedulesGUI;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.BaseDashboardGUI;
import client.gui.dashboard.FacultyDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Faculty;
import shared.models.Schedule;
import shared.models.User;
import shared.models.requests.GetSchedulesRequest;
import shared.models.responses.GetSchedulesResponse;

import javax.swing.*;
import java.util.List;

public class GetSchedulesHandler {
    public void handleGetSchedules(User currentUser, Institutions institution, JPanel parentGUI, BaseDashboardGUI parentDashboard) {
        // Create the GetSchedulesRequest
        GetSchedulesRequest getSchedulesRequest = new GetSchedulesRequest(MessageType.GET_SCHEDULES, null, institution, currentUser);

        // Send the request via the client
        Client.getInstance().sendRequest(getSchedulesRequest, new ResponseCallback<GetSchedulesResponse, Void>() {
            @Override
            public Void onSuccess(GetSchedulesResponse getSchedulesResponse) {
                // On success, retrieve the list of schedules
                List<Schedule> schedules = getSchedulesResponse.getSchedules();

                SwingUtilities.invokeLater(() -> {
                	if (parentDashboard instanceof StudentDashboardGUI) {
                        // Open ViewSchedulesGUI and pass the schedules list
                        ViewSchedulesGUI viewSchedulesGUI = new ViewSchedulesGUI(parentDashboard, schedules);
                        ((StudentDashboardGUI) parentDashboard).replaceOptionPanel(viewSchedulesGUI.getPanel());
                    } else if (parentDashboard instanceof FacultyDashboardGUI) {
                        ViewSchedulesGUI viewSchedulesGUI = new ViewSchedulesGUI(parentDashboard, schedules);
                        ((FacultyDashboardGUI) parentDashboard).replaceOptionPanel(viewSchedulesGUI.getPanel());
                    } else {
                        ViewSchedulesGUI viewSchedulesGUI = new ViewSchedulesGUI(parentDashboard, schedules);
                        ((AdminDashboardGUI) parentDashboard).replaceOptionPanel(viewSchedulesGUI.getPanel());
                    }
                });

                return null;
            }

            @Override
            public void onFailure(String reason) {
                SwingUtilities.invokeLater(() -> {
                    // Show an error dialog on failure
                    JOptionPane.showMessageDialog(parentGUI, "Failed to retrieve schedules: " + reason, "Error", JOptionPane.ERROR_MESSAGE);
                });
            }
        });
    }
}
