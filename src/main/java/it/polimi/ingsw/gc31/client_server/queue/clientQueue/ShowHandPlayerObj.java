package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;

import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowHandPlayerObj is a class that extends ClientQueueObject
 * It is sent to the client to notify play area of a player who is playing the game.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowHandPlayerObj extends ClientQueueObject {

    /**
     * Username of the player who is playing the game.
     */
    private final String username;

    /**
     * The list of the card in hand of the player.
     */
    private final List<String> hand;

    /**
     * The index of the current selected card.
     */
    private final int selectedCard;

    /**
     * Notify play area of a player who is playing the game.
     *
     * @param username username of the player who is playing the game.
     * @param hand list of the card in hand if the player.
     * @param selectedCard index of the current selected card.
     */
    public ShowHandPlayerObj(String username, List<String> hand, int selectedCard) {
        this.username = username;
        this.hand = hand;
        this.selectedCard = selectedCard;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to notify the play area
     * of a player who is playing the game.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_handPlayer(
                username,
                hand.stream().map(card -> gsonTranslater.fromJson(card, PlayableCard.class)).toList(),
                selectedCard);
    }
}
