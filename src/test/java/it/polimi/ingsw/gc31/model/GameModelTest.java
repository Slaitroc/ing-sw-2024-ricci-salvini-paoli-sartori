package it.polimi.ingsw.gc31.model;

import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.gc31.model.player.NotPlaced;

public class GameModelTest {
    private static List<String> usernamesTestList = new ArrayList<String>();

    GameModel gameModel = new GameModel(usernamesTestList);

    @BeforeAll
    static void addPlayersToTestList() {
        // creo gli username per i futuri player
        usernamesTestList.add("Alessandro");
        usernamesTestList.add("Christian");
        usernamesTestList.add("Matteo");
        usernamesTestList.add("Lorenzo");
    }

    @Test
    void simulazione() {

        // player 0
        gameModel.selectCard(0, 0).changeStarterCardSide(0).playStarterCard(0).changeCardSide(0);
        gameModel.selectCard(0, 1);
        gameModel.changeState(0, new NotPlaced());
        gameModel.playCard(0, new Point(1, 1));
        System.out.println("ciao");

    }

}
