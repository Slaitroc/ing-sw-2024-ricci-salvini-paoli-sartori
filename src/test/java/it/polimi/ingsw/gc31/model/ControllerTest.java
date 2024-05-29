package it.polimi.ingsw.gc31.model;

import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ControllerTest {
//    static Controller controller;
//    static VirtualClient mockClient;
//    IGameController gameController2, gameController3;
//    GameController gameController1;
//
//    @BeforeAll
//    public static void setUp() {
//        controller = Controller.getController();
//        mockClient = Mockito.mock(VirtualClient.class);
//    }
//
//    // NOTE To run with coverage, use different usernames also in different tests
//    // and call a test per time or
//    @Test
//    public void managementOfMultipleMatches() throws RemoteException, PlayerNicknameAlreadyExistsException {
//        synchronized (controller) {
//            controller.connect(mockClient, "Player1");
//            controller.connect(mockClient, "Player2");
//            controller.connect(mockClient, "Player3");
//            controller.connect(mockClient, "Player4");
//            controller.connect(mockClient, "Matteo");
//            controller.connect(mockClient, "Nicola");
//            controller.connect(mockClient, "Pippo");
//            controller.connect(mockClient, "Pluto");
//
//            // gameController1 = (GameController) controller.createGame("Player1", 4);
//            controller.joinGame("Player2", 0);
//            controller.joinGame("Player3", 0);
//            controller.joinGame("Player4", 0);
//
//            // gameController2 = controller.createGame("Matteo", 2);
//            controller.joinGame("Nicola", 1);
//
//            // gameController3 = controller.createGame("Pippo", 2);
//            controller.joinGame("Pluto", 2);
//        }
//
//        // controller.getGameList("Krotox"); //Cant call this, mockClient is actually
//        // null, can't call .showListGame(res);
//    }
//
//    @Test
//    public void testSameUsernames() throws PlayerNicknameAlreadyExistsException, RemoteException {
//        controller.connect(mockClient, "Player");
//        assertThrows(PlayerNicknameAlreadyExistsException.class, () -> controller.connect(mockClient, "Player"));
//    }
//
//    // NOTE to run this test singularly, make the players connect with idGame = 0
//    // otherwise, connect with idGame = 3
//    @Test
//    public void testGameFlow()
//            throws PlayerNicknameAlreadyExistsException, IOException, IllegalStateOperationException {
//        synchronized (controller) {
//            controller.connect(mockClient, "Krotox");
//            // gameController1 = (GameController) controller.createGame("Krotox", 4);
//            controller.connect(mockClient, "Slaitroc");
//            controller.joinGame("Slaitroc", 3);
//            controller.connect(mockClient, "SSalvo");
//            controller.joinGame("SSalvo", 3);
//            controller.connect(mockClient, "AleSarto");
//            controller.joinGame("AleSarto", 3);
////            gameController1.initGame();
//        }
//
//        GameModel gameModel = gameController1.getModel();
//        List<String> playerList = new ArrayList<>();
//        System.out.println("Current Game Cycle is: ");
//        for (int i = 0; i < gameModel.getNumOfPlayers() - 1; i++) {
//            playerList.add(gameModel.getCurrPlayingPlayer().getUsername());
//            gameController1.chooseSecretObjective(gameModel.getCurrPlayingPlayer().getUsername(), 1);
//            gameController1.playStarter(gameModel.getCurrPlayingPlayer().getUsername());
//            gameModel.setNextPlayingPlayer();
//        }
//        // System.out.println(playingPlayer);
//        playerList.add(gameModel.getCurrPlayingPlayer().getUsername());
//        gameController1.chooseSecretObjective(gameModel.getCurrPlayingPlayer().getUsername(), 2);
//        gameController1.changeStarterSide(gameModel.getCurrPlayingPlayer().getUsername());
//        gameController1.changeStarterSide(gameModel.getCurrPlayingPlayer().getUsername());
//        gameController1.playStarter(gameModel.getCurrPlayingPlayer().getUsername());
//        gameController1.changeStarterSide(gameModel.getCurrPlayingPlayer().getUsername());
//        gameModel.setNextPlayingPlayer();
//
//        @SuppressWarnings("unused")
//        boolean lastTurn = true;
//        int count = 1;
//        // Momentarily set to 15, to simulate a full match even without points
//        do {
//            for (String playingPlayer : playerList) {
//                // System.out.println("It's currently " + playingPlayer + "'s turn");
//                gameController1.selectCard(playingPlayer, 0);
//                gameController1.play(playingPlayer, count, count);
//                // System.out.println("Player: " + playingPlayer + " placed a card in (" + count
//                // + ", " + count + ")");
//                gameController1.drawResource(playingPlayer);
//                // System.out.println("Player: " + playingPlayer + " drawn a resource card\n");
//            }
//            count++;
//        } while (count != 15);
//
//        String playingPlayer = playerList.get(2);
//
//        gameController1.play(playingPlayer, count, count); // Supposed to throw error
//        playingPlayer = playerList.getFirst();
//        // System.out.println("It's currently " + playingPlayer + "'s turn");
//        gameController1.selectCard(playingPlayer, 0);
//        gameController1.changeSide(playingPlayer);
//        gameController1.play(playingPlayer, count, count);
//        // System.out.println("Player: " + playingPlayer + " placed a card in (" + count
//        // + ", " + count + ")");
//        gameController1.drawResourceCard1(playingPlayer);
//        // System.out.println("Player: " + playingPlayer + " drawn resource card 1\n");
//
//        playingPlayer = playerList.get(1);
//        // System.out.println("It's currently " + playingPlayer + "'s turn");
//        gameController1.selectCard(playingPlayer, 0);
//        gameController1.play(playingPlayer, count, count);
//        // System.out.println("Player: " + playingPlayer + " placed a card in (" + count
//        // + ", " + count + ")");
//        gameController1.drawResourceCard2(playingPlayer);
//        // System.out.println("Player: " + playingPlayer + " drawn resource card 2\n");
//        gameModel.startLastTurn();
//
//        playingPlayer = playerList.get(2);
//        // System.out.println("It's currently " + playingPlayer + "'s turn");
//        gameController1.selectCard(playingPlayer, 0);
//        gameController1.play(playingPlayer, count, count);
//        // System.out.println("Player: " + playingPlayer + " placed a card in (" + count
//        // + ", " + count + ")");
//        gameController1.drawGoldCard1(playingPlayer);
//        // System.out.println("Player: " + playingPlayer + " drawn gold card 1\n");
//
//        playingPlayer = playerList.get(3);
//        // System.out.println("It's currently " + playingPlayer + "'s turn");
//        gameController1.selectCard(playingPlayer, 0);
//        gameController1.play(playingPlayer, count, count);
//        // System.out.println("Player: " + playingPlayer + " placed a card in (" + count
//        // + ", " + count + ")");
//        gameController1.drawGoldCard2(playingPlayer);
//        // System.out.println("Player: " + playingPlayer + " drawn gold card 2\n");
//
//        /*
//         * System.out.println("\n___Checking out players points___\n");
//         * gameModel.startEndGame();
//         * for (int i = 0; i < gameModel.getNumOfPlayers(); i++) {
//         * gameModel.getObjectives(0).getObjective().isObjectiveDone(gameModel.
//         * getCurrPlayingPlayer().getPlayArea().getPlacedCards(), null,
//         * gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//         * gameModel.getObjectives(1).getObjective().isObjectiveDone(gameModel.
//         * getCurrPlayingPlayer().getPlayArea().getPlacedCards(), null,
//         * gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//         * gameModel.getCurrPlayingPlayer().getObjectiveCard().getObjective().
//         * isObjectiveDone(gameModel.getCurrPlayingPlayer().getPlayArea().getPlacedCards
//         * (), null,
//         * gameModel.getCurrPlayingPlayer().getPlayArea().getAchievedResources());
//         * gameModel.setNextPlayingPlayer();
//         * playingPlayer = gameModel.getCurrPlayingPlayer().getUsername();
//         * System.out.println("Player: " + playingPlayer + " scored a total of " +
//         * gameModel.getCurrPlayingPlayer().getScore() + " points");
//         * //assertEquals(0, gameModel.getCurrPlayingPlayer().getScore());
//         * }
//         */
//    }
//
}
