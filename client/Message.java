import java.io.Serializable;

// IMPORTANT: BOTH SERVER AND CLIENT Message.java MUST BE THE SAME! A CHANGE IN EITHER ONE MUST REFLECT THE OTHER!
// OTHERWISE YOU WILL HAVE SERIALIZATION PROBLEMS ON EITHER END!

public class Message implements Serializable {
    protected MessageType type; // LOGIN, LOGOUT, GET, SET, DELETE, etc
    protected MessageStatus status; // SUCCESS, ERROR, EXCEPTION, 
        
    // Classes to pass back and forth from the server and client
    protected User user;
    protected Users users;

    public Message(){
        this.type = null;
        this.status = null;
        
        this.user = null;
        this.users = null;
    }
    
    // COULD be used by the server to respond to generic non-data-returning requests
    public Message(MessageType type, MessageStatus status){
    	this.type = type;
    	this.status = status;
    }
    
    // For logging in, logging out, editing a user, adding a user, or removing a user. Anything user related.
    public Message(MessageType type, User user){
    	this.type = type;
    	this.user = user;
    }
    
    // Primarily used by the server to respond to the client's request
    public Message(MessageType type, MessageStatus status, User user){
    	this.type = type;
    	this.status = status;
    	this.user = user;
    }
    
    // We will need to add message constructors for specific classes, i.e. for receiving Course objects from client
    public Message(MessageType type, MessageClass messageclass, Course course) {
    	
    }
    
    public MessageType getType() {
    	return this.type;
    }
    
    public MessageStatus getStatus() {
    	return this.status;
    }
    
    public User getUser() {
    	return this.user;
    }
}
