package client;

import shared.models.Message;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;

class ServerHandler {
	private String hostname;
	private String port;
	private Socket socket;
	static OutputStream outputStream;
	static InputStream inputStream;
	static ObjectOutputStream objectOutputStream;
	static ObjectInputStream objectInputStream;
	
	public ServerHandler(String hostname, String port) throws Exception {
		this.hostname = hostname;
		this.port = port;
		this.socket = null;
		
		try {
			this.socket = new Socket(this.hostname, Integer.parseInt(this.port));
			
	        // Output stream socket.
	        outputStream = null;
			try {
				outputStream = this.socket.getOutputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
			// Input stream socket
			inputStream = null;
			try {
				inputStream = this.socket.getInputStream();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

	        // Create object output stream from the output stream to send an object through it
			objectOutputStream = null;
	        try {
				objectOutputStream = new ObjectOutputStream(this.outputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	        // Create a ObjectInputStream so we can read data from it.
	        objectInputStream = null;
	        try {
				objectInputStream = new ObjectInputStream(this.inputStream);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (UnknownHostException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new Exception("Invalid hostname and/or port!");
		}
	}
	
	public void closeSocket() {
		try {
			if (!socket.isClosed())
				this.socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean hasHostnamePortChanged(String hostname, String port) {
		return !this.hostname.equals(hostname) || !this.port.equals(port);
	}
	
	public String getHostname() {
		return this.hostname;
	}
	
	public String getPort() {
		return this.port;
	}
	
	public Message sendMessage(Message message) {
		Message response = new Message();
        
        // Send message 
        try {
			objectOutputStream.writeObject(message);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        try {
			objectOutputStream.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        
        // Wait and read server response
        try {
			response = (Message) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}
	
}