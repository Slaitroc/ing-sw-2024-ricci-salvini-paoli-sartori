package it.polimi.ingsw.gc31.model.deck;

import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void draw() {
        // the first draw of goldDeck shouldn't be null
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        assertDoesNotThrow(goldDeck::draw);

        // the 20th draw of goldDeck shouldn't be null
        for (int i=0; i<18; i++) {
            assertDoesNotThrow(goldDeck::draw);
        }
        assertDoesNotThrow(goldDeck::draw);

        // the 41th card of goldDeck should throw an EmptyDeckException
        for (int i=0; i<20; i++) {
            assertDoesNotThrow(goldDeck::draw);
        }
        assertThrowsExactly(EmptyDeckException.class, goldDeck::draw);

        Deck<PlayableCard> resourceDeck = new Deck<>(CardType.RESOURCE);
        // the first draw of goldDeck shouldn't be null
        assertDoesNotThrow(resourceDeck::draw);

        // the 20th draw of goldDeck shouldn't be null
        for (int i=0; i<18; i++) {
            assertDoesNotThrow(resourceDeck::draw);
        }
        assertDoesNotThrow(resourceDeck::draw);

        // the 41th card of goldDeck should be null
        for (int i=0; i<20; i++) {
            assertDoesNotThrow(resourceDeck::draw);
        }
        assertThrowsExactly(EmptyDeckException.class, resourceDeck::draw);

        // the starter deck has 6 card
        Deck<PlayableCard> starterDeck = new Deck<>(CardType.STARTER);
        // the first draw of starterDeck shouldn't be null
        assertDoesNotThrow(starterDeck::draw);

        // the 3rd draw of starterDeck shouldn't be null
        assertDoesNotThrow(starterDeck::draw);
        assertDoesNotThrow(starterDeck::draw);

        // the 7th card of goldDeck should be null
        /*for (int i=0; i<4; i++) {
            assertDoesNotThrow(starterDeck::draw);
        }
        assertThrowsExactly(EmptyDeckException.class, starterDeck::draw);*/

        // the objectiveDeck has 16 cards
        Deck<ObjectiveCard> objectiveDeck = new Deck<>(CardType.OBJECTIVE);
        // the first draw of ObjectiveDeck shouldn't be null
        assertDoesNotThrow(objectiveDeck::draw);

        // the 8th draw of objectiveDeck shouldn't be null
        for (int i=0; i<6; i++) {
            assertDoesNotThrow(objectiveDeck::draw);
        }
        assertDoesNotThrow(objectiveDeck::draw);

        // the 17th card of objectiveCard should be null
        for (int i=0; i<8; i++) {
            assertDoesNotThrow(objectiveDeck::draw);
        }
        assertThrowsExactly(EmptyDeckException.class, objectiveDeck::draw);
    }

    @Test
    void flipCard1() {
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        // the Card 1 must be on the true side by default
        assertTrue(goldDeck.peekCard1().getSide());

        // after fliCard1, Card1 must be on the false side
        goldDeck.flipCard1();
        assertFalse(goldDeck.peekCard1().getSide());
    }

    @Test
    void flipCard2() {
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        // the Card 2 must be on the true side by default
        assertTrue(goldDeck.peekCard2().getSide());

        // after fliCard1, Card2 must be on the false side
        goldDeck.flipCard2();
        assertFalse(goldDeck.peekCard2().getSide());
    }
}
