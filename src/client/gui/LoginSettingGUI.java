package client.gui;

import client.Client;
import client.ClientConfig;

import javax.swing.*;
import java.awt.*;


public class LoginSettingGUI extends JDialog {
    public LoginSettingGUI() {
        setTitle("Server Settings");
        setSize(300, 150);
        setLayout(new GridLayout(2, 2));

        JTextField hostField = new JTextField(ClientConfig.SERVER_ADDRESS, 15);
        JTextField portField = new JTextField(String.valueOf(ClientConfig.SERVER_PORT), 15);

        add(new JLabel("Host:"));
        add(hostField);
        add(new JLabel("Port:"));
        add(portField);

        int result = JOptionPane.showConfirmDialog(
                this,
                this.getContentPane(),
                "Server Settings",
                JOptionPane.OK_CANCEL_OPTION,
                JOptionPane.PLAIN_MESSAGE
        );

        if (result == JOptionPane.OK_OPTION) {
            ClientConfig.SERVER_ADDRESS = hostField.getText();
            try {
                ClientConfig.SERVER_PORT = Integer.parseInt(portField.getText());
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Invalid port number. Please try again.", "Error", JOptionPane.ERROR_MESSAGE, new ImageIcon(ClientConfig.ERROR_ICON));
            }
        }
        setVisible(true);
        dispose();
    }
}
//public class LoginSettingGUI extends JFrame {
//    public LoginSettingGUI() {
//        setTitle("Settings");
//        setSize(300, 200);
//        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        setLayout(new BorderLayout());
//
//        JPanel panel = new JPanel(new GridLayout(2, 2));
//
//        JTextField hostnameField = new JTextField("localhost");
//        JTextField portField = new JTextField("12345");
//
//        panel.add(new JLabel("Hostname:"));
//        panel.add(hostnameField);
//        panel.add(new JLabel("Port:"));
//        panel.add(portField);
//
//        JButton saveButton = new JButton("Save");
//        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);
//
//        saveButton.addActionListener(e -> {
//            JOptionPane.showMessageDialog(this, "Settings updated: Hostname=" + hostnameField.getText() + ", Port=" + portField.getText());
//            dispose();
//        });
//
//        add(panel, BorderLayout.CENTER);
//        add(saveButton, BorderLayout.SOUTH);
//
//        setVisible(true);
//    }
//}
