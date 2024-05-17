package it.polimi.ingsw.gc31.client_server.queue.clientSide;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.view.UI;

import java.rmi.RemoteException;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

public class ShowChooseSecretObjectiveCardObj implements ClientQueueObject{
    private final String objectiveCard1;
    private final String objectiveCard2;

    public ShowChooseSecretObjectiveCardObj(String objectiveCard1, String objectiveCard2) {
        this.objectiveCard1 = objectiveCard1;
        this.objectiveCard2 = objectiveCard2;
    }

    @Override
    public void execute(UI ui) {
        try {
            ui.show_chooseObjectiveCard(
                    gsonTranslater.fromJson(objectiveCard1, ObjectiveCard.class),
                    gsonTranslater.fromJson(objectiveCard2, ObjectiveCard.class)
            );
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
