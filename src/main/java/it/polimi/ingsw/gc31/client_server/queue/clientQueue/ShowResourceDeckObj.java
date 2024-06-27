package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowResourceDeckObj is a class that extends ClientQueueObject.
 * It is sent to the client to notify the resource deck.
 */
public class ShowResourceDeckObj extends ClientQueueObject {

    /**
     * The card on top of the deck.
     */
    private final String firstCardDeck;

    /**
     * The first gold card flipped on the board.
     */
    private final String card1;

    /**
     * The second gold card flipped on the board.
     */
    private final String card2;

    /**
     * Notify the resource deck.
     *
     * @param firstCardDeck the card on the top of the deck.
     * @param card1 the first gold card flipped on the board.
     * @param card2 the second gold card flipped on the board.
     */
    public ShowResourceDeckObj(String firstCardDeck, String card1, String card2) {
        this.firstCardDeck = firstCardDeck;
        this.card1 = card1;
        this.card2 = card2;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show the resource deck.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_resourceDeck(
                gsonTranslater.fromJson(firstCardDeck, PlayableCard.class),
                gsonTranslater.fromJson(card1, PlayableCard.class),
                gsonTranslater.fromJson(card2, PlayableCard.class));
    }
}
