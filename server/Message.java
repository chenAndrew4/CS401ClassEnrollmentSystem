import java.io.Serializable;

public class Message implements Serializable {
    protected MessageType type;
    protected MessageStatus status;
    protected String text;

    public Message(){
        this.type = null;
        this.status = null;
        this.text = null;
    }
    
    // Can be used by both server and client but probably will not be used as much as the other two constructors below
    public Message(MessageType type, MessageStatus status, String text){
        setType(type);
        setStatus(status);
        setText(text);
    }
    
    // Primarily used by the server to respond to the client's request
    public Message(MessageStatus status, String text){
        setStatus(status);
        setText(text);
    }
    
    // Primarily used by the client to request the server to do something
    public Message(MessageType type, String text){
        setType(type);
        setText(text);
    }

    private void setType(MessageType type){
    	this.type = type;
    }

    public void setStatus(MessageStatus status){
    	this.status = status;
    }

    public void setText(String text){
    	this.text = text;
    }

    public MessageType getType(){
    	return this.type;
    }

    public MessageStatus getStatus(){
    	return this.status;
    }

    public String getText(){
    	return this.text;
    }
}
