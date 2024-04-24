package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.rmi.RemoteException;

public class ControllerTest {
    GameController gameController;

    @Test
    public void setUp() throws IllegalStateOperationException, RemoteException {
        gameController = new GameController("Krotox", null, 3, 0);
        gameController.joinGame("Slaitroc", null);
        gameController.joinGame("SSalvo", null);
        //gameController.joinGame("AleSarto", null);

    }
}
