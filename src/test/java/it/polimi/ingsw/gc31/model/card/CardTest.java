package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.Board;
import org.junit.jupiter.api.Test;

import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class CardTest {
    Board board = new Board();

    @Test
    void getSide() {
        Card card = board.getDeckGold().draw();

        // Le carte devono avere side=false di default
        assertFalse(card.getSide());

        // Cambio il lato a tutte le carte
        card.changeSide();

        // Dopo aver cambiato il lato le carte devono avere side=true
        assertTrue(card.getSide());
    }
}