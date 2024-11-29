package client.gui;

import client.ClientConfig;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import client.utils.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AdminManageUsersGUI {
    private JPanel manageUsersPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;

    public AdminManageUsersGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageUsersPanel();
    }
    
    private void initializeManageUsersPanel() {
    	manageUsersPanel = new JPanel(new BorderLayout());
    	manageUsersPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Add User", new ImageIcon(ClientConfig.ADD_USER), this::handleAddUser);
        parentDashboard.addOptionToPanel(optionsPanel, "Delete User", new ImageIcon(ClientConfig.DELETE_USER), this::handleDeleteUser);
        parentDashboard.addOptionToPanel(optionsPanel, "Edit User", new ImageIcon(ClientConfig.EDIT_USER), this::handleEditUser);

        initializeTopRow(); // Back and next buttons

        manageUsersPanel.add(topRowPanel, BorderLayout.NORTH);
        manageUsersPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Manage Users", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        manageUsersPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleAddUser() {
        JOptionPane.showMessageDialog(manageUsersPanel, "Add User clicked!");
    }

    private void handleEditUser() {
        JOptionPane.showMessageDialog(manageUsersPanel, "Edit User clicked!");
    }

    private void handleDeleteUser() {
        JOptionPane.showMessageDialog(manageUsersPanel, "Delete User clicked!");
    }
    
    public JPanel getPanel() {
        return manageUsersPanel;
    }
}
