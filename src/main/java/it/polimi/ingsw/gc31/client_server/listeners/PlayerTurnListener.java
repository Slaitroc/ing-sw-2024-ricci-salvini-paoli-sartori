package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayerTurnObj;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.util.Map;

/**
 * This class represents a listener that handles updates for the turn of a specific player.
 * It sends the state in game of a player.
 *
 * @author sslvo
 */
public class PlayerTurnListener extends Listener{

    /**
     * This class represents a listener that handles updates for the turn of a specific player.
     * It sends the state in game of a player.
     */
    public PlayerTurnListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the state in game of the player and sends it to all clients.
     *
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     */
    @Override
    public void update(GameModel model, String username) {
        String turnInfo = "";
        if (model.getPlayers().get(username).infoState().equals("notplaced"))
            turnInfo = "Play a card";
        else if (model.getPlayers().get(username).infoState().equals("placed"))
            turnInfo = "Draw a card";
        else if (model.getPlayers().get(username).infoState().equals("waiting"))
            turnInfo = "It is not your turn";
        else if (model.getPlayers().get(username).infoState().equals("start"))
            turnInfo = "Choose an objective card and play the starter";
        ClientQueueObject clientQueueObject = new ShowPlayerTurnObj(username, turnInfo);
        for (Map.Entry<String, VirtualClient> clients: clients.entrySet()) {
            sendUpdate(model, clients.getKey(), clients.getValue(), clientQueueObject);
        }
    }
}
