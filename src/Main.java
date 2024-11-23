import client.gui.BaseLoginGUI;
import client.gui.CSUEB.CSUEBLoginGUI;
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
    }
}
