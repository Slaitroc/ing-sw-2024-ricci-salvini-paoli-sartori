package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.deck.Deck;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class GoldDeckListener implements Listener<Deck> {
    private VirtualClient client;
    public GoldDeckListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Deck data) throws RemoteException {
        client.show_goldDeck(
                gsonTranslater.toJson(data.peekCard(), PlayableCard.class),
                gsonTranslater.toJson(data.peekCard1(), PlayableCard.class),
                gsonTranslater.toJson(data.peekCard2(), PlayableCard.class)
        );
    }
}