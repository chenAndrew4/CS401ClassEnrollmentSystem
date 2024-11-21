package client.gui.CSUEB;

import client.Client;
import client.ClientConfig;
import shared.enums.Institutions;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class LoginGUI {
    private static Institutions institutionID;
    public static void main(String[] args) {
        institutionID = Institutions.CSUEB;
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
            try {
                BufferedImage originalImage = ImageIO.read(new File(ClientConfig.CSUEB_LOGO_FILE_PATH));
                Image resizedImage = originalImage.getScaledInstance(50, 50, Image.SCALE_SMOOTH); // Resize to 50x50
                ImageIcon resizedIcon = new ImageIcon(resizedImage);

            int loginChoice = JOptionPane.showOptionDialog(null, panel, institutionID + " - CCES - Login",
                    JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE,
                    resizedIcon, loginChoices, loginChoices[0]);

            if (loginChoice == 1 || loginChoice == JOptionPane.CLOSED_OPTION) {
                // Exit application
                return;
            }

            String username = usernameField.getText();
            String password = passwordField.getText();
            String hostname = hostnameField.getText();
            String port = portField.getText();

            Client client = new Client(hostname, port);

            if (client.login(username, password, institutionID)) {
                // Show the portal if login is successful
                client.showPortal(username);
                break;
            } else {
                JOptionPane.showMessageDialog(null, "Invalid credentials or server connection issue.",
                        institutionID + "CCES - Authentication Error", JOptionPane.ERROR_MESSAGE,
                        new ImageIcon(ClientConfig.INVALID_CREDS_FILE_PATH));
            }
            } catch (IOException e) {
                System.err.println("Error File Path");
                e.printStackTrace();
            }
        }
    }
}
