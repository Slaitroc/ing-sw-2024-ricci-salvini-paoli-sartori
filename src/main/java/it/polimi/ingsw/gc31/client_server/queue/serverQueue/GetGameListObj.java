package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

/**
 * This class represents the action of asking the game list to the server.
 */
public class GetGameListObj extends ServerQueueObject {
    private final int token;

    public GetGameListObj(int token) {
        this.token = token;
    }

    /**
     * This method is executed if the object should be executed by the
     * {@link GameController} but should be
     * executed by the {@link Controller}.
     *
     * @param gameController is the reference to the {@link GameController} that
     *                       should execute the object.
     */
    @Override
    public void execute(GameController gameController) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    /**
     * This method is executed by the {@link Controller} when it is polled from the
     * queue.
     * Invokes the {@link Controller#getGameList(String)}.
     *
     * @param controller is the reference to the {@link Controller} associated with
     *                   the client.
     */
    @Override
    public void execute(Controller controller) {
        try {
            controller.getGameList(token);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NoGamesException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is executed if the object should be executed by the
     * {@link RmiServer} but should be
     * executed by the {@link Controller}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute
     *               the object.
     */
    @Override
    public void execute(RmiServer server) {
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

}
