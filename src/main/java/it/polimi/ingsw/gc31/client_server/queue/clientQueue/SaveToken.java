package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class SaveToken extends ClientQueueObject{
    int token;
    public SaveToken(int token){
        this.token = token;
    }
    @Override
    public void execute(UI ui) {
        ui.receiveToken(token);
    }
}
