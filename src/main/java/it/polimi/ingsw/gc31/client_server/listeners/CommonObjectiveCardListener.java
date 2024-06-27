package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowCommonObjectiveCardObj;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * This class represents a listener that handles updates for the common objective cards of the game model.
 *
 * @author sslvo
 */
public class CommonObjectiveCardListener extends Listener{

    /**
     * This class represents a listener that handles updates for the common objective cards of the game model.
     */
    public CommonObjectiveCardListener(Map<String, VirtualClient> clients) {
        super(clients);
    }

    /**
     * Extract from the game model the two common objective cards and send them to a specific client.
     *
     * @param model The game model from which to extract the data,
     * @param username The username of the player whose play area is being updated.
     */
    @Override
    public void update(GameModel model, String username){
        ClientQueueObject clientQueueObject = new ShowCommonObjectiveCardObj(
                gsonTranslater.toJson(model.getCommonObjectives().get(0), ObjectiveCard.class),
                gsonTranslater.toJson(model.getCommonObjectives().get(1), ObjectiveCard.class)
        );

        sendUpdate(model, username, clients.get(username), clientQueueObject);
    }
}
