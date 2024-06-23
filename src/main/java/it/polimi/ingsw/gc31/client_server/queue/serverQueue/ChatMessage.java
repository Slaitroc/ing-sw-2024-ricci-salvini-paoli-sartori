package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public class ChatMessage extends ServerQueueObject {

    private final String message;
    private final String username;
    private String toUsername = null;


    public ChatMessage(String username, String message) {
        this.message = message;
        this.username = username;
    }

    public ChatMessage(String fromUsername, String toUsername , String message) {
        this.username = fromUsername;
        this.toUsername = toUsername;
        this.message = message;
    }

    @Override
    public void execute(GameController gameController) {
        if (toUsername == null)
            gameController.sendChatMessage(new NewChatMessage(username, message));
        else gameController.sendChatMessage(new NewChatMessage(username, toUsername, message));
    }

    @Override
    public void execute(Controller controller) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
