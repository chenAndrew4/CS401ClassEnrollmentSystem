package client.gui;

import client.Client;

import javax.swing.*;
import java.awt.*;

public class LoginGUI {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(LoginGUI::renderLoginGUI);
    }

    private static void renderLoginGUI() {
        String[] loginChoices = {"Login", "Exit"};
        JPanel panel = new JPanel(new GridLayout(4, 2));

        JTextField usernameField = new JTextField();
        JTextField passwordField = new JPasswordField();
        JTextField hostnameField = new JTextField("localhost");
        JTextField portField = new JTextField("25800");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(new JLabel("Server Host:"));
        panel.add(hostnameField);
        panel.add(new JLabel("Server Port:"));
        panel.add(portField);

        while (true) {
            int loginChoice = JOptionPane.showOptionDialog(null, panel, "CCES - Login",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    new ImageIcon("src/client/assets/icons/login_32.png"), loginChoices, loginChoices[0]);

            if (loginChoice == 1 || loginChoice == JOptionPane.CLOSED_OPTION) {
                // Exit application
                return;
            }

            String username = usernameField.getText();
            String password = passwordField.getText();
            String hostname = hostnameField.getText();
            String port = portField.getText();

            Client client = new Client(hostname, port);

            if (client.login(username, password)) {
                // Show the portal if login is successful
                client.showPortal(username);
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials or server connection issue.",
                        "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon("src/client/assets/icons/invalid_creds_32.png"));
            }
        }
    }
}
