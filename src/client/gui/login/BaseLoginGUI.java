package client.gui.login;

import client.ClientConfig;
import client.deleted.SignupGUI;
import client.gui.LoginSettingGUI;
import client.handlers.LoginHandler;
import client.utils.ImageUtils;
import server.ServerManager;
import shared.enums.Institutions;

import javax.swing.*;
import java.awt.*;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;

public abstract class BaseLoginGUI extends JFrame {
    protected JTextField usernameField;
    protected JPasswordField passwordField; // For masked password input
    protected JLabel statusLabel;
    protected Institutions institution;

    public BaseLoginGUI(Institutions institution) {
        this.institution = institution;

        setTitle(institution + " - CCES Login");
        setIconImage(new ImageIcon(ClientConfig.CLIENT_LOGO_FILE_PATH).getImage());
        setSize(400, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Top panel with "Settings" icon blending with the background
        ImageIcon settingsIcon = new ImageIcon(ClientConfig.SETTING_ICON);
        Image resizedSettingsIcon = settingsIcon.getImage().getScaledInstance(16, 16, Image.SCALE_SMOOTH);
        JLabel settingsLabel = new JLabel(new ImageIcon(resizedSettingsIcon));
        settingsLabel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        settingsLabel.setToolTipText("Settings");
        settingsLabel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                new LoginSettingGUI(); // Open settings dialog
            }
        });

        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        topPanel.setBackground(getBackground());
        topPanel.add(settingsLabel);
        add(topPanel, BorderLayout.NORTH);

        // Main panel for central layout
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        add(mainPanel, BorderLayout.CENTER);

        // University icon
        JLabel universityIcon = new JLabel(ImageUtils.resizeImageIcon(new ImageIcon(getUniversityIconPath()), 100, 100)); // Medium size
        universityIcon.setAlignmentX(Component.CENTER_ALIGNMENT);
        mainPanel.add(universityIcon);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(20));

        // Username field
        usernameField = new JTextField("Username", 15);
        usernameField.setForeground(Color.GRAY);
        usernameField.addFocusListener(new PlaceholderListener("Username", usernameField));
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        mainPanel.add(usernameField);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(10));

        // Password field (visible placeholder, converts to masked input on focus)
        passwordField = new JPasswordField(15);
        passwordField.setText("Password"); // Set initial placeholder text
        passwordField.setForeground(Color.GRAY);
        passwordField.setEchoChar((char) 0); // Placeholder visible initially
        passwordField.addFocusListener(new PlaceholderListener("Password", passwordField));
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        mainPanel.add(passwordField);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(20));

        // Login button
        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        getRootPane().setDefaultButton(loginButton);
        LoginHandler loginHandler = new LoginHandler();

        loginButton.addActionListener(e -> {
            String password = new String(passwordField.getPassword());
            if (!validatePassword(password)) {
                setStatusMessage("Password must be at least 8 characters with 1 letter and 1 number.", Color.RED);
            } else {
                loginHandler.handleLogin(usernameField.getText(), password, institution, this);
            }
        });
        mainPanel.add(loginButton);

        // Add spacing
        mainPanel.add(Box.createVerticalStrut(10));

        // Feedback message below Login button
        statusLabel = new JLabel("", SwingConstants.CENTER);
        statusLabel.setForeground(Color.RED);
        statusLabel.setFont(new Font("Arial", Font.ITALIC, 12));
        statusLabel.setAlignmentX(Component.CENTER_ALIGNMENT); // Center alignment
        mainPanel.add(Box.createVerticalStrut(10)); // Space between Login and Status
        mainPanel.add(statusLabel);
    }

    protected abstract String getUniversityIconPath();

    // Validate password with regex
    private boolean validatePassword(String password) {
        String regex = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,}$"; // Minimum 8 characters, 1 letter, 1 number
        return password.matches(regex);
    }

    // Format and display status message
    private void setStatusMessage(String message, Color color) {
        statusLabel.setText(formatMessage(message, 5)); // Limit to 5 words per line
        statusLabel.setForeground(color);
    }

    // Format message into lines with a max word count per line
    private String formatMessage(String message, int maxWordsPerLine) {
        StringBuilder formatted = new StringBuilder("<html><div style='text-align: center;'>");
        String[] words = message.split(" ");
        int wordCount = 0;
        for (String word : words) {
            if (wordCount >= maxWordsPerLine) {
                formatted.append("<br>"); // Start a new line
                wordCount = 0;
            }
            formatted.append(word).append(" ");
            wordCount++;
        }
        formatted.append("</div></html>");
        return formatted.toString().trim();
    }

    // Placeholder logic for fields
    private static class PlaceholderListener implements FocusListener {
        private final String placeholder;
        private final JTextField textField;

        public PlaceholderListener(String placeholder, JTextField textField) {
            this.placeholder = placeholder;
            this.textField = textField;
        }

        @Override
        public void focusGained(FocusEvent e) {
            if (textField.getText().equals(placeholder)) {
                textField.setText("");
                textField.setForeground(Color.BLACK);
                if (textField instanceof JPasswordField) {
                    ((JPasswordField) textField).setEchoChar('*'); // Mask password
                }
            }
        }

        @Override
        public void focusLost(FocusEvent e) {
            if (textField.getText().isEmpty()) {
                textField.setText(placeholder);
                textField.setForeground(Color.GRAY);
                if (textField instanceof JPasswordField) {
                    ((JPasswordField) textField).setEchoChar((char) 0); // Show placeholder
                }
            }
        }
    }

    public JTextField getUsernameField() {
        return usernameField;
    }

    public void setUsernameField(JTextField usernameField) {
        this.usernameField = usernameField;
    }

    public JTextField getPasswordField() {
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






