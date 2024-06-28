package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowCommonObjectiveCardObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the objective card common to all the players.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowCommonObjectiveCardObj extends ClientQueueObject {

    /**
     * The first common objective card.
     */
    private final String card1;

    /**
     * The second common objective card.
     */
    private final String card2;

    /**
     * Notify the objective card common to all the players.
     *
     * @param card1 the first common objective card.
     * @param card2 the second common objective card.
     */
    public ShowCommonObjectiveCardObj(String card1, String card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to notify the objective card common to all the players.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_commonObjectiveCard(
                gsonTranslater.fromJson(card1, ObjectiveCard.class),
                gsonTranslater.fromJson(card2, ObjectiveCard.class)
        );
    }
}
