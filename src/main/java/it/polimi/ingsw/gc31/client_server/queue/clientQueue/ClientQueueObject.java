package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.io.Serializable;

public abstract class ClientQueueObject implements Serializable {

    String recipient;

    public abstract void execute(UI ui);

}
