package shared.models;

import shared.enums.MessageClass;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

import java.io.Serializable;

// IMPORTANT: BOTH SERVER AND CLIENT shared.models.Message.java MUST BE THE SAME! A CHANGE IN EITHER ONE MUST REFLECT THE OTHER!
// OTHERWISE YOU WILL HAVE SERIALIZATION PROBLEMS ON EITHER END!

public class Message implements Serializable {
    private final MessageType type; // LOGIN, LOGOUT, GET, SET, DELETE, etc
    private MessageStatus status; // SUCCESS, ERROR, EXCEPTION,
//    private Object content;

//    // Classes to pass back and forth from the server and client
//    protected User user;
//    protected Users users;

    public Message(){
        this.type = null;
        this.status = null;
//        content = null;
//        this.user = null;
//        this.users = null;
    }
    
    // for login or pass info to server
    public Message(MessageType type){
    	this.type = type;
        this.status = null;
//        this.content = content;
    }
    
//    // For logging in, logging out, editing a user, adding a user, or removing a user. Anything user related.
//    public Message(MessageType type, User user){
//    	this.type = type;
//    	this.user = user;
//    }
    
    // Used by the server to respond to the client's request
    public Message(MessageType type, MessageStatus status){
    	this.type = type;
    	this.status = status;
    }
    
//    // We will need to add message constructors for specific classes, i.e. for receiving shared.models.Course objects from client
//    public Message(MessageType type, MessageClass messageclass, Course course, MessageType type1) {
//        this.type = type;
//    }

    public Message setStatus(MessageStatus status) {
        this.status = status;
        return this;
    }
    
    public MessageType getType() {
    	return this.type;
    }
    
    public MessageStatus getStatus() {
    	return this.status;
    }

    @Override
    public String toString() {
        return "Message{" +
                "type=" + type +
                ", status=" + status +
                '}';
    }
    
//    public User getUser() {
//    	return this.user;
//    }
}
