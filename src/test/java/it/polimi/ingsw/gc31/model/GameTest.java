package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class GameTest {
//    GameModel gameModel;
//
//    @BeforeEach
//    public void setUp() throws IllegalStateOperationException {
//        gameModel = new GameModel();
//        Set<String> usernames = Set.of("Player1", "Player2", "Player3");
//        gameModel.createPlayers(usernames);
//        gameModel.initCommonObj();
//        gameModel.initHands();
//        gameModel.getBoard().getDeckResource().refill();
//        gameModel.getBoard().getDeckGold().refill();
//        gameModel.initStarters();
//        System.out.println("Current Game Cycle is: ");
//        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
//            System.out.println(gameModel.getCurrPlayingPlayer().getUsername());
//            // TODO da riguardare
//            // gameModel.getCurrPlayingPlayer().addObjectiveCard(gameModel.getBoard().getDeckObjective().draw());
//            gameModel.getCurrPlayingPlayer().playStarter();
//            gameModel.setNextPlayingPlayer();
//        }
//    }
//
//    @Test
//    public void gameFlowTest() {
//        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
//        gameModel.getCurrPlayingPlayer().setSelectedCard(2);
//        gameModel.getCurrPlayingPlayer().play(new Point(1, 1));
//        assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(1, 1)));
//        gameModel.getCurrPlayingPlayer().setSelectedCard(1);
//        gameModel.getCurrPlayingPlayer().play(new Point(-1, -1));
//        gameModel.getCurrPlayingPlayer().drawResourceCard1();
//        gameModel.setNextPlayingPlayer();
//        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
//        gameModel.getCurrPlayingPlayer().drawGold();
//        gameModel.getCurrPlayingPlayer().setSelectedCard(0);
//        gameModel.getCurrPlayingPlayer().getSelectedCard().changeSide();
//        gameModel.getCurrPlayingPlayer().play(new Point(-1, 1));
//        assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(-1, 1)));
//        gameModel.getCurrPlayingPlayer().drawGold();
//        gameModel.setNextPlayingPlayer();
//        System.out.println("It's currently " + gameModel.getCurrPlayingPlayer().getUsername() + "'s turn");
//        System.out.println("TEST SUCCESSFUL");
//
//    }
//
//    // TODO current test is playing 15 turns. Create a test were players are
//    // actually earning points (use functions below)
//    // NOTE Resource deck is replaced with the gold Deck after turn 12
//    // NOTE In current implementation player's order is actually random
//    @Test
//    public void fullGameTest() {
//        Player playingPlayer = gameModel.getCurrPlayingPlayer();
//        int count = 1;
//        while (true) {
//            for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
//                System.out.println("It's currently " + playingPlayer.getUsername() + "'s turn");
//                playingPlayer.setSelectedCard(0);
//                playingPlayer.play(new Point(count, count));
//                assertNotNull(playingPlayer.getPlayArea().getPlacedCards().get(new Point(count, count)));
//                System.out.println(
//                        "Player: " + playingPlayer.getUsername() + " placed a card in (" + count + ", " + count + ")");
//                playingPlayer.drawResource();
//                System.out.println("Player: " + playingPlayer.getUsername() + " drawn a resource card\n");
//                gameModel.setNextPlayingPlayer();
//                playingPlayer = gameModel.getCurrPlayingPlayer();
//                playingPlayer.setInGameState(new NotPlaced()); // Wake up function of the player
//            }
//            count++;
//            if (gameModel.getGameState() == GameState.LAST_TURN)
//                break;
//            if (count == 15) { //
//                System.out.println("___LAST TURN HAS STARTED___");
//                gameModel.startLastTurn();
//            }
//        }
//
//        // TODO During the last turn can the players draw?? CHECK RULES
//        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
//            System.out.println("It's currently " + playingPlayer.getUsername() + "'s turn");
//            playingPlayer.setSelectedCard(0);
//            playingPlayer.play(new Point(count, count));
//            assertNotNull(playingPlayer.getPlayArea().getPlacedCards().get(new Point(count, count)));
//            System.out.println(
//                    "Player: " + playingPlayer.getUsername() + " placed a card in (" + count + ", " + count + ")");
//            playingPlayer.drawResourceCard1();
//            gameModel.setNextPlayingPlayer();
//            playingPlayer = gameModel.getCurrPlayingPlayer();
//            playingPlayer.setInGameState(new NotPlaced());
//        }
//
//        System.out.println("\n___Checking out players points___\n");
//        gameModel.startEndGame();
//        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
//            gameModel.getObjectives(0).getObjective().isObjectiveDone(playingPlayer.getPlayArea().getPlacedCards(),
//                    null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//            gameModel.getObjectives(1).getObjective().isObjectiveDone(playingPlayer.getPlayArea().getPlacedCards(),
//                    null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//            playingPlayer.getObjectiveCard().getObjective().isObjectiveDone(
//                    playingPlayer.getPlayArea().getPlacedCards(), null,
//                    gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//            gameModel.setNextPlayingPlayer();
//            playingPlayer = gameModel.getCurrPlayingPlayer();
//            System.out.println("Player: " + playingPlayer.getUsername() + " scored a total of "
//                    + playingPlayer.getScore() + " points");
//            assertEquals(0, playingPlayer.getScore());
//        }
//    }
//
}
