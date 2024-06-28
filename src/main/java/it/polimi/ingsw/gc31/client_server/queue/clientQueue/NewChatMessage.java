package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * NewChatMessage is a class that extends ClientQueueObject
 * It is sent to the client to notify a new message in the chat.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class NewChatMessage extends ClientQueueObject {

    /**
     * The username of the player who sent the message
     */
    String fromUsername;

    /**
     * The username of the player who receive the message.
     * It is set to null by default.
     */
    String toUsername = null;

    /**
     * Content of the message.
     */
    String message;

    /**
     * NewChatMessage is a class that extends ClientQueueObject
     * Notify a new message in the chat.
     */
    public NewChatMessage(String username, String message) {
        this.fromUsername = username;
        this.message = message;
    }

    /**
     * Notify a new message in the chat.
     * @param fromUsername username of the player who sent the message.
     * @param toUsername username of the player who receive the message.
     * @param message content of the message.
     */
    public NewChatMessage(String fromUsername, String toUsername, String message){
        this.fromUsername = fromUsername;
        this.toUsername = toUsername;
        this.message = message;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show a new message.
     * If the message is private the variable toUsername is set to null.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        if (toUsername == null)
            ui.show_chatMessage(fromUsername, message);
        else ui.show_privateChatMessage(fromUsername, toUsername, message);
    }

}
