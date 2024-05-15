package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientSide.ShowHandPlayerObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import javafx.util.Pair;

import java.rmi.RemoteException;
import java.util.List;

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
public class PlayerHandListener implements Listener<Pair<String, List<PlayableCard>>> {
    private VirtualClient client;

    /**
     * Constructs a PlayerHandListener with the specified VirtualClient.
     *
     * @param client The VirtualClient to update with players hand information.
     */
    public PlayerHandListener(VirtualClient client) {
        this.client = client;
    }

    /**
     * Receives an update containing the player's hand data and triggers the display
     * on the associated VirtualClient.
     *
     * @param data A pair containing the username and the player's hand.
     * @throws RemoteException It there is a communication error.
     */
    @Override
    public void update(Pair<String, List<PlayableCard>> data) throws RemoteException {
        client.sendCommand(new ShowHandPlayerObj(
                data.getKey(),
                data.getValue().stream().map(card -> gsonTranslater.toJson(card, PlayableCard.class))
                        .toList()
        ));
    }
}
