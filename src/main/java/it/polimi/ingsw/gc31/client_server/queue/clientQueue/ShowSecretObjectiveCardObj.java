package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowSecretObjectiveCardObj implements ClientQueueObject {
    private final String objectiveCard;

    public ShowSecretObjectiveCardObj(String objectiveCard) {
        this.objectiveCard = objectiveCard;
    }

    @Override
    public void execute(UI ui) {
        ui.show_objectiveCard(gsonTranslater.fromJson(objectiveCard, ObjectiveCard.class));
    }
}
