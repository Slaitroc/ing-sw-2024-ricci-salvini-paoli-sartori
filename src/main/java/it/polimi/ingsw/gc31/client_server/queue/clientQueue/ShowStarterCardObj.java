package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowStarterCardObj implements ClientQueueObject{
    private final String starterCard;

    public ShowStarterCardObj(String starterCard) {
        this.starterCard = starterCard;
    }

    @Override
    public void execute(UI ui) {

    }
}
