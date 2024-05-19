package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class NewChatMessage implements ClientQueueObject {

    String username;
    String message;

    public NewChatMessage(String username, String message) {
        this.username = username;
        this.message = message;
    }

    @Override
    public void execute(UI ui) {
        ui.show_chatMessage(username, message);
    }

}
