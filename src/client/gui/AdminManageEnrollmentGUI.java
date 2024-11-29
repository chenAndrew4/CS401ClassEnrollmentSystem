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

public class AdminManageEnrollmentGUI {
    private JPanel manageEnrollmentPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;

    public AdminManageEnrollmentGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageEnrollmentPanel();
    }
    
    private void initializeManageEnrollmentPanel() {
    	manageEnrollmentPanel = new JPanel(new BorderLayout());
    	manageEnrollmentPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 2, GUIConfig.MARGIN, GUIConfig.MARGIN)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Enroll Student", new ImageIcon(ClientConfig.ADD_COURSE), this::handleEnrollStudent);
        parentDashboard.addOptionToPanel(optionsPanel, "Disenroll Student", new ImageIcon(ClientConfig.DELETE_COURSE), this::handleDisenrollStudent);

        initializeTopRow(); // Back and next buttons

        manageEnrollmentPanel.add(topRowPanel, BorderLayout.NORTH);
        manageEnrollmentPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Manage Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        manageEnrollmentPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleEnrollStudent() {
        JOptionPane.showMessageDialog(manageEnrollmentPanel, "Enroll Student clicked!");
    }

    private void handleDisenrollStudent() {
        JOptionPane.showMessageDialog(manageEnrollmentPanel, "Disenroll Student clicked!");
    }

    public JPanel getPanel() {
        return manageEnrollmentPanel;
    }
}