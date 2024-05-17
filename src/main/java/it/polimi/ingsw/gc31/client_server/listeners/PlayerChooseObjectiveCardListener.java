package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientSide.ShowChooseSecretObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import javafx.util.Pair;

import java.rmi.RemoteException;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;
import java.util.List;

public class PlayerChooseObjectiveCardListener implements Listener<Pair<ObjectiveCard, ObjectiveCard>> {
    private VirtualClient client;
    public PlayerChooseObjectiveCardListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Pair<ObjectiveCard, ObjectiveCard> data) throws RemoteException {
        client.sendCommand(
                new ShowChooseSecretObjectiveCardObj(
                        gsonTranslater.toJson(data.getKey(), ObjectiveCard.class),
                        gsonTranslater.toJson(data.getValue(), ObjectiveCard.class)
                )
        );
    }
}
