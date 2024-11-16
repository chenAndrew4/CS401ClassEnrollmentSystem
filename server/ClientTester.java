import java.io.*;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ClientTester {

    public static void main(String[] args) throws IOException {
    	Message response = null;
    	
        Scanner sc= new Scanner(System.in); //System.in is a standard input stream.
//        System.out.print("Enter the host address to connect to: <localhost> ");
//        String host = sc.nextLine();
//        System.out.print("Enter the port number to connect to: <1234> ");
//        int port = sc.nextInt();

        // Connect to the ServerSocket at host:port
//        Socket socket = new Socket(host, port);
//        System.out.println("Connected to " + host + ":" + port);
    	
        Socket socket = new Socket("127.0.0.1", 25800);
        System.out.println("Connected to 127.0.0.1:25800");

        // Output stream socket.
        OutputStream outputStream = socket.getOutputStream();
        InputStream inputStream = socket.getInputStream();

        // Create object output stream from the output stream to send an object through it
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
        
        // create a ObjectInputStream so we can read data from it.
        ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
        

        // List of Message objects
        // List<Message> messages = new ArrayList<>();
        // messages.add(new Message(MessageType.TEXT, "Hello world!"));
        // messages.add(new Message(MessageType.LOGIN, "Can i please login?"));
        // messages.add(new Message(MessageType.TEXT, "Can i haz cheeseburger?"));
        // messages.add(new Message(MessageType.TEXT, "¯\\_(ツ)_/¯"));
        // messages.add(new Message(MessageType.LOGOUT, "Can i please logout?"));

        // Send login message to server
        System.out.println("Logging into server...");
        objectOutputStream.writeObject(new Message(MessageType.LOGIN, "Can i please login?"));
        objectOutputStream.flush();
        
        // Read from sever and cast to Message object
        try {
			response = (Message) objectInputStream.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        // If we were able to login successfully...
        if (response.type == MessageType.LOGIN && response.status == MessageStatus.SUCCESS) {
        	System.out.println("Logged into server successfully!");
        	
        	while (true) {
        		// Prompt user what to send to the server
        		System.out.print("Enter a message to send to the server> ");
        		String text = sc.nextLine();
        		
        		// Did we type 'logout'?
        		if (text.equalsIgnoreCase("logout")) {
        			objectOutputStream.writeObject(new Message(MessageType.LOGOUT, "Can i please logout?"));
        			objectOutputStream.flush();
        			
        			try {
						response = (Message) objectInputStream.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
        			
        			// Display response, close the socket, and terminate the loop
        			System.out.println("Server replied: " + response.text);
        			socket.close();
        			return;
        		}
        		
        		// Send to server
        		objectOutputStream.writeObject(new Message(MessageType.DEBUG, text));
        		objectOutputStream.flush();
        		
        		// Read from sever and cast to Message object
        		try {
					response = (Message) objectInputStream.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
        		
        		// Display what we received
        		if (response.type == MessageType.DEBUG && response.status == MessageStatus.SUCCESS)
        			System.out.println("Server replied: " + response.text);
        	}
        }
        
    }
    

}
