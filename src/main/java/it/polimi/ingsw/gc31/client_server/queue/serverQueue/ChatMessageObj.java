package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the chat messages.
 */
public class ChatMessageObj extends ServerQueueObject {
    /**
     * Is the message the player wants to write in the chat.
     */
    private final String message;
    /**
     * Is the username of the player sending the message.
     */
    private final String username;
    /**
     * Is the username of the player that should receive the message (private
     * message).
     */
    private String toUsername = null;

    /**
     * This is the constructor of the class.
     *
     * @param username is the username of the client sending the message.
     * @param message  is the string wrote by the client as the message.
     */
    public ChatMessageObj(String username, String message) {
        this.message = message;
        this.username = username;
    }

    /**
     * This is the constructor of the class when the message should be private.
     *
     * @param fromUsername is the username of the player sending the message.
     * @param toUsername   is the username of the player that should receive the
     *                     message.
     * @param message      is the content of the message.
     */
    public ChatMessageObj(String fromUsername, String toUsername, String message) {
        this.username = fromUsername;
        this.toUsername = toUsername;
        this.message = message;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from
     * the queue.
     * Invokes the {@link GameController#sendChatMessage(NewChatMessage)}.
     *
     * @param gameController is the reference to the {@link GameController}
     *                       associated with the client
     */
    @Override
    public void execute(GameController gameController) {
        if (toUsername == null)
            gameController.sendChatMessage(new NewChatMessage(username, message));
        else
            gameController.sendChatMessage(new NewChatMessage(username, toUsername, message));
    }

    /**
     * This method is executed if the object should be executed by the
     * {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should
     *                   execute the object.
     */
    @Override
    public void execute(Controller controller) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    /**
     * This method is executed if the object should be executed by the
     * {@link RmiServer} but should be
     * executed by the {@link GameController}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute
     *               the object.
     */
    @Override
    public void execute(RmiServer server) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
