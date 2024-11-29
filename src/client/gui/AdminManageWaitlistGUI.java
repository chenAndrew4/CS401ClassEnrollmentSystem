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

public class AdminManageWaitlistGUI {
    private JPanel manageWaitlistPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;

    public AdminManageWaitlistGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageWaitlistPanel();
    }
    
    private void initializeManageWaitlistPanel() {
    	manageWaitlistPanel = new JPanel(new BorderLayout());
    	manageWaitlistPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Add Waitlist", new ImageIcon(ClientConfig.ADD_WAITLIST), this::handleAddWaitlist);
        parentDashboard.addOptionToPanel(optionsPanel, "Delete Waitlist", new ImageIcon(ClientConfig.DELETE_WAITLIST), this::handleDeleteWaitlist);
        parentDashboard.addOptionToPanel(optionsPanel, "Edit Waitlist", new ImageIcon(ClientConfig.EDIT_WAITLIST), this::handleEditWaitlist);

        initializeTopRow(); // Back and next buttons

        manageWaitlistPanel.add(topRowPanel, BorderLayout.NORTH);
        manageWaitlistPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Manage Waitlists", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        manageWaitlistPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleAddWaitlist() {
        JOptionPane.showMessageDialog(manageWaitlistPanel, "Add Waitlist clicked!");
    }

    private void handleDeleteWaitlist() {
        JOptionPane.showMessageDialog(manageWaitlistPanel, "Edit Waitlist clicked!");
    }

    private void handleEditWaitlist() {
        JOptionPane.showMessageDialog(manageWaitlistPanel, "Delete Waitlist clicked!");
    }

    public JPanel getPanel() {
        return manageWaitlistPanel;
    }
}