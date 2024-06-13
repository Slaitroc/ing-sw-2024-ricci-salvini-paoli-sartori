package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowSecretObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class represents a listener that handles updates related to a specific player's objective card.
 * It sends the objective card the player had choose.
 *
 * @author sslvo
 */
public class PlayerObjectiveCardListener extends Listener {
    public PlayerObjectiveCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the objective card the player had choose and sends it to all clients.
     *
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     * @throws RemoteException if there is a remote communication error.
     */
    @Override
    public void update(GameModel model, String username) throws RemoteException {
        Player player = model.getPlayers().get(username);
        if (player.getObjectiveCard() != null) {
            for (VirtualClient client : clients.values()) {
                client.sendCommand(new ShowSecretObjectiveCardObj(username, gsonTranslater.toJson(player.getObjectiveCard(), ObjectiveCard.class)));
            }
        }

    }
}
