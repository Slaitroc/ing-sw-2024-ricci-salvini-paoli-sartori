package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowSecretObjectiveCardObj extends ClientQueueObject {
    private final String username;
    private final String objectiveCard;

    public ShowSecretObjectiveCardObj(String username, String objectiveCard) {
        this.username = username;
        this.objectiveCard = objectiveCard;
    }

    @Override
    public void execute(UI ui) {
        ui.show_objectiveCard(username, gsonTranslater.fromJson(objectiveCard, ObjectiveCard.class));
    }
}
