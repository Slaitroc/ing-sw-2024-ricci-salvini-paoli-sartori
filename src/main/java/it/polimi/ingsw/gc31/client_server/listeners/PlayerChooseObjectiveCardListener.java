package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowChooseSecretObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.Player;

import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class represents a listener that handles updates when a player has to choose his secret objective card.
 * It sends the two objective cards the player can choose.
 *
 * @author sslvo
 */
public class PlayerChooseObjectiveCardListener extends Listener {

    /**
     * This class represents a listener that handles updates when a player has to choose his secret objective card.
     * It sends the two objective cards the player can choose.
     */
    public PlayerChooseObjectiveCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the two objective cards and sends them to all clients.
     *
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     */
    @Override
    public void update(GameModel model, String username) {
        Player player = model.getPlayers().get(username);
        ClientQueueObject clientQueueObject = new ShowChooseSecretObjectiveCardObj(
                username,
                gsonTranslater.toJson(player.getChooseSecretObjective().get(0), ObjectiveCard.class),
                gsonTranslater.toJson(player.getChooseSecretObjective().get(1), ObjectiveCard.class)
        );
        for (Map.Entry<String, VirtualClient> clients: clients.entrySet()) {
            sendUpdate(model, clients.getKey(), clients.getValue(), clientQueueObject);
        }
    }
}
