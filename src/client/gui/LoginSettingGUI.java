package client.gui;

import javax.swing.*;
import java.awt.*;

public class LoginSettingGUI extends JFrame {
    public LoginSettingGUI() {
        setTitle("Settings");
        setSize(300, 200);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout());

        JPanel panel = new JPanel(new GridLayout(2, 2));

        JTextField hostnameField = new JTextField("localhost");
        JTextField portField = new JTextField("12345");

        panel.add(new JLabel("Hostname:"));
        panel.add(hostnameField);
        panel.add(new JLabel("Port:"));
        panel.add(portField);

        JButton saveButton = new JButton("Save");
        saveButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        saveButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Settings updated: Hostname=" + hostnameField.getText() + ", Port=" + portField.getText());
            dispose();
        });

        add(panel, BorderLayout.CENTER);
        add(saveButton, BorderLayout.SOUTH);

        setVisible(true);
    }
}
