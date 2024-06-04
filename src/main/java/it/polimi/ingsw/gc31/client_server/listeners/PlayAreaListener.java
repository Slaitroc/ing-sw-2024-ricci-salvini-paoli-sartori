package it.polimi.ingsw.gc31.client_server.listeners;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayAreaObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class PlayAreaListener implements Listener {
        private final List<VirtualClient> client;
        public PlayAreaListener(List<VirtualClient> client) {
                this.client = client;
        }

        @Override
        public void update(GameModel model, String username) throws RemoteException {
                Player player = model.getPlayers().get(username);
                ClientQueueObject clientQueueObject = new ShowPlayAreaObj(
                        username,
                        gsonTranslater.toJson(player.getPlayArea().getPlacedCards(), new TypeToken<Map<Point, PlayableCard>>(){}.getType()),
                        gsonTranslater.toJson(player.getPlayArea().getAchievedResources(), new TypeToken<Map<Resources, Integer>>(){}.getType())
                );

                for (VirtualClient client : client) {
                        client.sendCommand(clientQueueObject);
                }
        }
}
