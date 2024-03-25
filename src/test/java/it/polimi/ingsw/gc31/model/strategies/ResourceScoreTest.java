package it.polimi.ingsw.gc31.model.strategies;

import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.PlayArea;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
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

        playArea.place(board.getDeckStarter().draw(), point);

    }

    /**
     * This method tests if the result of ResourceScore is correct with a certain Resources contained on the board
     */
    @Test
    void firstTest() {
        Objective ob = new ResourceScore(Resources.INK);


    }

    private PlayableCard createGoldCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                        Resources b1, Resources b2, Resources b3, Resources b4, Resources b5,
                                        Resources r1, int n1, Resources r2, int n2) {
        it.polimi.ingsw.gc31.model.enumeration.Color color = it.polimi.ingsw.gc31.model.enumeration.Color.RED;

        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);
        resourcesFront.add(f4);

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(r1, n1);
        requirements.put(r2, n2);

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob
        );

        // resourceBack può avere dai 4 ai 7 elementi
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack
        );

        return new GoldCard(color, front, back);
    }

    //f1 sta per front alto-dx, f2 basso-dx, f3 basso-sx, f4 alto-sx
    private PlayableCard createResourceCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                            Resources b1, Resources b2, Resources b3, Resources b4, Resources b5) {
        it.polimi.ingsw.gc31.model.enumeration.Color color = Color.RED;

        int score = 0;

        // resources deve avere 4 elementi
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);
        resourcesFront.add(f4);

        Map<Resources, Integer> requirements = Collections.emptyMap() ;

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob
        );

        // resourceBack può avere dai 4 ai 7 elementi
        List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack
        );

        return new ResourceCard(color, front, back);
    }
}
