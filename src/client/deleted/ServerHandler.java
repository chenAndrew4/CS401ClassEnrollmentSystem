package client.deleted;

import shared.enums.MessageType;
import shared.models.requests.BaseRequest;
import shared.models.responses.BaseResponse;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Map;

class ServerHandler {
	private String hostname;
	private String port;
	private Socket socket;
	static OutputStream outputStream;
	static InputStream inputStream;
	static ObjectOutputStream objectOutputStream;
	static ObjectInputStream objectInputStream;
	private Map<MessageType, ResponseHandler> responseHandlers;
	
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
	
	public String getHost() {
		return this.hostname;
	}
	
	public String getPort() {
		return this.port;
	}
	public void  setHost(String hostname) {
		this.hostname = hostname;
	}

	public void setPort(String port) {
		this.port = port;
	}
	
	public BaseResponse sendRequest(BaseRequest request) {
		BaseResponse response = new BaseResponse();
        
        // Send message 
        try {
			objectOutputStream.writeObject(request);
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
			response = (BaseResponse) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return response;
	}

	private void handleResponse() {
		try {
			BaseResponse response = (BaseResponse) objectInputStream.readObject();
			ResponseHandler handler = responseHandlers.get(response.getType());
			if (handler != null) {
				handler.handleResponse(response);
			} else {
				System.out.println("No handler found for response type: " + response.getType());
			}
		} catch (IOException | ClassNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void registerResponseHandler(MessageType messageType, ResponseHandler handler) {
		responseHandlers.put(messageType, handler);
	}

}