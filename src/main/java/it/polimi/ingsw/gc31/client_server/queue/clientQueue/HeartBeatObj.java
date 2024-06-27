package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * JoinedToGameObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the heartbeat has been received correctly and thus the connection is stable.
 */
public class HeartBeatObj extends ClientQueueObject {

    /**
     * Notify that the heartbeat has been received correctly and thus the connection is stable.
     */
    public HeartBeatObj() {
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the heartbeat has been
     * received correctly.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_heartBeat();
    }

}
