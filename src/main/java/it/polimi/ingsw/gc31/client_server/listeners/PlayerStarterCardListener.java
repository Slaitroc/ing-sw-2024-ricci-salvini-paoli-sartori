package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowStarterCardObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;
import java.util.Map;

/**
 * This class is a listener that handles updates for the starter card of a specific player.
 * It sends the starter card of a player.
 *
 * @author sslvo
 */
public class PlayerStarterCardListener extends Listener {

    public PlayerStarterCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the starter card of the player and send it to all clients.
     *
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     */
    @Override
    public void update(GameModel model, String username) {
        Player player = model.getPlayers().get(username);
        ClientQueueObject clientQueueObject = new ShowStarterCardObj(
                username,
                gsonTranslater.toJson(player.getStarterCard(), PlayableCard.class)
        );
        for (Map.Entry<String, VirtualClient> clients: clients.entrySet()) {
            sendUpdate(model, clients.getKey(), clients.getValue(), clientQueueObject);
        }
    }
}
