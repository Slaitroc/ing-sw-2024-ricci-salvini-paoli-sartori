package it.polimi.ingsw.gc31.model.chat;

public class Message {
    private String message;
    private String sender;
    private String recipient;

    public Message(String message, String sender, String recipient) {
        this.message = message;
        this.sender = sender;
        this.recipient = recipient;
    }
}
