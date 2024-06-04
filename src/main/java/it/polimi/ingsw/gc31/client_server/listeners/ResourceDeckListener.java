package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowResourceDeckObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ResourceDeckListener implements Listener {
    private final List<VirtualClient> clients;

    public ResourceDeckListener(List<VirtualClient> clients) {
        this.clients = clients;
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        for (VirtualClient client : clients) {
            client.sendCommand(
                    new ShowResourceDeckObj(
                            gsonTranslater.toJson(model.getBoard().getDeckResource().peekCard(), PlayableCard.class),
                            gsonTranslater.toJson(model.getBoard().getDeckResource().peekCard1(), PlayableCard.class),
                            gsonTranslater.toJson(model.getBoard().getDeckResource().peekCard2(), PlayableCard.class)
                    )
            );
        }
    }
}
