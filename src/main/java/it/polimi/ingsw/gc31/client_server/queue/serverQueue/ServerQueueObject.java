package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.io.Serializable;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

public interface ServerQueueObject extends Serializable {

    public void execute(GameController gameController);

    public void execute(Controller controller);

}
