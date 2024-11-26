package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class ReportsGUI {
    private JPanel reportsPanel;

    public ReportsGUI(AdminDashboardGUI adminDashboardGUI) {
        reportsPanel = new JPanel();
        reportsPanel.add(new JLabel("Reports Panel")); // Example content
    }

    public JPanel getPanel() {
        return reportsPanel;
    }
}
