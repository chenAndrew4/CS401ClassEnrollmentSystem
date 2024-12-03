package client.gui;

import client.ClientConfig;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.BaseDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import client.handlers.AddUserHandler;
import client.utils.ImageUtils;
import shared.enums.AccountType;
import shared.enums.Department;
import shared.enums.GenderIdentity;
import shared.enums.Institutions;
import shared.models.Administrator;
import shared.models.User;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class AdminAddUserGUI {
    private JPanel addUsersPanel;
    private JPanel optionsPanel; // Options for Add, Delete, and Edit
    private JLabel titleLabel; // Displays the title
    private JButton backArrow; // Back to main options
    private JButton saveButton; // Button to save 
    private JPanel topRowPanel; // Top row of the options panel
    private final AdminManageUsersGUI parentDashboard;
    private Administrator adminUser;
    
	private JTextField username;
	private JTextField password;
	private JTextField firstName;
	private JTextField lastName;
	private JComboBox<Institutions> institutionID;
	private JTextField address;
	private JTextField phone;
	private JComboBox<Department> department;
	private JComboBox<AccountType> accountType;
	private JComboBox<GenderIdentity> genderIdentity;

    public AdminAddUserGUI(Administrator user, AdminManageUsersGUI adminAddUserGUI) {
        this.parentDashboard = adminAddUserGUI;
        initializeManageUsersPanel();
        this.adminUser = user;
    }
    
    private void initializeManageUsersPanel() {
		username = new JTextField();
		password = new JPasswordField();
		firstName = new JTextField();
		lastName = new JTextField();
    	institutionID = new JComboBox<Institutions>(Institutions.values());
		address = new JTextField();
		phone = new JTextField();
		department = new JComboBox<Department>(Department.values());
		accountType = new JComboBox<AccountType>(AccountType.values());
		genderIdentity = new JComboBox<GenderIdentity>(GenderIdentity.values());
		
    	addUsersPanel = new JPanel(new BorderLayout());
    	addUsersPanel.setOpaque(false);

        optionsPanel = new JPanel(new GridLayout(10, 2, 10, 10)); // Increase spacing
        optionsPanel.setOpaque(false);		
		
		optionsPanel.add(new JLabel("Username:"));
		optionsPanel.add(username);
		optionsPanel.add(new JLabel("Password:"));
		optionsPanel.add(password);
		optionsPanel.add(new JLabel("First Name:"));
		optionsPanel.add(firstName);
		optionsPanel.add(new JLabel("Last Name:"));
		optionsPanel.add(lastName);
		optionsPanel.add(new JLabel("Institution:"));
		optionsPanel.add(institutionID);
		optionsPanel.add(new JLabel("Address:"));
		optionsPanel.add(address);
		optionsPanel.add(new JLabel("Phone:"));
		optionsPanel.add(phone);
		optionsPanel.add(new JLabel("Department:"));
		optionsPanel.add(department);
		optionsPanel.add(new JLabel("Account Type:"));
		optionsPanel.add(accountType);
		optionsPanel.add(new JLabel("Gender Identity:"));
		optionsPanel.add(genderIdentity);

        initializeTopRow(); // Back and next buttons

        addUsersPanel.add(topRowPanel, BorderLayout.NORTH);
        addUsersPanel.add(optionsPanel, BorderLayout.CENTER);
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
        titleLabel = new JLabel("Add User", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        titleLabel.setForeground(Color.BLACK);
        topRowPanel.add(titleLabel, BorderLayout.CENTER);
        
        // Save button on the right
        saveButton = new JButton(ImageUtils.resizeImageIcon(new ImageIcon(ClientConfig.SAVE_ICON), GUIConfig.SMALL_ICON_WIDTH, GUIConfig.SMALL_ICON_HEIGHT));
        saveButton.setContentAreaFilled(false);
        saveButton.setBorderPainted(false);
        saveButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        saveButton.addActionListener(e -> addUser());
        topRowPanel.add(saveButton, BorderLayout.EAST);
        
        addUsersPanel.add(topRowPanel, BorderLayout.NORTH);
    }
    
    private void handleBack() {
    	parentDashboard.goBackToAdminManageUsers();
    }
    
    private void addUser() {
    	User newUser = new User(username.getText(), firstName.getText(), lastName.getText(), password.getText(),
    			(Institutions)institutionID.getSelectedItem(), (Department) department.getSelectedItem(),
    			(AccountType)accountType.getSelectedItem(), (GenderIdentity)genderIdentity.getSelectedItem(),
    			phone.getText(), address.getText());
    			
    	AddUserHandler addUserHandler = new AddUserHandler();
    	addUserHandler.handleAddUser(adminUser, newUser, addUsersPanel);
    }
    
	public JPanel getPanel() {
        return addUsersPanel;
    }
}