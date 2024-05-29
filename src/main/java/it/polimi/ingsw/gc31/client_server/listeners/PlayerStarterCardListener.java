package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowStarterCardObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;

public class PlayerStarterCardListener implements Listener<PlayableCard> {
    private VirtualClient client;

    public PlayerStarterCardListener(VirtualClient client) {
        this.client = client;
    }

    @Override
    public void update(PlayableCard data) throws RemoteException {
        client.sendCommand(new ShowStarterCardObj(gsonTranslater.toJson(data, PlayableCard.class)));
    }
}
