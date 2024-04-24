package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.strategies.Objective;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
    GameModel gameModel;


    @BeforeEach
    public void setUp() throws IllegalStateOperationException {
        gameModel = new GameModel();
        Set<String> usernames = Set.of("Player1", "Player2", "Player3");
        gameModel.createPlayers(usernames);
        gameModel.initCommonObj();
        gameModel.initHands();
        gameModel.getBoard().getDeckResource().refill();
        gameModel.getBoard().getDeckGold().refill();
        gameModel.initStarters();
        gameModel.startGame();
        System.out.println("Current Game Cycle is: ");
        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
            System.out.println(gameModel.getCurrPlayingPlayer().getUsername());
            gameModel.getCurrPlayingPlayer().addObjectiveCard(gameModel.getBoard().getDeckObjective().draw());
            gameModel.getCurrPlayingPlayer().playStarter();
            gameModel.setNextPlayingPlayer();
        }
    }

    @Test
    public void gameFlowTest() {
        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
        gameModel.getCurrPlayingPlayer().setSelectedCard(2);
        gameModel.getCurrPlayingPlayer().play(new Point(1, 1));
        assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(1, 1)));
        gameModel.getCurrPlayingPlayer().setSelectedCard(1);
        gameModel.getCurrPlayingPlayer().play(new Point(-1, -1));
        gameModel.getCurrPlayingPlayer().drawResourceCard1();
        gameModel.setNextPlayingPlayer();
        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
        gameModel.getCurrPlayingPlayer().drawGold();
        gameModel.getCurrPlayingPlayer().setSelectedCard(0);
        gameModel.getCurrPlayingPlayer().getSelectedCard().changeSide();
        gameModel.getCurrPlayingPlayer().play(new Point(-1, 1));
        assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(-1, 1)));
        gameModel.getCurrPlayingPlayer().drawGold();
        gameModel.setNextPlayingPlayer();
        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
        System.out.println("TEST SUCCESSFUL");


    }

    // TODO current test is playing 15 turns. Create a test were players are actually earning points (use functions below)
    // NOTE Resource deck is replaced with the gold Deck after turn 12
    // NOTE In current implementation player's order is actually random
    @Test
    public void fullGameTest() {
        Player plaingPlayer = gameModel.getCurrPlayingPlayer();
        boolean lastTurn = false;
        int count = 1;
        while (true) {
            for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
                System.out.println("It's currently " + plaingPlayer.getUsername() + "'s turn");
                plaingPlayer.setSelectedCard(0);
                plaingPlayer.play(new Point(count, count));
                assertNotNull(plaingPlayer.getPlayArea().getPlacedCards().get(new Point(count, count)));
                System.out.println("Player: " + plaingPlayer.getUsername() + " placed a card in (" + count + ", " + count + ")");
                plaingPlayer.drawResource();
                System.out.println("Player: " + plaingPlayer.getUsername() + " drawn a resource card\n");
                gameModel.setNextPlayingPlayer();
                plaingPlayer = gameModel.getCurrPlayingPlayer();
                plaingPlayer.setInGameState(new NotPlaced());  //Wake up function of the player
            }
            count++;
            if (gameModel.getGameState() == GameState.LAST_TURN) break;
            if (count == 15) { //
                System.out.println("___LAST TURN HAS STARTED___");
                gameModel.startLastTurn();
            }
        }

        //TODO During the last turn can the players draw?? CHECK RULES
        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
            System.out.println("It's currently " + plaingPlayer.getUsername() + "'s turn");
            plaingPlayer.setSelectedCard(0);
            plaingPlayer.play(new Point(count, count));
            assertNotNull(plaingPlayer.getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + plaingPlayer.getUsername() + " placed a card in (" + count + ", " + count + ")");
            plaingPlayer.drawResourceCard1();
            gameModel.setNextPlayingPlayer();
            plaingPlayer = gameModel.getCurrPlayingPlayer();
            plaingPlayer.setInGameState(new NotPlaced());
        }

        System.out.println("\n___Checking out players points___\n");
        gameModel.startEndGame();
        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
            gameModel.getObjectives(0).getObjective().isObjectiveDone(plaingPlayer.getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            gameModel.getObjectives(1).getObjective().isObjectiveDone(plaingPlayer.getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            plaingPlayer.getObjectiveCard().getObjective().isObjectiveDone(plaingPlayer.getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            gameModel.setNextPlayingPlayer();
            plaingPlayer = gameModel.getCurrPlayingPlayer();
            System.out.println("Player: " + plaingPlayer.getUsername() + " scored a total of " + plaingPlayer.getScore() + " points");
            assertEquals(0, plaingPlayer.getScore());
        }
    }


    private static PlayableCard createStarterCard(Resources f1, Resources f2, Resources f3, Resources f4, Resources b1,
                                                  Resources b2, Resources b3, Resources b4, Resources b5, Resources b6, Resources b7) {
        int score = 0;

        java.util.List<Resources> resourceFront = new ArrayList<>();
        resourceFront.add(f1);
        resourceFront.add(f2);
        resourceFront.add(f3);
        resourceFront.add(f4);

        Map<Resources, Integer> requirements = new HashMap<>();

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourceFront,
                requirements,
                dirImgFront,
                ob);

        java.util.List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);
        resourceBack.add(b6);
        resourceBack.add(b7);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new StarterCard(CardColor.NOCOLOR, front, back);
    }

    private static PlayableCard createGoldCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                               Resources b1, Resources b2, Resources b3, Resources b4, Resources b5,
                                               Resources r1, int n1, Resources r2, int n2) {
        it.polimi.ingsw.gc31.model.enumeration.CardColor color = it.polimi.ingsw.gc31.model.enumeration.CardColor.RED;

        int score = 0;

        // resources deve avere 4 elementi
        java.util.List<Resources> resourcesFront = new ArrayList<>();
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
                ob);

        // resourceBack può avere dai 4 ai 7 elementi
        java.util.List<Resources> resourceBack = new ArrayList<>();
        resourceBack.add(b1);
        resourceBack.add(b2);
        resourceBack.add(b3);
        resourceBack.add(b4);
        resourceBack.add(b5);

        String dirImgBack = null;

        CardBack back = new CardBack(
                resourceBack,
                dirImgBack);

        return new GoldCard(color, front, back);
    }

    // f1 sta per front alto-dx, f2 basso-dx, f3 basso-sx, f4 alto-sx
    private static PlayableCard createResourceCard(Resources f1, Resources f2, Resources f3, Resources f4,
                                                   Resources b1, Resources b2, Resources b3, Resources b4, Resources b5) {

        CardColor color;
        switch (b5) {
            case ANIMAL -> color = CardColor.BLUE;
            case MUSHROOM -> color = CardColor.RED;
            case INSECT -> color = CardColor.PURPLE;
            case PLANT -> color = CardColor.GREEN;
            default -> color = CardColor.NOCOLOR;
        }

        int score = 0;

        // resources deve avere 4 elementi
        java.util.List<Resources> resourcesFront = new ArrayList<>();
        resourcesFront.add(f1);
        resourcesFront.add(f2);
        resourcesFront.add(f3);
        resourcesFront.add(f4);

        Map<Resources, Integer> requirements = Collections.emptyMap();

        String dirImgFront = null;
        Objective ob = null;

        CardFront front = new CardFront(
                score,
                resourcesFront,
                requirements,
                dirImgFront,
                ob);

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
                dirImgBack);

        return new ResourceCard(color, front, back);
    }
}
