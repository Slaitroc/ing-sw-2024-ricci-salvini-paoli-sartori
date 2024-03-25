package it.polimi.ingsw.gc31.model.strategies;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.PlayArea;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ResourceScoreTest {
    static private PlayArea playArea;
    static private Board board;
    static private Point point;
    @BeforeAll
    static void initializeModel(){
        playArea = new PlayArea();
        board = new Board();
        point = new Point(0, 0);

        playArea.place(board.getDeckStarer().draw(), point);


    }

    /**
     * This method tests if the result of ResourceScore is correct with a certain Resources contained on the board
     */
    @Test
    void firstTest() {
        Objective ob = new ResourceScore(Resources.INK);

    }
}