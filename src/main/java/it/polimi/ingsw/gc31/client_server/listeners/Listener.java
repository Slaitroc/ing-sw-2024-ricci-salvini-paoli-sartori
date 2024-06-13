package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * This interface represents a generic listener that handles updates for virtual clients.
 * Listeners are designed to retrieve updated data from the game model and send it to the clients.
 * An update concerns a specific player but is still sent to all clients in the game.
 * It is the responsibility of the clients to ignore the updates that do not concern them.
 *
 * @author sslvo
 */
public abstract class Listener {
    /**
     * Map containing the virtual clients identified by their username
     * Subclasses can use this map to send data to the clients.
     */
    final Map<String, VirtualClient> clients;

    /**
     * Constructs a Listener with the specified map of virtual clients.
     *
     * @param clients a map where the key is a String representing the username and the value is a VirtualClient object
     */
    Listener(Map<String, VirtualClient> clients) {
        this.clients = clients;
    }

    /**
     * This abstract method defines the behavior for handling updates from the game model.
     * Concrete subclasses must implement this method to retrieve a specific data, of the player identified by the username, from the game
     * model and send it to all clients.
     *
     * @param model The current game model object containing the updated information.
     * @param username The username of the player associated with the update.
     * @throws RemoteException This exception is thrown if a communication error occurs while interacting with the remote client.
     */
    abstract void update(GameModel model, String username) throws RemoteException;
}
