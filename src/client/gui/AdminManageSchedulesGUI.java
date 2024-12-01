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

public class AdminManageSchedulesGUI {
    private JPanel manageSchedulesPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;

    public AdminManageSchedulesGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageSchedulesPanel();
    }
    
    private void initializeManageSchedulesPanel() {
    	manageSchedulesPanel = new JPanel(new BorderLayout());
    	manageSchedulesPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Add Schedule", new ImageIcon(ClientConfig.ADD_SCHEDULE), this::handleAddSchedule);
        parentDashboard.addOptionToPanel(optionsPanel, "Delete Schedule", new ImageIcon(ClientConfig.DELETE_SCHEDULE), this::handleDeleteSchedule);
        parentDashboard.addOptionToPanel(optionsPanel, "Edit Schedule", new ImageIcon(ClientConfig.EDIT_SCHEDULE), this::handleEditSchedule);

        initializeTopRow(); // Back and next buttons

        manageSchedulesPanel.add(topRowPanel, BorderLayout.NORTH);
        manageSchedulesPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Manage Schedules", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        manageSchedulesPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleAddSchedule() {
        JOptionPane.showMessageDialog(manageSchedulesPanel, "Add Schedule clicked!");
    }

    private void handleDeleteSchedule() {
        JOptionPane.showMessageDialog(manageSchedulesPanel, "Edit Schedule clicked!");
    }

    private void handleEditSchedule() {
        JOptionPane.showMessageDialog(manageSchedulesPanel, "Delete Schedule clicked!");
    }

    public JPanel getPanel() {
        return manageSchedulesPanel;
    }
}
