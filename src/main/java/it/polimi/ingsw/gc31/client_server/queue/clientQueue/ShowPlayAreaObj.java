package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class ShowPlayAreaObj extends ClientQueueObject {
    private final String username;
    private final String playArea;
    private final String achievedResources;

    public ShowPlayAreaObj(String username, String playArea, String achievedResources) {
        this.username = username;
        this.playArea = playArea;
        this.achievedResources = achievedResources;
    }

    @Override
    public void execute(UI ui) {
        ui.show_playArea(username, playArea, achievedResources);
    }
}
