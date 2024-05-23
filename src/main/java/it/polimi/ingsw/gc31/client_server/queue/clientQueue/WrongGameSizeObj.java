package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class WrongGameSizeObj extends ClientQueueObject {

    public WrongGameSizeObj(){

    }
    @Override
    public void execute(UI ui) {
        ui.show_wrongGameSize();
    }

}
