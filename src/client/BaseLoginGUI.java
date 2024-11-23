package client;

import client.gui.SignupGUI;
import client.gui.dashboard.AdminDashboardGUI;
import client.gui.dashboard.FacultyDashboardGUI;
import client.gui.dashboard.StudentDashboardGUI;
import shared.enums.Institutions;
import shared.enums.MessageType;
import shared.models.Administrator;
import shared.models.Faculty;
import shared.models.Student;
import shared.models.User;
import shared.models.requests.LoginRequest;
import shared.models.responses.LoginResponse;

import javax.swing.*;
import java.awt.*;

public abstract class BaseLoginGUI extends JFrame {
    protected JTextField usernameField;
    protected JPasswordField passwordField;
    protected JLabel statusLabel;
    protected Institutions institution;

    public BaseLoginGUI(Institutions institution) {
        this.institution = institution;

        setTitle(institution + " - University CCES Login");
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
                openSettingsDialog();
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
        JLabel universityIcon = new JLabel(resizeImageIcon(getUniversityIconPath(), 100, 100)); // Medium size
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
        mainPanel.add(loginButton);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(10));

        // Sign-Up button
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(signUpButton);

        statusLabel = new JLabel("Enter your credentials");
        add(statusLabel, BorderLayout.SOUTH);

        // Event Listeners
        loginButton.addActionListener(e -> handleLogin());
        signUpButton.addActionListener(e -> new SignupGUI());

    }

    protected abstract String getUniversityIconPath();

    private void handleLogin() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        if (username.isEmpty() || password.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
        } else {
            statusLabel.setText("Logging in...");

            User currentUser = new User();
            currentUser.setUsername(username);
            currentUser.setPassword(password);
            currentUser.setInstitutionID(institution);
            LoginRequest loginRequest = new LoginRequest(MessageType.LOGIN, null, institution, currentUser);
            ThreadClient.getInstance().sendRequest(loginRequest, new ResponseCallback<LoginResponse, Void>() {
                @Override
                public Void onSuccess(LoginResponse loginResponse) {
                    SwingUtilities.invokeLater(() -> {
                        openDashboard(loginResponse.getUser());
                        dispose(); // Close LoginGUI
                    });
                    return null;
                }

                @Override
                public void onFailure(String reason) {
                    SwingUtilities.invokeLater(() -> {
                        statusLabel.setText("Login failed: " + reason);
                    });
                }
            });
        }
    }

    private void openDashboard(User user) {
        switch (user.getAccountType()) {
            case Student -> SwingUtilities.invokeLater(() -> { new StudentDashboardGUI((Student) user); repaint();});
            case Faculty -> SwingUtilities.invokeLater(() -> new FacultyDashboardGUI((Faculty) user).setVisible(true));
            case Administrator -> SwingUtilities.invokeLater(() -> new AdminDashboardGUI((Administrator) user).setVisible(true));
            default -> JOptionPane.showMessageDialog(this, "Unknown Account type: " + user.getAccountType(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void openSettingsDialog() {
        JTextField hostField = new JTextField(ThreadClient.getInstance().getSERVER_ADDRESS(), 15);
        JTextField portField = new JTextField(String.valueOf(ThreadClient.getInstance().getSERVER_PORT()), 15);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(2, 2));
        panel.add(new JLabel("Host:"));
        panel.add(hostField);
        panel.add(new JLabel("Port:"));
        panel.add(portField);

        int result = JOptionPane.showConfirmDialog(
                this, panel, "Server Settings", JOptionPane.OK_CANCEL_OPTION, JOptionPane.PLAIN_MESSAGE);

        if (result == JOptionPane.OK_OPTION) {
            ThreadClient.getInstance().setSERVER_ADDRESS(hostField.getText());
            try {
                int port = Integer.parseInt(portField.getText());
                ThreadClient.getInstance().setSERVER_PORT(port);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid port number. Please try again.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private ImageIcon resizeImageIcon(String path, int width, int height) {
        ImageIcon originalIcon = new ImageIcon(path);
        Image resizedImage = originalIcon.getImage().getScaledInstance(width, height, Image.SCALE_SMOOTH);
        return new ImageIcon(resizedImage);
    }

}



