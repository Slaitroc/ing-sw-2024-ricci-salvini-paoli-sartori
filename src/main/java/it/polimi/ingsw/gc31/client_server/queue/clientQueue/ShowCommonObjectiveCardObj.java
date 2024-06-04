package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowCommonObjectiveCardObj extends ClientQueueObject {
    private final String card1;
    private final String card2;

    public ShowCommonObjectiveCardObj(String card1, String card2) {
        this.card1 = card1;
        this.card2 = card2;
    }

    @Override
    public void execute(UI ui) {
        ui.show_commonObjectiveCard(
                gsonTranslater.fromJson(card1, ObjectiveCard.class),
                gsonTranslater.fromJson(card2, ObjectiveCard.class)
        );
    }
}
