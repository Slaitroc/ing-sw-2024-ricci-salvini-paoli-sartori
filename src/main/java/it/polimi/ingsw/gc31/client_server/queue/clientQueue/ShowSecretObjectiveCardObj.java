package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowSecretObjectiveCardObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the secrete objective card that the player had choose previously.
 */
public class ShowSecretObjectiveCardObj extends ClientQueueObject {

    /**
     * Username of the player.
     */
    private final String username;

    /**
     * Secrete objective card the player had choose.
     */
    private final String objectiveCard;

    /**
     * Notify the secrete objective card that the player had choose previously.
     * @param username username of the player.
     * @param objectiveCard secrete objective card the player had choose.
     */
    public ShowSecretObjectiveCardObj(String username, String objectiveCard) {
        this.username = username;
        this.objectiveCard = objectiveCard;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI notify the secrete objective card that the
     * player had choose previously..
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_objectiveCard(username, gsonTranslater.fromJson(objectiveCard, ObjectiveCard.class));
    }
}
