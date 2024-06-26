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
        for (int i=0; i<3; i++) {
            assertDoesNotThrow(starterDeck::draw);
        }
        assertThrowsExactly(EmptyDeckException.class, starterDeck::draw);

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
        assertTrue(objectiveDeck.isEmpty());
    }

    @Test
    void replaceDeck() throws EmptyDeckException {
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        Deck<PlayableCard> resourceDeck = new Deck<>(CardType.RESOURCE);

        goldDeck.replaceDeck(resourceDeck.getQueueDeck());
        assertInstanceOf(ResourceCard.class, goldDeck.draw());
    }

    @Test
    void refill() {
    }

    @Test
    void getCard1() {
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        assertEquals(38, goldDeck.getQueueDeck().size());
        // this method returns card 1 and removes it from deck
        goldDeck.getCard1();
        assertEquals(37, goldDeck.getQueueDeck().size());

        // refill is automatically called
        assertNotNull(goldDeck.getCard1());
    }

    @Test
    void getCard2() {
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        assertEquals(38, goldDeck.getQueueDeck().size());
        // this method returns card 2 and removes it from deck
        goldDeck.getCard2();
        assertEquals(37, goldDeck.getQueueDeck().size());

        // refill is automatically called
        assertNotNull(goldDeck.getCard2());
    }

    @Test
    void peekCard() {
        // this method returns the card on the top of the deck but doesn't remove it from deck
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);

        assertEquals(40, goldDeck.getQueueDeck().size());
        goldDeck.peekCard();
        assertEquals(40, goldDeck.getQueueDeck().size());
    }

    @Test
    void peekCard1() {
        // this method returns card 1 but doesn't remove it from deck
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        assertEquals(38, goldDeck.getQueueDeck().size());
        goldDeck.peekCard1();
        assertEquals(38, goldDeck.getQueueDeck().size());
    }

    @Test
    void peekCard2() {
        // this method returns card 2 but doesn't remove it from deck
        Deck<PlayableCard> goldDeck = new Deck<>(CardType.GOLD);
        goldDeck.refill();

        assertEquals(38, goldDeck.getQueueDeck().size());
        goldDeck.peekCard2();
        assertEquals(38, goldDeck.getQueueDeck().size());
    }
}
