package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowResourceDeckObj extends ClientQueueObject {
    private final String firstCardDeck;
    private final String card1;
    private final String card2;

    public ShowResourceDeckObj(String firstCardDeck, String card1, String card2) {
        this.firstCardDeck = firstCardDeck;
        this.card1 = card1;
        this.card2 = card2;
    }

    @Override
    public void execute(UI ui) {
        ui.show_resourceDeck(
                gsonTranslater.fromJson(firstCardDeck, PlayableCard.class),
                gsonTranslater.fromJson(card1, PlayableCard.class),
                gsonTranslater.fromJson(card2, PlayableCard.class));
    }
}
