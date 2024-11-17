import java.io.*;
import java.net.Socket;

import javax.swing.JTextArea;

class ClientHandler implements Runnable {
		private final Socket clientSocket;
		private final Connection connectionStatus;
		private final Log log;
		private String clientIpAddrPort = null;
		private Users users;

		// Constructor
		public ClientHandler(Socket socket, Log log, Users users)
		{
			this.clientSocket = socket;
			this.connectionStatus = new Connection();
			this.log = log;
			this.clientIpAddrPort = this.clientSocket.getInetAddress().getHostAddress().replace("/", "") + ":" + this.clientSocket.getPort();
			this.users = users;
		}
		
		public void run()
		{
			boolean quit = false;
			
	        // get the input stream from the connected socket
	        InputStream inputStream = null;
			try {
				inputStream = this.clientSocket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        OutputStream outputStream = null;
			try {
				outputStream = this.clientSocket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        // create a ObjectInputStream so we can read data from it.
	        ObjectInputStream objectInputStream = null;
			try {
				objectInputStream = new ObjectInputStream(inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
	        ObjectOutputStream objectOutputStream = null;
			try {
				objectOutputStream = new ObjectOutputStream(outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				        
			try {
				while (!quit) {
			        while (inputStream.available() > 0) {
			        	Message message = new Message();
				        // read the message from the socket
				        try {
							message = (Message) objectInputStream.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
				        // Log what we received to the JTextArea
				        this.log.receivedMessage(clientIpAddrPort, message);

				        
			        	if (message.type.equals(MessageType.LOGIN)) {
			        		Message response;
			        		
			        		if (this.connectionStatus.isUnVerified()) {
			        			User user = message.user;
			        			// Check user credentials
			        			if (this.users.isValidLogin(user.getUsername(), user.getPassword())) {
				        			this.connectionStatus.setVerified();
				        			response = new Message(MessageType.LOGIN, MessageStatus.SUCCESS);
			        			} else {
			        				response = new Message(MessageType.LOGIN, MessageStatus.ERROR);
			        			}
			        		} else {
			        			response = new Message(MessageType.LOGIN, MessageStatus.ERROR);
			        		}
			        		
			        		this.log.sendingMessage(clientIpAddrPort, response);
			        		objectOutputStream.writeObject(response);
			        		
			        	} else if (message.type.equals(MessageType.LOGOUT)) {
			        		// Do not continue, terminate thread
			        		quit = true;
			        		
			        		Message response;
			        		
			        		if (this.connectionStatus.isVerified()) {
			        			this.connectionStatus.setUnVerified();
			        			response = new Message(MessageType.LOGOUT, MessageStatus.SUCCESS);
			        		} else {
			        			// User is already logged out!
			        			response = new Message(MessageType.LOGOUT, MessageStatus.ERROR);
			        		}
			        		
			        		this.log.sendingMessage(clientIpAddrPort, response);
			        		objectOutputStream.writeObject(response);
			        		
			        	} else if (message.type.equals(MessageType.DEBUG)) {
			        		if (this.connectionStatus.isVerified()) {
			        			Message response = new Message(MessageType.DEBUG, MessageStatus.SUCCESS);
			        			
				        		this.log.sendingMessage(clientIpAddrPort, response);
				        		objectOutputStream.writeObject(response);
			        		}
			        	}
			        }
				}
		        
			}
			catch (IOException e) {
				e.printStackTrace();
			}
		}
	}