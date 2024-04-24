package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.awt.*;
import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerTest {
    static Controller controller;
    static VirtualClient mockClient;
    GameController gameController1, gameController2, gameController3;

    @BeforeAll
    public static void setUp() throws RemoteException {
        controller = Controller.getController();
        mockClient = Mockito.mock(VirtualClient.class);
    }

    //NOTE To run with coverage, use different usernames also in different tests
    //     and call a test per time or
    @Test
    public void managementOfMultipleMatches() throws RemoteException, PlayerNicknameAlreadyExistsException, NoGamesException {
        synchronized (controller){
            controller.connect(mockClient, "Krotox");
            controller.connect(mockClient, "Slaitroc");
            controller.connect(mockClient, "SSalvo");
            controller.connect(mockClient, "AleSarto");
            controller.connect(mockClient, "Matteo");
            controller.connect(mockClient, "Pippo");
            controller.connect(mockClient, "Pluto");

            gameController1 = (GameController) controller.createGame("Krotox", 4);
            controller.joinGame("Slaitroc", 0);
            controller.joinGame("SSalvo", 0);
            controller.joinGame("AleSarto", 0);

            gameController2 = (GameController) controller.createGame("Matteo", 2);
            controller.joinGame("Nicola", 1);

            gameController3 = (GameController) controller.createGame("Pippo", 2);
            controller.joinGame("Pluto", 2);
        }

        //controller.getGameList("Krotox"); //Cant call this, mockClient is actually null, can't call .showListGame(res);
    }


    @Test
    public void testSameUsernames() throws RemoteException, PlayerNicknameAlreadyExistsException {
        controller.connect(mockClient, "Player");
        assertThrows(PlayerNicknameAlreadyExistsException.class, () -> controller.connect(mockClient, "Player"));
    }

    //NOTE to run this test singularly, make the players connect with idGame = 0
    //      otherwise, connect with idGame = 3
    @Test
    public void testGameFlow() throws PlayerNicknameAlreadyExistsException, RemoteException, IllegalStateOperationException {
        synchronized (controller){
            controller.connect(mockClient, "Player1");
            gameController1 = (GameController) controller.createGame("Player1", 4);
            controller.connect(mockClient, "Player2");
            controller.joinGame("Player2", 3);
            controller.connect(mockClient, "Player3");
            controller.joinGame("Player3", 3);
            controller.connect(mockClient, "Player4");
            controller.joinGame("Player4", 3);
        }

        GameModel gameModel = gameController1.getModel();
        String playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
        System.out.println("Current Game Cycle is: ");
        for (int i = 0; i < gameModel.getNumOfPlayers()-1; i++) {
            System.out.println(playingPlayer);
            gameController1.chooseSecretObjective1(playingPlayer);
            gameController1.playStarter(playingPlayer);
            gameModel.setNextPlayingPlayer();
            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
        }
        System.out.println(playingPlayer);
        gameController1.chooseSecretObjective2(playingPlayer);
        gameController1.playStarter(playingPlayer);
        gameModel.setNextPlayingPlayer();
        playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();

        boolean lastTurn = true;
        int count = 1;
        while (true) {
            for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
                System.out.println("It's currently " + playingPlayer + "'s turn");
                gameController1.selectCard(playingPlayer, 0);
                gameController1.play(playingPlayer, count, count);
                assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
                System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
                gameController1.drawResource(playingPlayer);
                System.out.println("Player: " + playingPlayer + " drawn a resource card\n");
                playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            }
            count++;
            if (gameModel.getGameState() == GameState.LAST_TURN) break;  //This in case of players actually earning points
            if (count == 15) { //Momentarily set to 15, to simulate a full match even without points
                System.out.println("___TESTING MORE FUNCTIONS___\n");
                lastTurn = false;
            }
        }

        if(!lastTurn){ //Works only if 4 players are playing
            count++;
            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("It's currently " + playingPlayer + "'s turn");
            gameController1.selectCard(playingPlayer, 0);
            gameController1.play(playingPlayer, count, count);
            assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
            gameController1.drawResourceCard1(playingPlayer);
            System.out.println("Player: " + playingPlayer + " drawn resource card 1\n");

            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("It's currently " + playingPlayer + "'s turn");
            gameController1.selectCard(playingPlayer, 0);
            gameController1.play(playingPlayer, count, count);
            assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
            gameController1.drawResourceCard2(playingPlayer);
            System.out.println("Player: " + playingPlayer + " drawn resource card 2\n");


            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("It's currently " + playingPlayer + "'s turn");
            gameController1.selectCard(playingPlayer, 0);
            gameController1.play(playingPlayer, count, count);
            assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
            gameController1.drawGoldCard1(playingPlayer);
            System.out.println("Player: " + playingPlayer + " drawn gold card 1\n");

            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("It's currently " + playingPlayer + "'s turn");
            gameController1.selectCard(playingPlayer, 0);
            gameController1.play(playingPlayer, count, count);
            assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
            gameController1.drawGoldCard2(playingPlayer);
            System.out.println("Player: " + playingPlayer + " drawn gold card 2\n");

            gameModel.startLastTurn();
        }

        //TODO During the last turn can the players draw?? CHECK RULES
        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("It's currently " + playingPlayer + "'s turn");
            gameController1.selectCard(playingPlayer, 0);
            gameController1.play(playingPlayer, count, count);
            assertNotNull(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards().get(new Point(count, count)));
            System.out.println("Player: " + playingPlayer + " placed a card in (" + count + ", " + count + ")");
            gameController1.drawGold(playingPlayer);
            System.out.println("Player: " + playingPlayer + " drawn a card \n");
        }

        System.out.println("\n___Checking out players points___\n");
        gameModel.startEndGame();
        for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
            gameModel.getObjectives(0).getObjective().isObjectiveDone(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            gameModel.getObjectives(1).getObjective().isObjectiveDone(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            gameModel.getCurrPlayingPlayer().getObjectiveCard().getObjective().isObjectiveDone(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards(), null, gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
            gameModel.setNextPlayingPlayer();
            playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
            System.out.println("Player: " + playingPlayer + " scored a total of " + gameModel.getCurrPlayingPlayer().getScore() + " points");
            assertEquals(0, gameModel.getCurrPlayingPlayer().getScore());
        }
    }


}
