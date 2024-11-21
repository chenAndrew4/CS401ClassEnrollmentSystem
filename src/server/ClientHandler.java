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
		private final Connection connectionStatus;
		private final Log log;
		private String clientIpAddPort = null;
//		private final SessionManager sessionManager;

		// Constructor
		public ClientHandler(Socket socket, Log log)
		{
			this.clientSocket = socket;
			this.connectionStatus = new Connection();
			this.log = log;
			this.clientIpAddPort = this.clientSocket.getInetAddress().getHostAddress().replace("/", "") + ":" + this.clientSocket.getPort();
//			this.sessionManager = sessionManager;
		}
//	// Constructor
//		public ClientHandler(Socket socket, Log log, SessionManager sessionManager)
//		{
//			this.clientSocket = socket;
//			this.connectionStatus = new Connection();
//			this.log = log;
//			this.clientIpAddrPort = this.clientSocket.getInetAddress().getHostAddress().replace("/", "") + ":" + this.clientSocket.getPort();
//
//		}

	@Override
	public void run() {
			System.out.println("thread executed");
		try (ObjectInputStream objectInputStream = new ObjectInputStream(clientSocket.getInputStream());
			 ObjectOutputStream objectOutputStream = new ObjectOutputStream(clientSocket.getOutputStream())) {

			boolean quit = false;

			while (!quit) {
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

		} catch (IOException | ClassNotFoundException e) {
			log.println("Error handling client " + clientIpAddPort + ": " + e.getMessage());
		} finally {
			try {
				clientSocket.close();
			} catch (IOException e) {
				log.println("Error closing client socket: " + e.getMessage());
			}
			log.println("Connection closed: " + clientIpAddPort);
		}
	}
		
//		public void run()
//		{
//			boolean quit = false;
//
//	        // get the input stream from the connected socket
//	        InputStream inputStream = null;
//			try {
//				inputStream = this.clientSocket.getInputStream();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        OutputStream outputStream = null;
//			try {
//				outputStream = this.clientSocket.getOutputStream();
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        // create a ObjectInputStream so we can read data from it.
//	        ObjectInputStream objectInputStream = null;
//			try {
//				objectInputStream = new ObjectInputStream(inputStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//	        ObjectOutputStream objectOutputStream = null;
//			try {
//				objectOutputStream = new ObjectOutputStream(outputStream);
//			} catch (IOException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//
//			try {
//				while (!quit) {
//			        while (inputStream.available() > 0) {
//			        	Message message = new Message();
//				        // read the message from the socket
//				        try {
//							message = (Message) objectInputStream.readObject();
//						} catch (ClassNotFoundException e) {
//							// TODO Auto-generated catch block
//							e.printStackTrace();
//						}
//
//				        // server.utils.Log what we received to the JTextArea
//				        this.log.receivedMessage(clientIpAddrPort, message);
//
//
//			        	if (message.getType().equals(MessageType.LOGIN)) {
//			        		Message response;
//
//			        		if (this.connectionStatus.isUnVerified()) {
//			        			User user = message.getUser();
//			        			// Check user credentials
//			        			if (this.users.isValidLogin(user.getUsername(), user.getPassword())) {
//				        			this.connectionStatus.setVerified();
//				        			User actualUser = this.users.getUser(user.getUsername());
//				        			response = new Message(MessageType.LOGIN, MessageStatus.SUCCESS, actualUser);
//			        			} else {
//			        				response = new Message(MessageType.LOGIN, MessageStatus.ERROR);
//			        			}
//			        		} else {
//			        			response = new Message(MessageType.LOGIN, MessageStatus.ERROR);
//			        		}
//
//			        		this.log.sendingMessage(clientIpAddrPort, response);
//			        		objectOutputStream.writeObject(response);
//
//			        	} else if (message.getType().equals(MessageType.LOGOUT)) {
//			        		// Do not continue, terminate thread
//			        		quit = true;
//
//			        		Message response;
//
//			        		if (this.connectionStatus.isVerified()) {
//			        			this.connectionStatus.setUnVerified();
//			        			response = new Message(MessageType.LOGOUT, MessageStatus.SUCCESS);
//			        		} else {
//			        			// shared.models.User is already logged out!
//			        			response = new Message(MessageType.LOGOUT, MessageStatus.ERROR);
//			        		}
//
//			        		this.log.sendingMessage(clientIpAddrPort, response);
//			        		objectOutputStream.writeObject(response);
//
//			        	} else if (message.getType().equals(MessageType.DEBUG)) {
//			        		if (this.connectionStatus.isVerified()) {
//			        			Message response = new Message(MessageType.DEBUG, MessageStatus.SUCCESS);
//
//				        		this.log.sendingMessage(clientIpAddrPort, response);
//				        		objectOutputStream.writeObject(response);
//			        		}
//			        	}
//			        }
//				}
//
//			}
//			catch (IOException e) {
//				e.printStackTrace();
//			}
//		}
	}