package server;

import server.gui.ServerGUI;
import server.utils.Log;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {
	private ServerManager serverManager;
	private ExecutorService pool;
	private Log log;
	private ServerGUI gui;
	private ServerSocket serverSocket;
	private SessionManager sessionManager = new SessionManager();
	private UserManager userManager;
	private CourseManager courseManager;

	public static void main(String[] args) {
		ServerGUI gui = new ServerGUI();
		Server server = new Server(gui);
		server.start();
	}

	public Server(ServerGUI gui) {
		this.gui = gui;
		this.serverManager = new ServerManager();
		this.pool = Executors.newCachedThreadPool();
		this.log = gui.getLog();
		this.userManager = new UserManager(log);
		this.userManager.importDB();
		this.courseManager = new CourseManager();
	}

//	public SessionManager getSessionManager() {
//		return sessionManager;
//	}

	public void start() {
		try {
			log.println("Server is bound to: " + serverManager.getIpAddress().toString().replace("/", "") + ":" + serverManager.getPort());
		} catch (UnknownHostException e) {
			log.println("Error retrieving IP Address: " + e.getMessage());
		}
		try {
			serverSocket = new ServerSocket(serverManager.getPort(), 0, serverManager.getIpAddress());
			serverSocket.setReuseAddress(true);
			log.println("Server started on: " + serverManager.getIpAddress() + ":" + serverManager.getPort());

			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					log.println("New Connection from: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());
					pool.execute(new ClientHandler(clientSocket, log, userManager, courseManager,sessionManager));
//					pool.submit(new ClientHandler(clientSocket, log, new Users(log)));
				} catch (IOException e) {
					System.err.println("Error accepting client connection: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			log.println("Server Error: " + e.getMessage());
		} finally {
			pool.shutdown();
			log.println("Server shut down.");
		}
	}
	public void end() {
		try {
			if (serverSocket != null) {
				serverSocket.close();
			}
			pool.shutdown();
			log.println("Server shut down.");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
//	public static void main(String[] args) {
//		ServerManager serverManager = new ServerManager();
//		ServerSocket server = null;
//		Log log = null;
//
//		// Render GUI
//
//		JFrame severLogFrame = new JFrame();
//		severLogFrame.setTitle("College shared.models.Course Enrollment System - Server");
//		severLogFrame.setIconImage(new ImageIcon("assets/icons/server_32.png").getImage());
//		severLogFrame.setResizable(false);
//
//        JTextArea logTextArea = new JTextArea(25, 50);
//        JScrollPane scrollPane = new JScrollPane(logTextArea);
//        logTextArea.setEditable(false);
//
//        JPanel mainPanel = new JPanel(new BorderLayout(10,10));
//        mainPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
//        mainPanel.add(scrollPane, BorderLayout.CENTER);
//
//		severLogFrame.add(mainPanel);
//		severLogFrame.pack();
//
//		severLogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		severLogFrame.setLocationRelativeTo(null);
//		severLogFrame.setVisible(true);
//
//		log = new Log(logTextArea);
//
//		// Import databases
//		Users users = new Users(log);
//
//		users.importDB();
//
//		try {
//			log.println("Server is binded to: " + serverManager.getIpAddress().toString().replace("/", "") + ":" + serverManager.getPort());
//		} catch (UnknownHostException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		try {
//			// Server listens to...
//			server = new ServerSocket(serverManager.getPort(), 0, serverManager.getIpAddress());
//			server.setReuseAddress(true);
//
//			// running infinite loop for getting
//			// client request
//			while (true) {
//
//				// socket object to receive incoming client
//				// requests
//				Socket client = server.accept();
//
//				// Displaying that new client is connected
//				// to server
//				log.println("New Connection from: " + client.getInetAddress().getHostAddress() + ":" + client.getPort());
//
//				// create a new thread object
//				ClientHandler clientSock = new ClientHandler(client, log, users);
//
//				// This thread will handle the client
//				// separately
//				new Thread(clientSock).start();
//			}
//		}
//		catch (IOException e) {
//			e.printStackTrace();
//		}
//		finally {
//			if (server != null) {
//				try {
//					server.close();
//				}
//				catch (IOException e) {
//					e.printStackTrace();
//				}
//			}
//		}
//
//	}
}