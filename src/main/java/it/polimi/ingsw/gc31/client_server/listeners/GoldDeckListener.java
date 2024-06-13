package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowGoldDeckObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class is a listener that handles updates related to the Gold Deck in the game model.
 */
public class GoldDeckListener extends Listener {

    public GoldDeckListener(Map<String, VirtualClient> clients) {
        super(clients);
    }
    /**
     * Extract from the game model the card on the top, the fist card and the second card of the gold deck and sends them to a specific client.
     *
     * @param model The game model from which to extract the data.
     * @param username The username of the player whose play area is being updated.
     * @throws RemoteException if there is a remote communication error.
     */
    @Override
    public void update(GameModel model, String username) throws RemoteException {
        clients.get(username).sendCommand(
                new ShowGoldDeckObj(
                        gsonTranslater.toJson(model.getBoard().getDeckGold().peekCard(), PlayableCard.class),
                        gsonTranslater.toJson(model.getBoard().getDeckGold().peekCard1(), PlayableCard.class),
                        gsonTranslater.toJson(model.getBoard().getDeckGold().peekCard2(), PlayableCard.class)
                )
        );
    }
}
