package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowCommonObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class CommonObjectiveCardListener implements Listener{
    private final List<VirtualClient> clients;

    public CommonObjectiveCardListener(List<VirtualClient> clients) {
        this.clients = clients;
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        for (VirtualClient client : clients) {
            client.sendCommand(
                    new ShowCommonObjectiveCardObj(
                            gsonTranslater.toJson(model.getCommonObjectives().get(0), ObjectiveCard.class),
                            gsonTranslater.toJson(model.getCommonObjectives().get(1), ObjectiveCard.class)
                    )
            );
        }
    }
}
