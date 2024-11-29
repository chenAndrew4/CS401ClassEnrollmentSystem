package server;

import server.handlers.RequestHandler;
import server.utils.Log;
import shared.enums.MessageType;
import shared.models.Message;
import shared.models.requests.BaseRequest;
import shared.models.responses.BaseResponse;

import java.io.*;
import java.net.Socket;

class ClientHandler implements Runnable {
		private final Socket clientSocket;
		private final Log log;
		private String clientIpAddPort = null;

		// Constructor
		public ClientHandler(Socket socket, Log log)
		{
			this.clientSocket = socket;
			this.log = log;
			this.clientIpAddPort = this.clientSocket.getInetAddress().getHostAddress().replace("/", "") + ":" + this.clientSocket.getPort();		}

	@Override
	public void run() {
		try {
			InputStream inputStream = null;
			OutputStream outputStream = null;
			
			inputStream = this.clientSocket.getInputStream();
			outputStream = this.clientSocket.getOutputStream();
			
			ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream());

			boolean quit = false;

			while (!quit) {
				while (inputStream.available() > 0) {
					// Read message from client
					BaseRequest request = (BaseRequest) objectInputStream.readObject();
					log.receivedMessage(clientIpAddPort, request);

					// Delegate message handling to the appropriate handler
					BaseResponse response = RequestHandler.handleRequest(request, log);

					if (response != null) {
						log.sendingMessage(clientIpAddPort, response);
						objectOutputStream.writeObject(response);
					}

					// Handle LOGOUT message specifically to terminate the connection
					if (request.getType() == MessageType.LOGOUT) {
						quit = true;
					}
				}
			}

		} catch (IOException | ClassNotFoundException e) {
			log.exception("Error handling client " + clientIpAddPort + ": " + e.toString());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				log.exception("Error closing client socket: " + e.toString());
			}
			log.println("Connection closed: " + clientIpAddPort);
		}
	}
}