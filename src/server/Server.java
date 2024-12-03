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
				log.println("Server is shutting down. Saving all data...");
				DataSaveManager.getInstance().saveAll();
				System.out.println("Server is shutting down. Saving all data...");
			}));
			log.println("Server is bound to: " + serverManager.getIpAddress().toString().replace("/", "") + ":" + serverManager.getPort());
		} catch (UnknownHostException e) {
			log.exception("Error retrieving IP Address: " + e.toString());
		}
		try {
			serverSocket = new ServerSocket(serverManager.getPort(), 0, serverManager.getIpAddress());
			serverSocket.setReuseAddress(true);
			while (true) {
				try {
					Socket clientSocket = serverSocket.accept();
					log.println("New Connection from: " + clientSocket.getInetAddress().getHostAddress() + ":" + clientSocket.getPort());
					pool.execute(new ClientHandler(clientSocket, log));
				} catch (IOException e) {
					log.exception(e.toString());
				}
			}
		} catch (IOException e) {
			log.exception(e.toString());
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