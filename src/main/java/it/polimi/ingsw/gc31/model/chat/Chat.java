package it.polimi.ingsw.gc31.model.chat;

import java.util.ArrayList;
import java.util.List;

// I messaggio sono identificati da sender e recipient
// Un player può vedere tutti i messaggi che hanno come sender o recipient il suo nickname (tanto è univoco)
// Esistono la chat globale e le chat private
// Se recipient == null allora il messaggio è mandato sulla chat vocale
// altrimenti il messaggio può essere visto solo da sender e recipient
public class Chat {
    private List<Message> messageList;

    public Chat() {
        messageList = new ArrayList<>();
    }

    public void send(String msg, String sender, String recipient) {
        Message message = new Message(msg, sender, recipient);
    }
}
