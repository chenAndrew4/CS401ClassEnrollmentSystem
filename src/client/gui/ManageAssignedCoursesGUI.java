package client.gui;

import client.ClientConfig;
import client.gui.dashboard.FacultyDashboardGUI;
import client.handlers.GetAssignedCoursesHandler;
import client.utils.ImageUtils;
import server.service.CourseService;
import shared.models.Course;
import shared.models.CourseSection;
import shared.models.Faculty;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import javax.swing.*;

public class ManageAssignedCoursesGUI {
	private final FacultyDashboardGUI parentDashboard;
    private JPanel manageAssignedCoursePanel;
    private JPanel optionsPanel; // Options for Courses and Syllabus
    private JPanel topRowPanel;
    private JButton backArrow;
    private JLabel titleLabel;
    Faculty currentUser;
    
    public ManageAssignedCoursesGUI(Faculty currentUser, FacultyDashboardGUI facultyDashboardGUI) {
    	this.parentDashboard = facultyDashboardGUI;
        this.currentUser = currentUser;
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
        parentDashboard.addOptionToPanel(optionsPanel, "Courses", new ImageIcon(ClientConfig.ASSIGNED_COURSES), this::handleCourses);
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
        CourseSection section; // Just trying to populate assigned courses
        CourseService service;
        service = CourseService.getInstance();
        CourseSection course1 = new CourseSection(currentUser.getInstitutionID(), "CS101");
        CourseSection course2 = new CourseSection(currentUser.getInstitutionID(), "CS102");
        CourseSection course3 = new CourseSection(currentUser.getInstitutionID(), "CS103");
        service.assignCourse(currentUser.getInstitutionID(), currentUser, course1);
        service.assignCourse(currentUser.getInstitutionID(), currentUser, course2);
        service.assignCourse(currentUser.getInstitutionID(), currentUser, course3);
        
        GetAssignedCoursesHandler handler = new GetAssignedCoursesHandler();
        handler.handleGetAssignedCourses(currentUser, currentUser.getInstitutionID(), manageAssignedCoursePanel, this);
    }
    
    private void handleSyllabus() {
        JOptionPane.showMessageDialog(manageAssignedCoursePanel, "Syllabus clicked!");
    }

    public void goBackToManageAssignedCourses(){
        parentDashboard.getOptionsPanel().removeAll();
        initializeManageAssignedCourses();

        parentDashboard.getOptionsPanel().setLayout(new BorderLayout());
        parentDashboard.getOptionsPanel().setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        parentDashboard.getOptionsPanel().add(manageAssignedCoursePanel, BorderLayout.CENTER);

        parentDashboard.revalidate();
        parentDashboard.repaint();
    }

    public void replaceOptionPanel(JPanel newPanel) {
        manageAssignedCoursePanel.removeAll();
        manageAssignedCoursePanel.add(newPanel, BorderLayout.CENTER);
        manageAssignedCoursePanel.revalidate();
        manageAssignedCoursePanel.repaint();
    }

}
    
