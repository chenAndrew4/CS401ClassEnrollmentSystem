package client.gui;

import client.gui.dashboard.AdminDashboardGUI;

import javax.swing.*;

public class SystemSettingsGUI {
    private JPanel systemSettingsPanel;

    public SystemSettingsGUI(AdminDashboardGUI adminDashboardGUI) {
        systemSettingsPanel = new JPanel();
        systemSettingsPanel.add(new JLabel("System Settings Panel")); // Example content
    }

    public JPanel getPanel() {
        return systemSettingsPanel;
    }
}