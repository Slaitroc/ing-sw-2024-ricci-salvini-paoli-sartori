package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowChooseSecretObjectiveCardObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the two secret objective cards the player can choose.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowChooseSecretObjectiveCardObj extends ClientQueueObject {

    /**
     * Username of the player who the choose secret objective cards belongs.
     */
    private final String username;

    /**
     * The first card the player can choose.
     */
    private final String objectiveCard1;

    /**
     * The second card the player can choose.
     */
    private final String objectiveCard2;

    /**
     * Notify the two secret objective cards the player can choose.
     *
     * @param username username of the player who the choose secret objective cards belongs.
     * @param objectiveCard1 the first card the player can choose.
     * @param objectiveCard2 the second card the player can choose.
     */
    public ShowChooseSecretObjectiveCardObj(String username, String objectiveCard1, String objectiveCard2) {
        this.username = username;
        this.objectiveCard1 = objectiveCard1;
        this.objectiveCard2 = objectiveCard2;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the two secret objective cards the player can choose.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_chooseObjectiveCard(
                username,
                gsonTranslater.fromJson(objectiveCard1, ObjectiveCard.class),
                gsonTranslater.fromJson(objectiveCard2, ObjectiveCard.class));
    }
}
