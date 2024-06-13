package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;

import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowHandPlayerObj extends ClientQueueObject {
    private final String username;
    private final List<String> hand;
    private final int selectedCard;

    public ShowHandPlayerObj(String username, List<String> hand, int selectedCard) {
        this.username = username;
        this.hand = hand;
        this.selectedCard = selectedCard;
    }

    @Override
    public void execute(UI ui) {
        ui.show_handPlayer(
                username,
                hand.stream().map(card -> gsonTranslater.fromJson(card, PlayableCard.class)).toList(),
                selectedCard);
    }
}
