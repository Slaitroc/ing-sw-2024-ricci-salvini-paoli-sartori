package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

import java.io.Serializable;

public interface ClientQueueObject extends Serializable {
    void execute(UI ui);
}
