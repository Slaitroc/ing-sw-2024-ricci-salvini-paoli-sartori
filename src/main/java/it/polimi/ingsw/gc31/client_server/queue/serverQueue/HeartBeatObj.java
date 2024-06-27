package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;

/**
 * This class represents a heartBeat. Sent from a client to the server in order
 * to show that the specific client is not crashed.
 */
public class HeartBeatObj extends ServerQueueObject{
    /**
     * Is the username of the client which generated the heartBeat.
     */
    private String username;

    /**
     * This is the constructor of the class.
     *
     * @param username is the username of the client which generated the heartBeat.
     */
    public HeartBeatObj(String username){
        this.username = username;
    }

    /**
     * This method is executed if the object should be executed by the {@link GameController} but should be
     *
     * @param gameController is the reference to the {@link GameController} that should execute the object.
     */
    @Override
    public void execute(GameController gameController) {

    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {

    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {

    }
}
