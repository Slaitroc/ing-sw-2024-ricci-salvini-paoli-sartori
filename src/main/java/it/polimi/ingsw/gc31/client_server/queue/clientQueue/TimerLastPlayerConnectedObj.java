package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

/**
 * TimerLastPlayerConnectedObj is a class that extends ClientQueueObject
 * It is sent to the client to notify that the player is playing is the last remained in the game.
 * A timer has started and the game ends automatically.
 */
public class TimerLastPlayerConnectedObj extends ClientQueueObject{

    /**
     * Seconds when the time runs out.
     */
    final Integer secondsLeft;

    /**
     * Notify that the player is playing is the last remained in the game.
     * A timer has started and the game ends automatically.
     * @param secondsLeft seconds when the time runs out.
     */
    public TimerLastPlayerConnectedObj(Integer secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show seconds when the time runs out.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_timerLastPlayerConnected(secondsLeft);
    }
}
