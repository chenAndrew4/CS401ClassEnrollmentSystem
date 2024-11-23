package client.gui.login;

import client.ClientConfig;
import shared.enums.Institutions;

public class CSUEBLoginGUI extends BaseLoginGUI {
    public CSUEBLoginGUI() {
        super(Institutions.CSUEB);
    }

    @Override
    protected String getUniversityIconPath() {
        return ClientConfig.CSUEB_LOGO_FILE_PATH; // Use path from ClientConfig
    }
}
//    private JTextField usernameField;
//    private JPasswordField passwordField;
//    private JLabel statusLabel;
//    private Institutions institutions = Institutions.CSUEB;
//
//    public LoginGUI() {
//        setTitle("University Login");
//        setSize(400, 500);
//        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        // Top-left small "Settings" label
//        JLabel settingsLabel = new JLabel("<HTML><U>settings</U></HTML>");
//        settingsLabel.setFont(new Font("Arial", Font.PLAIN, 12));
//        settingsLabel.setForeground(Color.BLUE);
//        settingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
//        add(settingsLabel, BorderLayout.NORTH);
//
//        // Main panel for central layout
//        JPanel mainPanel = new JPanel();
//        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
//        add(mainPanel, BorderLayout.CENTER);
//
//        // University icon
//        JLabel universityIcon = new JLabel(new ImageIcon("college-icon.png")); // Replace with actual image path
//        universityIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(universityIcon);
//
//        // Add spacing
//        mainPanel.add(Box.createVerticalStrut(20));
//
//        // Username field
//        JLabel usernameLabel = new JLabel("Username:");
//        usernameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(usernameLabel);
//
//        usernameField = new JTextField(15);
//        usernameField.setMaximumSize(usernameField.getPreferredSize());
//        mainPanel.add(usernameField);
//
//        // Add spacing
//        mainPanel.add(Box.createVerticalStrut(10));
//
//        // Password field
//        JLabel passwordLabel = new JLabel("Password:");
//        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(passwordLabel);
//
//        passwordField = new JPasswordField(15);
//        passwordField.setMaximumSize(passwordField.getPreferredSize());
//        mainPanel.add(passwordField);
//
//        // Add spacing
//        mainPanel.add(Box.createVerticalStrut(20));
//
//        // Login button
//        JButton loginButton = new JButton("Login");
//        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(loginButton);
//
//        // Add spacing
//        mainPanel.add(Box.createVerticalStrut(10));
//
//        // Sign-Up button
//        JButton signUpButton = new JButton("Sign Up");
//        signUpButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//        mainPanel.add(signUpButton);
//
//        statusLabel = new JLabel("Enter your credentials");
//        add(statusLabel, BorderLayout.SOUTH);
//
//        // Event Listeners
//        loginButton.addActionListener(e -> handleLogin());
//        signUpButton.addActionListener(e -> new SignupGUI());
//        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
//            @Override
//            public void mouseClicked(java.awt.event.MouseEvent e) {
//                new LoginSettingGUI();
//            }
//        });
//    }
//
////    private void handleLogin() {
////        String username = usernameField.getText();
////        String password = new String(passwordField.getPassword());
////        if (username.isEmpty() || password.isEmpty()) {
////            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
////        } else {
////            statusLabel.setText("Logging in...");
////            ThreadClient.login(username, password, institutions, this);
////            JOptionPane.showMessageDialog(this, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
////        }
////    }
//    private void handleLogin() {
//        String username = usernameField.getText();
//        String password = new String(passwordField.getPassword());
//        if (username.isEmpty() || password.isEmpty()) {
//            JOptionPane.showMessageDialog(this, "Username or password cannot be empty.", "Login Error", JOptionPane.ERROR_MESSAGE);
//        } else {
//            statusLabel.setText("Logging in...");
//
//            // Use the callback for handling login results
//            User currentUser = new User();
//            currentUser.setUsername(username);
//            currentUser.setPassword(password);
//            currentUser.setInstitutionID(institutions);
//            LoginRequest loginRequest = new LoginRequest(MessageType.LOGIN, null, institutions, currentUser);
//            ThreadClient.getInstance().sendRequest(loginRequest, new ResponseCallback<LoginResponse, Void>() {
//                @Override
//                public Void onSuccess(LoginResponse loginResponse) {
//                    SwingUtilities.invokeLater(() -> {
//    //                    JOptionPane.showMessageDialog(LoginGUI.this, "Login successful!", "Login", JOptionPane.INFORMATION_MESSAGE);
//                        openDashboard(loginResponse.getUser());
//                        dispose(); // Close LoginGUI
//                    });
//                    return null;
//                }
//                @Override
//                public void onFailure(String reason) {
//                    SwingUtilities.invokeLater(() -> {
//    //                    JOptionPane.showMessageDialog(LoginGUI.this, "Login failed: " + reason, "Login Error", JOptionPane.ERROR_MESSAGE);
//                        statusLabel.setText("Login failed: " + reason);
//                    });
//                }
//            });
//        }
//    }
//
//    private void openDashboard(User user) {
//        switch (user.getAccountType()) {
//            case Student -> new Thread(new StudentDashboardGUI((Student) user)).start();
//            case Faculty -> new Thread(new FacultyDashboardGUI((Faculty) user)).start();
//            case Administrator -> new Thread(new AdminDashboardGUI((Administrator) user)).start();
//            default -> JOptionPane.showMessageDialog(this, "Unknown Account type: " + user.getAccountType(), "Error", JOptionPane.ERROR_MESSAGE);
//        }
//    }
//
//    // Update status label based on the client response
//    public void updateStatus(String status) {
//    statusLabel.setText(status);
//    }
//
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(() -> {
//            LoginGUI loginGUI = new LoginGUI();
//            loginGUI.setVisible(true);
//        });
//    }
//}

//public class LoginGUI {
//    private static Institutions institutionID;
//    public static void main(String[] args) {
//        institutionID = Institutions.CSUEB;
//        SwingUtilities.invokeLater(LoginGUI::renderLoginGUI);
//    }
//
//    private static void renderLoginGUI() {
//        String[] loginChoices = {"Login", "Exit"};
//        JPanel panel = new JPanel(new GridLayout(4, 2));
//
//        JTextField usernameField = new JTextField();
//        JTextField passwordField = new JPasswordField();
//        JTextField hostnameField = new JTextField("localhost");
//        JTextField portField = new JTextField("25800");
//
//        panel.add(new JLabel("Username:"));
//        panel.add(usernameField);
//        panel.add(new JLabel("Password:"));
//        panel.add(passwordField);
//        panel.add(new JLabel("Server Host:"));
//        panel.add(hostnameField);
//        panel.add(new JLabel("Server Port:"));
//        panel.add(portField);
//
//        while (true) {
//            try {
//                BufferedImage originalImage = ImageIO.read(new File(ClientConfig.CSUEB_LOGO_FILE_PATH));
//                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize to 50x50
//                ImageIcon resizedIcon = new ImageIcon(resizedImage);
//
//            int loginChoice = JOptionPane.showOptionDialog(null, panel, institutionID + " - CCES - Login",
//                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
//                    resizedIcon, loginChoices, loginChoices[0]);
//
//            if (loginChoice == 1 || loginChoice == JOptionPane.CLOSED_OPTION) {
//                // Exit application
//                return;
//            }
//
//            String username = usernameField.getText();
//            String password = passwordField.getText();
//            String hostname = hostnameField.getText();
//            String port = portField.getText();
//
//            Client client = new Client(hostname, port);
//
//            if (client.login(username, password, institutionID)) {
//                // Show the portal if login is successful
//                client.showPortal(username);
//                break;
//            } else {
//                JOptionPane.showMessageDialog(null, "Invalid credentials or server connection issue.",
//                        institutionID + "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE,
//                        new ImageIcon(ClientConfig.INVALID_CREDS_FILE_PATH));
//            }
//            } catch (IOException e) {
//                System.err.println("Error File Path");
//                e.printStackTrace();
//            }
//        }
//    }
//}
