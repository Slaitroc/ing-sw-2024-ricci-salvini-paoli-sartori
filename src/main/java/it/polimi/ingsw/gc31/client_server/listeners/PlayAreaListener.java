package it.polimi.ingsw.gc31.client_server.listeners;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayAreaObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * The PlayAreaListener class is a subclass of Listener that handles updates for the play area of a player.
 * It retrieves the updated play area data from the game model and sends it to all clients.
 *
 * @author sslvo
 */
public class PlayAreaListener extends Listener {
        public PlayAreaListener(Map<String, VirtualClient> clients) {
                super(clients);
        }

        /**
         * Extracts from the game model the cards placed by the player
         * and the available resources in the play area.
         *
         * @param model The game model from which to extract the data,
         * @param username The username of the player whose play area is being updated.
         */
        @Override
        public void update(GameModel model, String username){
                Player player = model.getPlayers().get(username);
                if (player.getPlayArea().getPlacedCards().containsKey(new Point(0,0))) {
                        ClientQueueObject clientQueueObject = new ShowPlayAreaObj(
                                username,
                                gsonTranslater.toJson(player.getPlayArea().getPlacedCards(), new TypeToken<LinkedHashMap<Point, PlayableCard>>(){}.getType()),
                                gsonTranslater.toJson(player.getPlayArea().getAchievedResources())
                        );


                        for (Map.Entry<String, VirtualClient> clients: clients.entrySet()) {
                                sendUpdate(model, clients.getKey(), clients.getValue(), clientQueueObject);
                        }
                }
        }
}
