package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class AnotherMatchObj extends ClientQueueObject{
    String username;

    public AnotherMatchObj(String username){
        this.username = username;
    }
    @Override
    public void execute(UI ui) {
        ui.show_requestAnotherMatch();
    }
}
