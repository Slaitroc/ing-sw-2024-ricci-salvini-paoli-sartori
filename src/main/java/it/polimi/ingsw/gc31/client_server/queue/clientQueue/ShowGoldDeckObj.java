package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowGoldDeckObj extends ClientQueueObject {
    private final String firstCardDeck;
    private final String card1;
    private final String card2;

    public ShowGoldDeckObj(String firstCardDeck, String card1, String card2) {
        this.firstCardDeck = firstCardDeck;
        this.card1 = card1;
        this.card2 = card2;
    }

    @Override
    public void execute(UI ui) {
        ui.show_goldDeck(firstCardDeck, card1, card2);
    }
}
