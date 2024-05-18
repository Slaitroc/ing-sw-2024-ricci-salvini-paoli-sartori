package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ChatMessage implements ServerQueueObject {

    String message;
    String username;

    public ChatMessage(String username, String message) {
        this.message = message;
        this.username = username;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.sendChatMessage(new NewChatMessage(username, message));
    }

    @Override
    public void execute(Controller controller) {
    }

}
