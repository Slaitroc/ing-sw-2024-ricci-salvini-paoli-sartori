package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class HeartBeatObj extends ClientQueueObject {

    public HeartBeatObj() {
        ;
    }

    @Override
    public void execute(UI ui) {
        ui.show_heartBeat();
    }

}
