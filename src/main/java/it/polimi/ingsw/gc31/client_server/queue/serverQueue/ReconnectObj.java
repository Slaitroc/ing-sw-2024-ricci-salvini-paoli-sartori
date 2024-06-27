package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents the action of reconnecting to a game.
 */
public class ReconnectObj extends ServerQueueObject {
    /**
     * Is the boolean value set to true if the client wants to rejoin a game, false otherwise.
     */
    private boolean esito;
    /**
     * Is the value of the client token.
     */
    private int token;
    /**
     * Is the value of the client tempToken.
     */
    private int tempToken;

    /**
     * This is the constructor of the class.
     *
     * @param esito     indicates if the client wants to reconnect or not.
     * @param username  is the username of the player that wants to reconnect.
     * @param tempToken is the value of the tempToken.
     * @param token     is the value of the token.
     */
    public ReconnectObj(boolean esito, String username,int tempToken, int token) {
        this.esito = esito;
        this.token = token;
        this.tempToken = tempToken;
    }

    /**
     * This method is executed by the {@link Controller} when it is polled from the queue.
     * Invokes the {@link Controller#rejoin(int, int, boolean)} method.
     *
     * @param controller is the reference to the {@link Controller} associated with the client.
     */
    @Override
    public void execute(Controller controller) {
        controller.rejoin(tempToken, token, esito);
    }

    /**
     * This method is executed if the object should be executed by the {@link GameController} but should be
     * executed by the {@link Controller}.
     *
     * @param gameController is the reference to the {@link GameController} that should execute the object.
     */
    @Override
    public void execute(GameController gameController) {
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link Controller}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {
    }

}
