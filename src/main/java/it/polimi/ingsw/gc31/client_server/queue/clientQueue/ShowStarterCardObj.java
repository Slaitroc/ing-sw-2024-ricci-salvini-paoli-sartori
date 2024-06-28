package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowStarterCardObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the starter card the player have to place to start the game.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowStarterCardObj extends ClientQueueObject {

    /**
     * Username of the player who the starter card belongs.
     */
    private final String username;

    /**
     * The starter card the player had to choose to start the game.
     */
    private final String starterCard;

    /**
     *
     * @param username username of the player who the starter card belongs.
     * @param starterCard the starter card the player had to choose to start the game.
     */
    public ShowStarterCardObj(String username, String starterCard) {
        this.username = username;
        this.starterCard = starterCard;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the starter card
     * the player have to place to start the game.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_starterCard(username, gsonTranslater.fromJson(starterCard, PlayableCard.class));
    }
}
