package it.polimi.ingsw.gc31.model.strategies;

import it.polimi.ingsw.gc31.model.enumeration.Resources;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ResourceScoreTest {

    /**
     * This method tests if the result of ResourceScore is correct with a certain Resources contained on the board
     */
    @Test
    void firstTest() {
        Objective ob = new ResourceScore(Resources.INK);

    }
}