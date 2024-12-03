package client.deleted;

import javax.swing.*;

import client.gui.dashboard.AdminDashboardGUI;

import java.awt.*;

public class SignupGUI extends JFrame {
    JTextField usernameField;
    JTextField firstField;
    JTextField lastField;
    JPasswordField passwordField;

    public SignupGUI() {
        setTitle("Sign Up");
        setSize(300, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BoxLayout(getContentPane(), BoxLayout.Y_AXIS));

        add(new JLabel("Enter your details to sign up:"));
        add(Box.createVerticalStrut(10));

        usernameField = new JTextField(15);
        usernameField.setMaximumSize(usernameField.getPreferredSize());
        add(new JLabel("User name:"));
        add(usernameField);

        firstField = new JTextField(15);
        firstField.setMaximumSize(firstField.getPreferredSize());
        add(new JLabel("First name:"));
        add(firstField);

        lastField = new JTextField(15);
        lastField.setMaximumSize(lastField.getPreferredSize());
        add(new JLabel("Last name:"));
        add(lastField);

        passwordField = new JPasswordField(15);
        passwordField.setMaximumSize(passwordField.getPreferredSize());
        add(new JLabel("Password:"));
        add(passwordField);

        JButton submitButton = new JButton("Submit");
        submitButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        add(Box.createVerticalStrut(10));
        add(submitButton);

        submitButton.addActionListener(e -> {
            if (handleSignup()) {
                JOptionPane.showMessageDialog(this, "Sign Up Successful!", "Sign-Up", JOptionPane.INFORMATION_MESSAGE);
            } else {
                JOptionPane.showMessageDialog(this, "Sign Up Error!", "Sign-Up", JOptionPane.INFORMATION_MESSAGE);
            }});

        setVisible(true);
    }

    private boolean handleSignup() {
        String username = usernameField.getText();
        String password = new String(passwordField.getPassword());
        String firstname = firstField.getText();
        String lastname = lastField.getText();

        return true;
    }
}
