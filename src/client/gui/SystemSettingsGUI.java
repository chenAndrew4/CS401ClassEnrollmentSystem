package client.gui;

import client.ClientConfig;
import client.gui.dashboard.AdminDashboardGUI;
import client.utils.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.*;

public class SystemSettingsGUI {
    private JPanel systemSettingsPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;
    
    public SystemSettingsGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageCoursesPanel();
    }
    
    private void initializeManageCoursesPanel() {
    	systemSettingsPanel = new JPanel(new BorderLayout());
    	systemSettingsPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN, GUIConfig.MARGIN)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Server Settings", new ImageIcon(ClientConfig.SERVER_SETTINGS), this::handleServerSettings);
        parentDashboard.addOptionToPanel(optionsPanel, "Profile Settings", new ImageIcon(ClientConfig.PROFILE_SETTINGS), this::handleProfileSettings);

        initializeTopRow(); // Back and next buttons

        systemSettingsPanel.add(topRowPanel, BorderLayout.NORTH);
        systemSettingsPanel.add(optionsPanel, BorderLayout.CENTER);
    }
    
    private void initializeTopRow() {
        topRowPanel = new JPanel(new BorderLayout());
        topRowPanel.setOpaque(false);
        topRowPanel.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0)); // Add vertical space

        // Back arrow on the left
        backArrow = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.BACK_ARROW_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        backArrow.setContentAreaFilled(false);
        backArrow.setBorderPainted(false);
        backArrow.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        backArrow.addActionListener(e -> handleBack());
        topRowPanel.add(backArrow, BorderLayout.WEST);

        // Title label in the center
        titleLabel = new JLabel("System Configuration", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        systemSettingsPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleServerSettings() {
        JOptionPane.showMessageDialog(systemSettingsPanel, "Server Settings clicked!");
    }

    private void handleProfileSettings() {
        JOptionPane.showMessageDialog(systemSettingsPanel, "Profile Settings clicked!");
    }

    public JPanel getPanel() {
        return systemSettingsPanel;
    }
}