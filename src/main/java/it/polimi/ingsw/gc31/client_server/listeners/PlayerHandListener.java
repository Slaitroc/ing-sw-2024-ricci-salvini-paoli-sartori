package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import javafx.util.Pair;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;
import java.util.List;

public class PlayerHandListener implements Listener<Pair<String, List<PlayableCard>>> {
    private VirtualClient client;
    public PlayerHandListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Pair<String, List<PlayableCard>> data) throws RemoteException {
        client.show_handPlayer(
                data.getKey(),
                data.getValue()
                        .stream()
                        .map(card -> gsonTranslater.toJson(card, PlayableCard.class))
                        .toList()
        );
    }
}
