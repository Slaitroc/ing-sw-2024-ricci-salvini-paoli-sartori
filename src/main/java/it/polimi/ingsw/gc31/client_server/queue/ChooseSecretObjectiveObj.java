package it.polimi.ingsw.gc31.client_server.queue;

import it.polimi.ingsw.gc31.model.GameModel;

public class ChooseSecretObjectiveObj implements QueueObject {

    private final GameModel model;
    int choice;
    String username;

    public ChooseSecretObjectiveObj(String username, GameModel model, int index) {
        this.model = model;
        this.username = username;
        this.choice = index;
    }

    @Override
    public void execute() {
        model.setPlayerObjective(username, choice);
    }

}