package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowHandPlayerObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class represents a listener that handles updates for a player's hand.
 * It retrieves the updated hand of a player from the game model and sends it to all clients.
 *
 * @author sslvo
 */
public class PlayerHandListener extends Listener {

    public PlayerHandListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the hand of the player and the index of the selected card and sends them to all clients.
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     * @throws RemoteException if there is a remote communication error.
     */
    @Override
    public void update(GameModel model, String username) throws RemoteException {
        Player player = model.getPlayers().get(username);
        ClientQueueObject clientQueueObject = new ShowHandPlayerObj(
                username,
                player.getHand().stream().map(card -> gsonTranslater.toJson(card, PlayableCard.class)).toList(),
                player.getIndexSelectedCard()
        );

        for (VirtualClient client: clients.values()) {
            client.sendCommand(clientQueueObject);
        }
    }
}
