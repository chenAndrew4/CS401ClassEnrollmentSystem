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

public class ReportsGUI {
    private JPanel reportsPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminDashboardGUI parentDashboard;

    public ReportsGUI(AdminDashboardGUI adminDashboardGUI) {
        this.parentDashboard = adminDashboardGUI;
        initializeManageCoursesPanel();
    }
    
    private void initializeManageCoursesPanel() {
    	reportsPanel = new JPanel(new BorderLayout());
    	reportsPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(2, 3, GUIConfig.MARGIN, GUIConfig.MARGIN)); // Increase spacing
        optionsPanel.setOpaque(false);
        parentDashboard.addOptionToPanel(optionsPanel, "Course Details", new ImageIcon(ClientConfig.COURSE_DETAILS_REPORT), this::handleCourseDetailReport);
        parentDashboard.addOptionToPanel(optionsPanel, "Grades", new ImageIcon(ClientConfig.GRADE_REPORT), this::handleStudentGradesReport);
        parentDashboard.addOptionToPanel(optionsPanel, "Waitlists", new ImageIcon(ClientConfig.WAITLISTS_REPORT), this::handleWaitlistReport);
        parentDashboard.addOptionToPanel(optionsPanel, "Schedules", new ImageIcon(ClientConfig.SCHEDULE_REPORT), this::handleScheduleReport);
        parentDashboard.addOptionToPanel(optionsPanel, "Enrollments", new ImageIcon(ClientConfig.ENROLLMENT_REPORT), this::handleEnrollmentReport);

        initializeTopRow(); // Back and next buttons

        reportsPanel.add(topRowPanel, BorderLayout.NORTH);
        reportsPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Reports", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);

        reportsPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToMainOptions();
    }
    
    private void handleCourseDetailReport() {
        JOptionPane.showMessageDialog(reportsPanel, "Course Details Report clicked!");
    }
    
    private void handleStudentGradesReport() {
        JOptionPane.showMessageDialog(reportsPanel, "Student Grades Report clicked!");
    }
    
    private void handleWaitlistReport() {
        JOptionPane.showMessageDialog(reportsPanel, "Waitlists Report clicked!");
    }

    private void handleScheduleReport() {
        JOptionPane.showMessageDialog(reportsPanel, "Schedule Report Course clicked!");
    }

    private void handleEnrollmentReport() {
        JOptionPane.showMessageDialog(reportsPanel, "Enrollment Report clicked!");
    }

    public JPanel getPanel() {
        return reportsPanel;
    }
}
