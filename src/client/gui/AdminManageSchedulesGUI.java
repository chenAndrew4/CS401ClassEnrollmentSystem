package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class AdminManageSchedulesGUI {
    private JPanel manageSchedulesPanel;

    public AdminManageSchedulesGUI(AdminDashboardGUI adminDashboardGUI) {
        manageSchedulesPanel = new JPanel();
        manageSchedulesPanel.add(new JLabel("Admin Manage Schedules Panel")); // Example content
    }

    public JPanel getPanel() {
        return manageSchedulesPanel;
    }
}
