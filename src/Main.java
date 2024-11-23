import client.gui.login.BaseLoginGUI;
import client.gui.login.CSUEBLoginGUI;
import client.gui.login.CSUFLoginGUI;
import client.gui.login.SJSULoginGUI;
import server.Server;

import javax.swing.*;
import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        // Start the server in a separate thread
        new Thread(() -> {
            try {
                Server.main(new String[0]); // Start the server
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();

        // Launch the GUI
        SwingUtilities.invokeLater(() -> {
            BaseLoginGUI loginGUI = new CSUEBLoginGUI();
            loginGUI.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            BaseLoginGUI loginGUI = new SJSULoginGUI();
            loginGUI.setVisible(true);
        });
        SwingUtilities.invokeLater(() -> {
            BaseLoginGUI loginGUI = new CSUFLoginGUI();
            loginGUI.setVisible(true);
        });
    }
}
