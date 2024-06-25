package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.gameModel.EndGameModelState;

public class EndGameOnePlayerLeftObj extends ServerQueueObject{
    private final String lastPlayerConnected;

    public EndGameOnePlayerLeftObj(String lastPlayerConnected) {
        this.lastPlayerConnected = lastPlayerConnected;
    }

    @Override
    public void execute(GameController gameController) {
        gameController.getModel().setGameState(new EndGameModelState(gameController.getModel()));
        try {
            gameController.getModel().endGame(lastPlayerConnected);
        } catch (IllegalStateOperationException ignored) {
        }
    }

    @Override
    public void execute(Controller controller) {

    }

    @Override
    public void execute(RmiServer server) {

    }
}
