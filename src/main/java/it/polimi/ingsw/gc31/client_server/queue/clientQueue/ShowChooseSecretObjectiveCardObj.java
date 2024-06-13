package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowChooseSecretObjectiveCardObj extends ClientQueueObject {
    private final String username;
    private final String objectiveCard1;
    private final String objectiveCard2;

    public ShowChooseSecretObjectiveCardObj(String username, String objectiveCard1, String objectiveCard2) {
        this.username = username;
        this.objectiveCard1 = objectiveCard1;
        this.objectiveCard2 = objectiveCard2;
    }

    @Override
    public void execute(UI ui) {
        ui.show_chooseObjectiveCard(
                username,
                gsonTranslater.fromJson(objectiveCard1, ObjectiveCard.class),
                gsonTranslater.fromJson(objectiveCard2, ObjectiveCard.class));
    }
}
