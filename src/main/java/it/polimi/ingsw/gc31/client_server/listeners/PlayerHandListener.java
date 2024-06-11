package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowHandPlayerObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * A listener class that handles updates related to a player's hand.
 * It receives data in the form of a pair containing:
 * <ul>
 * <li>The username of the player to whom the hand belongs</li>
 * <li>A list of PlayableCard objects representing the cards in the player's
 * hand.</li>
 * </ul>
 * This listener is designed to update a VirtualClient with the player's hand
 * updated.
 *
 * @author christian salvini
 */
public class PlayerHandListener extends Listener {

    public PlayerHandListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

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
