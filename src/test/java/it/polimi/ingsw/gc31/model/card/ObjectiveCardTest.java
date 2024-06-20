package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.strategies.StairUp;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ObjectiveCardTest {
    ObjectiveCard objectiveCard;
    @BeforeEach
    void setUp() {
        objectiveCard = new ObjectiveCard(
                2,
                new StairUp(CardColor.RED),
                "dirImgFront",
                "dirImgBack"
        );
    }

    @Test
    void getScore() {
        assertEquals(2, objectiveCard.getScore());
    }

    @Test
    void getObjective() {
        assertInstanceOf(StairUp.class, objectiveCard.getObjective());
    }

    @Test
    void changeSide() {
        assertFalse(objectiveCard.getSide());
        objectiveCard.changeSide();
        assertTrue(objectiveCard.getSide());
    }

    @Test
    void getImage() {
        assertEquals("dirImgBack", objectiveCard.getImage());
        objectiveCard.changeSide();
        assertEquals("dirImgFront", objectiveCard.getImage());
    }
}