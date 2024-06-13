package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowStarterCardObj extends ClientQueueObject {
    private final String username;
    private final String starterCard;

    public ShowStarterCardObj(String username, String starterCard) {
        this.username = username;
        this.starterCard = starterCard;
    }

    @Override
    public void execute(UI ui) {
        ui.show_starterCard(username, gsonTranslater.fromJson(starterCard, PlayableCard.class));
    }
}
