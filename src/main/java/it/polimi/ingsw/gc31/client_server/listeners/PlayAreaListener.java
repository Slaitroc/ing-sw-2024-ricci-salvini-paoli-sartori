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
import java.util.LinkedHashMap;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class PlayAreaListener extends Listener {
        public PlayAreaListener(Map<String, VirtualClient> clients) {
                super(clients);
        }

        @Override
        public void update(GameModel model, String username) throws RemoteException {
                Player player = model.getPlayers().get(username);
                if (player.getPlayArea().getPlacedCards().containsKey(new Point(0,0))) {
                        ClientQueueObject clientQueueObject = new ShowPlayAreaObj(
                                username,
                                gsonTranslater.toJson(player.getPlayArea().getPlacedCards(), new TypeToken<LinkedHashMap<Point, PlayableCard>>(){}.getType()),
                                gsonTranslater.toJson(player.getPlayArea().getAchievedResources(), new TypeToken<Map<Resources, Integer>>(){}.getType())
                        );

                        for (VirtualClient client: clients.values()) {
                                client.sendCommand(clientQueueObject);
                        }
                }
        }
}
