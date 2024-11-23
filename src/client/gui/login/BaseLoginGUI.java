package client.gui.login;

import client.ClientConfig;
import client.gui.LoginSettingGUI;
import client.gui.SignupGUI;
import client.handlers.LoginHandler;
import client.utils.ImageUtils;
import shared.enums.Institutions;

import javax.swing.*;
import java.awt.*;

public abstract class BaseLoginGUI extends JFrame {
protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JLabel statusLabel;
    protected Institutions institution;

    public BaseLoginGUI(Institutions institution) {
        this.institution = institution;

        setTitle(institution + " - CCES Login");
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top-left "Settings" icon
        ImageIcon settingsIcon = new ImageIcon(ClientConfig.SETTING_ICON);
        Image resizedSettingsIcon = settingsIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        JLabel settingsLabel = new JLabel(new ImageIcon(resizedSettingsIcon));
        settingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsLabel.setToolTipText("Settings");
        settingsLabel.setForeground(Color.LIGHT_GRAY);
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new LoginSettingGUI(); // Open settings dialog
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(Color.WHITE); // Set background color for contrast
        topPanel.add(settingsLabel);
        add(topPanel, BorderLayout.NORTH);

        // Main panel for central layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // University icon
        JLabel universityIcon = new JLabel(ImageUtils.resizeImageIcon( new ImageIcon(getUniversityIconPath()), 100, 100)); // Medium size
        universityIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(universityIcon);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(20));

        // Username field
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(usernameLabel);

        usernameField = new JTextField(15);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        mainPanel.add(usernameField);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(10));

        // Password field
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(passwordLabel);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        mainPanel.add(passwordField);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(20));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        LoginHandler loginHandler = new LoginHandler();

        loginButton.addActionListener(e -> {
            loginHandler.handleLogin(usernameField.getText(), new String(passwordField.getPassword()), institution, this);
        });

        mainPanel.add(loginButton);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(10));

        // Sign-Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        signUpButton.addActionListener(e -> new SignupGUI());
        mainPanel.add(signUpButton);

        statusLabel = new JLabel();
        add(statusLabel, BorderLayout.SOUTH);
    }

    protected abstract String getUniversityIconPath();


    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JPasswordField getPasswordField() {
        return passwordField;
    }

    public void setPasswordField(JPasswordField passwordField) {
        this.passwordField = passwordField;
    }

    public JLabel getStatusLabel() {
        return statusLabel;
    }

    public void setStatusLabel(JLabel statusLabel) {
        this.statusLabel = statusLabel;
    }

    public Institutions getInstitution() {
        return institution;
    }

    public void setInstitution(Institutions institution) {
        this.institution = institution;
    }
}



