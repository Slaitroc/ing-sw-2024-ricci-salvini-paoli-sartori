package it.polimi.ingsw.gc31.Model.Deck;

import it.polimi.ingsw.gc31.Model.Card.Card;
import it.polimi.ingsw.gc31.Model.Card.GoldCard;
import it.polimi.ingsw.gc31.Model.Card.ObjectiveCard;
import it.polimi.ingsw.gc31.Model.Card.PlayableCard;
import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;
import it.polimi.ingsw.gc31.Model.Strategies.Objective;
import org.junit.jupiter.api.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

class DeckTest {

    @Test
    void draw() {
        List<Card> temp = new ArrayList<>();
        temp.add(crateGoldCard());
        temp.add(crateGoldCard());
        temp.add(crateGoldCard());
        temp.add(crateGoldCard());

        Deck deck = new Deck(temp);

        List<Card> temp2 = new ArrayList<>();
        temp2.add(createObjectiveCard());
        temp2.add(createObjectiveCard());
        temp2.add(createObjectiveCard());
        temp2.add(createObjectiveCard());

        Deck deckOb = new Deck(temp2);

        List<PlayableCard> hand = new ArrayList<>();
        hand.add((PlayableCard) deck.draw());
        hand.add((PlayableCard) deck.draw());

        ObjectiveCard ob = (ObjectiveCard) deckOb.draw();
        assertFalse(ob.getSide());

        hand.get(0).changeSide();
    }

    @Test
    void refill() {
    }
    public ObjectiveCard createObjectiveCard() {
        Objective ob = null;
        int score = 0;
        String dirImgFront = null;
        String dirImgBack = null;

        return new ObjectiveCard(ob, score, null, null);
    }
    public GoldCard crateGoldCard() {
        Color color = Color.RED;
        int score = 0;
        List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.FEATHER);
        resourcesFront.add(Resources.EMPTY);
        resourcesFront.add(Resources.HIDDEN);

        List<Resources> resourcesBack = new ArrayList<>();
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.EMPTY);
        resourcesBack.add(Resources.MUSHROOM);

        Map<Resources, Integer> requirements = new HashMap<>();
        requirements.put(Resources.MUSHROOM, 2);
        requirements.put(Resources.ANIMAL, 1);

        String dirImgFront = null;
        String dirImgBack = null;
        Objective ob = null;

        return new GoldCard(color, score, resourcesFront, resourcesBack, requirements, dirImgFront, dirImgBack, ob);
    }
}