package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowScorePlayerObj;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;

/**
 * This class defines a listener for receiving player score updates.
 * It implements the {@link Listener} interface with a generic type parameter of
 * Pair<String, Integer>
 * <ul>
 * <li>String: username of the player to whom the score belongs</li>
 * <li>Integer: the score</li>
 * </ul>
 */
public class PlayerScoreListener implements Listener<LinkedHashMap<String, Integer>> {
    private VirtualClient client;

    /**
     * Constructor that takes a {@link VirtualClient} instance as input.
     *
     * @param client the VirtualClients instance to be used for showing player
     *               scores.
     */
    public PlayerScoreListener(VirtualClient client) {
        this.client = client;
    }

    /**
     * This method is invoked whenever there is an update to a player's score.
     * It takes a Pair<String, Integer> object as input, where the first element is
     * the player's name (as a String)
     * and the second element is the player's score (as an Integer).
     * This method then calls the show_scorePlayer method on the client instance to
     * display the updated score.
     *
     * @param data a Pair<String, Integer> object containing the player's name and
     *             score.
     * @throws RemoteException This exception is thrown if there is an error
     *                         communicating with the VirtualClient.
     */
    @Override
    public void update(LinkedHashMap<String, Integer> data) throws RemoteException {
        client.sendCommand(new ShowScorePlayerObj(data));
    }
}
