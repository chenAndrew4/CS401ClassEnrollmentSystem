import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;

import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Server {
	public static void main(String[] args) {
		ServerManager serverManager = new ServerManager();
		ServerSocket server = null;
		Log log = null;
		
		JFrame severLogFrame = new JFrame();
		severLogFrame.setTitle("College Course Enrollment System - Server");
		severLogFrame.setIconImage(new ImageIcon("assets/icons/server_32.png").getImage());
		severLogFrame.setResizable(false);
		
        JTextArea logTextArea = new JTextArea(25, 50);
        JScrollPane scrollPane = new JScrollPane(logTextArea);
        logTextArea.setEditable(false);

        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        mainPanel.add(scrollPane, BorderLayout.CENTER);
		
		severLogFrame.add(mainPanel);
		severLogFrame.pack();
		
		severLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		severLogFrame.setLocationRelativeTo(null);
		severLogFrame.setVisible(true);
		
		log = new Log(logTextArea);
		
		try {
			log.println("Server is binded to: " + serverManager.getIpAddress().toString().replace("/", "") + ":" + serverManager.getPort());
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			// Server listens to...
			server = new ServerSocket(serverManager.getPort(), 0, serverManager.getIpAddress());
			server.setReuseAddress(true);

			// running infinite loop for getting
			// client request
			while (true) {

				// socket object to receive incoming client
				// requests
				Socket client = server.accept();

				// Displaying that new client is connected
				// to server
				log.println("New Connection from: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());

				// create a new thread object
				ClientHandler clientSock = new ClientHandler(client, log);

				// This thread will handle the client
				// separately
				new Thread(clientSock).start();
			}
		}
		catch (IOException e) {
			e.printStackTrace();
		}
		finally {
			if (server != null) {
				try {
					server.close();
				}
				catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
}