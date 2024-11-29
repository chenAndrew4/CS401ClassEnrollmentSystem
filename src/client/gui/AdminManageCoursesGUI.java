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

public class AdminManageCoursesGUI {
    private JPanel manageCoursesPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;
    
    public AdminManageCoursesGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageCoursesPanel();
    }
    
    private void initializeManageCoursesPanel() {
    	manageCoursesPanel = new JPanel(new BorderLayout());
    	manageCoursesPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 3, GUIConfig.MARGIN * 2, GUIConfig.MARGIN * 2)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Add Course", new ImageIcon(ClientConfig.ADD_COURSE), this::handleAddCourse);
        parentDashboard.addOptionToPanel(optionsPanel, "Delete Course", new ImageIcon(ClientConfig.DELETE_COURSE), this::handleDeleteCourse);
        parentDashboard.addOptionToPanel(optionsPanel, "Edit Course", new ImageIcon(ClientConfig.EDIT_COURSE), this::handleEditCourse);

        initializeTopRow(); // Back and next buttons

        manageCoursesPanel.add(topRowPanel, BorderLayout.NORTH);
        manageCoursesPanel.add(optionsPanel, BorderLayout.CENTER);
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

        manageCoursesPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleAddCourse() {
        JOptionPane.showMessageDialog(manageCoursesPanel, "Add Course clicked!");
    }

    private void handleEditCourse() {
        JOptionPane.showMessageDialog(manageCoursesPanel, "Edit Course clicked!");
    }

    private void handleDeleteCourse() {
        JOptionPane.showMessageDialog(manageCoursesPanel, "Delete Course clicked!");
    }

    public JPanel getPanel() {
        return manageCoursesPanel;
    }
}
