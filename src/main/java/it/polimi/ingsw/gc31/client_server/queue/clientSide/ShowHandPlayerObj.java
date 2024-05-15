package it.polimi.ingsw.gc31.client_server.queue.clientSide;

import it.polimi.ingsw.gc31.client_server.queue.QueueObject;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowHandPlayerObj implements ClientQueueObject {
    private final String username;
    private final List<String> hand;

    public ShowHandPlayerObj(String username, List<String> hand) {
        this.username = username;
        this.hand = hand;
    }

    @Override
    public void execute(UI ui) {
        ui.show_handPlayer(
                username,
                hand.stream().map(card -> gsonTranslater.fromJson(card, PlayableCard.class)).toList()
                );
    }
}
