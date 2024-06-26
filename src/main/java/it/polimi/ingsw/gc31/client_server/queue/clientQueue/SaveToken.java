package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.view.UI;

public class SaveToken extends ClientQueueObject implements Serializable {
    int token;
    boolean temporary;

    public SaveToken(int token, boolean temporary) {
        this.token = token;
        this.temporary = temporary;
    }

    @Override
    public void execute(UI ui) {
        ui.receiveToken(token, temporary);
    }
}
