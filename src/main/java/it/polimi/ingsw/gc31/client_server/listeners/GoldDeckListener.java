package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowGoldDeckObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class GoldDeckListener extends Listener {

    public GoldDeckListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

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
