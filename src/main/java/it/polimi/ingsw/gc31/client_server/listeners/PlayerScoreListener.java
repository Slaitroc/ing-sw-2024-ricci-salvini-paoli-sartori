package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowScorePlayerObj;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * This class represents a listener for player scores in a game model.
 * It sends the score of all players to a specific client.
 *
 * @author sslvo
 */
public class PlayerScoreListener extends Listener {

    public PlayerScoreListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the score of all players and send it to a
     * specific client
     *
     * @param model    The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     * @throws RemoteException if there is a remote communication error.
     */
    @Override
    public void update(GameModel model, String username) throws RemoteException {
        clients.get(username).sendCommand(new ShowScorePlayerObj(model.getBoard().getPlayersScore()));
    }
}
