package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowStarterCardObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

public class PlayerStarterCardListener extends Listener {

    public PlayerStarterCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        Player player = model.getPlayers().get(username);
        ClientQueueObject clientQueueObject = new ShowStarterCardObj(
                username,
                gsonTranslater.toJson(player.getStarterCard(), PlayableCard.class)
        );

        for (VirtualClient client : clients.values()) {
            client.sendCommand(clientQueueObject);
        }
    }
}
