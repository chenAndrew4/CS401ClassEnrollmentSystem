import java.io.*;
import java.net.Socket;

import javax.swing.JTextArea;

class ClientHandler implements Runnable {
		private final Socket clientSocket;
		private final Connection connectionStatus;
		private final Log log;
		private String clientIpAddrPort = null;

		// Constructor
		public ClientHandler(Socket socket, Log log)
		{
			this.clientSocket = socket;
			this.connectionStatus = new Connection();
			this.log = log;
			this.clientIpAddrPort = this.clientSocket.getInetAddress().getHostAddress().replace("/", "") + ":" + this.clientSocket.getPort();
		}
		
		public void run()
		{
			Message message = null;
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
				        // read the message from the socket
				        try {
							message = (Message) objectInputStream.readObject();
						} catch (ClassNotFoundException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
				        
				        this.log.receivedMessage(clientIpAddrPort, message);
				        
			        	if (message.type.equals(MessageType.LOGIN)) {
			        		Message response = new Message(MessageStatus.ERROR, "You are are neither verified or unverified!");
			        		
			        		if (this.connectionStatus.isUnVerified()) {
			        			this.connectionStatus.setVerified();
			        			response = new Message(MessageType.LOGIN, MessageStatus.SUCCESS, "Login successful!");
			        		} else {
			        			response = new Message(MessageType.LOGIN, MessageStatus.ERROR, "You are already logged in!");
			        		}
			        		
			        		this.log.sendingMessage(clientIpAddrPort, response);
			        		objectOutputStream.writeObject(response);
			        		
			        	} else if (message.type.equals(MessageType.LOGOUT)) {
			        		// Do not continue, terminate thread
			        		quit = true;
			        		
			        		Message response = new Message(MessageStatus.ERROR, "You are are neither verified or unverified!");
			        		
			        		if (this.connectionStatus.isVerified()) {
			        			this.connectionStatus.setUnVerified();
			        			response = new Message(MessageType.LOGOUT, MessageStatus.SUCCESS, "Logout successful!");
			        		} else {
			        			response = new Message(MessageType.LOGOUT, MessageStatus.ERROR, "You are already logged out!");
			        		}
			        		
			        		this.log.sendingMessage(clientIpAddrPort, response);
			        		objectOutputStream.writeObject(response);
			        		
			        	} else if (message.type.equals(MessageType.DEBUG)) {
			        		if (this.connectionStatus.isVerified()) {
			        			Message response = new Message(MessageType.DEBUG, MessageStatus.SUCCESS, message.text.toUpperCase());
			        			
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