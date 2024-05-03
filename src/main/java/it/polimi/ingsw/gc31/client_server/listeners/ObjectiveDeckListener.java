package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.rmi.RemoteException;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

@SuppressWarnings("rawtypes")
public class ObjectiveDeckListener implements Listener<Deck> {
    private VirtualClient client;

    public ObjectiveDeckListener(VirtualClient client) {
        this.client = client;
    }

    @Override
    public void update(Deck data) throws RemoteException {
        client.show_objectiveDeck(
                gsonTranslater.toJson(data.peekCard(), ObjectiveCard.class),
                gsonTranslater.toJson(data.peekCard1(), ObjectiveCard.class),
                gsonTranslater.toJson(data.peekCard2(), ObjectiveCard.class));
    }
}
