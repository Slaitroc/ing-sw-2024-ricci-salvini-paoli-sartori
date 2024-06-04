package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowChooseSecretObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class PlayerChooseObjectiveCardListener implements Listener {
    private final List<VirtualClient> clients;

    public PlayerChooseObjectiveCardListener(List<VirtualClient> clients) {
        this.clients = clients;
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        Player player = model.getPlayers().get(username);
        ClientQueueObject clientQueueObject = new ShowChooseSecretObjectiveCardObj(
                username,
                gsonTranslater.toJson(player.getChooseSecretObjective().get(0), ObjectiveCard.class),
                gsonTranslater.toJson(player.getChooseSecretObjective().get(1), ObjectiveCard.class)
        );
        for (VirtualClient client : clients) {
            client.sendCommand(clientQueueObject);
        }
    }
}
