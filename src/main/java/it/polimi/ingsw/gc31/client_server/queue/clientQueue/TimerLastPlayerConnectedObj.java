package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import it.polimi.ingsw.gc31.view.UI;

public class TimerLastPlayerConnectedObj extends ClientQueueObject{
    final Integer secondsLeft;

    public TimerLastPlayerConnectedObj(Integer secondsLeft) {
        this.secondsLeft = secondsLeft;
    }

    @Override
    public void execute(UI ui) {
        ui.show_timerLastPlayerConnected(secondsLeft);
    }
}
