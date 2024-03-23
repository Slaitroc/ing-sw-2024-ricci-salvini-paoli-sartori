package it.polimi.ingsw.gc31.model;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.Server;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.exceptions.GameModelAlreadyCreatedException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberNotReachedException;
import it.polimi.ingsw.gc31.view.GameView;

public class ServerTest {
    private GameModel gameModel;
    private Controller controller;
    private GameView view;
    private static List<String> usernamesTestList = new ArrayList<String>();

    @BeforeAll
    static void addPlayersToTestList() {
        // creo gli username per i futuri player
        usernamesTestList.add("Alessandro");
        usernamesTestList.add("Christian");
        usernamesTestList.add("Matteo");
        usernamesTestList.add("Lorenzo");
        usernamesTestList.add("Lorenzo");
    }

    // Verifica che:
    // Vengano aggiunti correttamente i nicknames presenti nella usernamesTestList
    // al Controller.
    // Non vengano aggiunti duplicati
    @Test
    void addPlayerToControllerTest() {
        for (String string : usernamesTestList) {
            Server.addPlayerToController(string);
        }
        assert (Server.getController().getGlobalUsernameSet().containsAll(usernamesTestList));
    }

    // Verifica che:
    // Il GameController creato dal Server attraverso il Controller venga creato
    // correttamente con il primo player della usernamesTestList.
    // Vengano aggiunti correttamente i player che non ne hanno comportato la
    // creazione
    // Non sia possibile aggiungere duplicati
    @Test
    void gameControllerCreationTest() {
        GameController gameController = Server.createGameController(usernamesTestList.get(0), 4);
        for (String string : usernamesTestList) {
            Server.addPlayerToGameController(gameController, string);
        }
        assert (gameController.getPlayerList().containsAll(usernamesTestList));
    }

    @Test
    void gameModelCreationTest() {
        GameController gameController = Server.createGameController(usernamesTestList, 4);
        try {
            gameController.createGameModel();
        } catch (PlayerNumberNotReachedException e) {
            System.out.println("Player Number Not Reached!");
            e.printStackTrace();
        } catch (GameModelAlreadyCreatedException e) {
            System.out.println("Game Model Already Exists!");
            e.printStackTrace();
        }
        // assert(gameController.getGameModel())
        /* Qui vanno aggiunti test sul Game Model creato */
        /*
         * Tutto questo sta implicitamente testando anche le deepcopy che ora si
         * chiamano clone
         */
    }

}
