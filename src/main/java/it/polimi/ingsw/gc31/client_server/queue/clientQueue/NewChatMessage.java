package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class NewChatMessage extends ClientQueueObject {

    String fromUsername;
    String toUsername = null;
    String message;

    public NewChatMessage(String username, String message) {
        this.fromUsername = username;
        this.message = message;
    }

    public NewChatMessage(String fromUsername, String toUsername, String message){
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.message = message;
    }

    @Override
    public void execute(UI ui) {
        if (toUsername == null)
            ui.show_chatMessage(fromUsername, message);
        else ui.show_privateChatMessage(fromUsername, toUsername, message);
    }

}
