import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import org.junit.jupiter.api.Test;
import java.awt.Point;
import java.util.HashMap;
import java.util.Map;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

class PlayArea {
/*
    @Test
    public void testPlaceStarter() {
        PlayArea playArea = new PlayArea();
        PlayableCard card = new StarterCard(); // Inserisci qui un oggetto PlayableCard valido
        playArea.placeStarter(card);
        assertEquals(1, playArea.getPlacedCards().size());
        assertEquals(card, playArea.getPlacedCards().get(new Point(0, 0)));
    }
    */
    /*
    @Test
    public void testCheckRequirements() {
        PlayableCard card = new GoldCard(); // Inserisci qui un oggetto PlayableCard valido
        assertTrue(playArea.checkRequirements(card));
    }

    @Test
    public void testAllowedMove() {
        assertFalse(playArea.allowedMove(new Point(1, 1))); // Inserisci qui un punto valido
        assertTrue(playArea.allowedMove(new Point(0, 0)));
    }

    @Test
    public void testPlace() {
        Deck<PlayableCard> deck1 = new Deck<>(CardType.GOLD);
        PlayableCard card = deck1.draw();
        Point point = new Point(1, 1); // Inserisci qui un punto valido
        int score = playArea.place(card, point);
        assertEquals(0, score); // Inserisci qui il punteggio corretto previsto
        assertEquals(card, playArea.getPlacedCards().get(point));
    }

    @Test
    public void testUpdateAvailableRes() {
        PlayableCard card = new PlayableCard(); // Inserisci qui un oggetto PlayableCard valido
        Point point = new Point(1, 1); // Inserisci qui un punto valido
        Map<Resources, Integer> initialResources = new HashMap<>(playArea.getAchievedResources());
        playArea.updateAvailableRes(card, point);
        // Verifica che le risorse siano state aggiornate correttamente
        assertEquals(initialResources.getOrDefault(Resources.MUSHROOM, 0) + 1,
                playArea.getAchievedResources().getOrDefault(Resources.MUSHROOM, 0).intValue());
        assertEquals(initialResources.getOrDefault(Resources.ANIMAL, 0) + 1,
                playArea.getAchievedResources().getOrDefault(Resources.PLANT, 0).intValue());
        // Continua con le altre risorse...
    }

 */
}