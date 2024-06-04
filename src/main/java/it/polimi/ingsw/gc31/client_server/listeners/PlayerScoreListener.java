package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowScorePlayerObj;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;

/**
 * This class defines a listener for receiving player score updates.
 * It implements the {@link Listener} interface with a generic type parameter of Pair<String, Integer>
 * <ul>
 *     <li>String: username of the player to whom the score belongs</li>
 *     <li>Integer: the score</li>
 * </ul>
 */
public class PlayerScoreListener implements Listener {
    private final List<VirtualClient> clients;

    public PlayerScoreListener(List<VirtualClient> clients) {
        this.clients = clients;
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        for (VirtualClient client : clients) {
            client.sendCommand(
                    new ShowScorePlayerObj(model.getBoard().getPlayersScore(username))
            );
        }
    }
}
