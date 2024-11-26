package server;

import server.dataManagers.DataSaveManager;
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

	public static void main(String[] args) throws IOException {
		ServerGUI gui = new ServerGUI();
		Server server = new Server(gui);
		server.start();
	}

	public Server(ServerGUI gui) throws IOException {
		this.gui = gui;
		this.serverManager = new ServerManager();
		this.pool = Executors.newCachedThreadPool();
		this.log = Log.getInstance(ServerGUI.logTextArea);
	}

	public void start() {
		try {
			// Register shutdown hook
			Runtime.getRuntime().addShutdownHook(new Thread(() -> {
				System.out.println("Server is shutting down. Saving all data...");
				DataSaveManager.getInstance().saveAll();
			}));
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
					pool.execute(new ClientHandler(clientSocket, log));
				} catch (IOException e) {
					System.err.println("Error accepting client connection: " + e.getMessage());
				}
			}
		} catch (IOException e) {
			log.println("Server Error: " + e.getMessage());
		} finally {
			end();
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
}