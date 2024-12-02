package shared.models;

import shared.enums.MessageClass;
import shared.enums.MessageStatus;
import shared.enums.MessageType;

import java.io.Serializable;

public class Message implements Serializable {
    private final MessageType type; // LOGIN, LOGOUT, GET, SET, DELETE, etc
    private MessageStatus status; // SUCCESS, ERROR, EXCEPTION,

    public Message(){
        this.type = null;
        this.status = null;
    }
    
    // for login or pass info to server
    public Message(MessageType type){
    	this.type = type;
        this.status = null;
    }
    
    // Used by the server to respond to the client's request
    public Message(MessageType type, MessageStatus status){
    	this.type = type;
    	this.status = status;
    }

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
        return "{" + "type=" + type + ", status=" + status + "}";
    }
}
