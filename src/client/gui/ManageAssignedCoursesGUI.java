package client.gui;

import client.ClientConfig;
import client.gui.dashboard.FacultyDashboardGUI;
import client.utils.ImageUtils;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class ManageAssignedCoursesGUI {
	private final FacultyDashboardGUI parentDashboard;
    private JPanel manageAssignedCoursePanel;
    private JPanel optionsPanel; // Options for Courses and Syllabus
    private JPanel topRowPanel;
    private JButton backArrow;
    private JLabel titleLabel;
    public ManageAssignedCoursesGUI(FacultyDashboardGUI facultyDashboardGUI) {
    	this.parentDashboard = facultyDashboardGUI;
    	initializeManageAssignedCourses();
    }
    
    public JPanel getPanel() {
        return manageAssignedCoursePanel;
    }
    
    public void initializeManageAssignedCourses() {
    	manageAssignedCoursePanel = new JPanel(new BorderLayout());
    	manageAssignedCoursePanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(0, 2, GUIConfig.MARGIN, GUIConfig.MARGIN)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Course", new ImageIcon(ClientConfig.ASSIGNED_COURSES), this::handleCourses);
        parentDashboard.addOptionToPanel(optionsPanel, "Syllabus", new ImageIcon(ClientConfig.ASSIGNED_SYLLABUS), this::handleSyllabus);
        
        initializeTopRow();
        
        manageAssignedCoursePanel.add(topRowPanel, BorderLayout.NORTH);
        manageAssignedCoursePanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Manage Assigned Courses", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        manageAssignedCoursePanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleCourses() {
    	JFrame frame = new JFrame("Courses");
    	frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    	frame.setSize(800, 300);    
        
    }
    
    private void handleSyllabus() {
        JOptionPane.showMessageDialog(manageAssignedCoursePanel, "Syllabus clicked!");
    }
}
    
