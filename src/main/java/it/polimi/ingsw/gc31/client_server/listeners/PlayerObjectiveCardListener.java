package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;

public class PlayerObjectiveCardListener implements Listener<ObjectiveCard> {
    private VirtualClient client;
    public PlayerObjectiveCardListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(ObjectiveCard data) throws RemoteException {
        client.show_objectiveCard(gsonTranslater.toJson(data, ObjectiveCard.class));
    }
}
