package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowSecretObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class PlayerObjectiveCardListener extends Listener {
    public PlayerObjectiveCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        Player player = model.getPlayers().get(username);
        if (player.getObjectiveCard() != null) {
            for (VirtualClient client : clients.values()) {
                client.sendCommand(new ShowSecretObjectiveCardObj(username, gsonTranslater.toJson(player.getObjectiveCard(), ObjectiveCard.class)));
            }
        }

    }
}
