package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.gameModel.EndGameModelState;
import it.polimi.ingsw.gc31.model.gameModel.GameModelState;

/**
 * This class represents the action of ending a game with only one player left.
 * This class is used only server-side.
 */
public class EndGameOnePlayerLeftObj extends ServerQueueObject{
    /**
     * Is the username of the last player remained in the game.
     */
    private final String lastPlayerConnected;

    /**
     * This is the constructor of the class.
     *
     * @param lastPlayerConnected is the username of the last remaining player.
     */
    public EndGameOnePlayerLeftObj(String lastPlayerConnected) {
        this.lastPlayerConnected = lastPlayerConnected;
    }

    /**
     * This method is executed by the {@link GameController} when it is polled from the queue.
     * Invokes the {@link GameController#getModel()} method
     * and on the model invokes the {@link it.polimi.ingsw.gc31.model.gameModel.GameModel#setGameState(GameModelState)} method.
     *
     * @param gameController is the reference to the {@link GameController} associated with the client.
     */
    @Override
    public void execute(GameController gameController) {
        gameController.getModel().setGameState(new EndGameModelState(gameController.getModel()));
        try {
            gameController.getModel().endGame(lastPlayerConnected);
        } catch (IllegalStateOperationException ignored) {
        }
    }

    /**
     * This method is executed if the object should be executed by the {@link Controller} but should be
     * executed by the {@link GameController}.
     *
     * @param controller is the reference to the {@link Controller} that should execute the object.
     */
    @Override
    public void execute(Controller controller) {

    }

    /**
     * This method is executed if the object should be executed by the {@link RmiServer} but should be
     * executed by the {@link GameController}.
     *
     * @param server is the reference to the {@link RmiServer} that should execute the object.
     */
    @Override
    public void execute(RmiServer server) {

    }
}
