package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

import java.rmi.RemoteException;

/**
 * This class represents the action of rejoining a game. This class execute a method that invokes a method
 * from the {@link GameController}.
 */
public class ReJoinObj extends ServerQueueObject{
    /**
     * Is the username of the player that wants to rejoin.
     */
    private final String username;
    /**
     * Is the reference to the {@link VirtualClient} of the player that wants to rejoin.
     */
    private final VirtualClient client;

    /**
     * This class is the constructor of the class.
     *
     * @param client    is the reference to the {@link VirtualClient} that wants to rejoin.
     * @param username  is the username of the player that wants to rejoin.
     */
    public ReJoinObj(VirtualClient client, String username) {
        this.username = username;
        this.client = client;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#reJoinGame(String, VirtualClient)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        try {
            gameController.reJoinGame(username, client);
        } catch (RemoteException e) {

        }
    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {
    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link GameController}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {

    }
}
