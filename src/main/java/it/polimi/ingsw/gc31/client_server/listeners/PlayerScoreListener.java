package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowScorePlayerObj;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
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
    public void update(GameModel model, String username) {
        LinkedHashMap<String, Pair<Integer, Boolean>> res = new LinkedHashMap<>();
        for (String usr : model.getBoard().getPlayersScore().keySet()) {
            boolean inTurn = model.getPlayers().get(usr).infoState().equals("notplaced") || model.getPlayers().get(usr).infoState().equals("placed");
            res.put(usr, new Pair<>(model.getBoard().getPlayersScore().get(usr), inTurn));
        }
        ClientQueueObject clientQueueObject = new ShowScorePlayerObj(res);
        sendUpdate(model, username, clients.get(username), clientQueueObject);
    }
}
