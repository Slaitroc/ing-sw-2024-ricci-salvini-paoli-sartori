package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.*;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.player.Placed;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.model.strategies.Count;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.*;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel model;
    Map<String, VirtualClient> clients;
    Object lock = new Object();

    @BeforeEach
    public void setUp() {
        model = new GameModel(lock, 0);
        clients = new LinkedHashMap<>();

        clients.put("Players1", new FakeClient());
        clients.put("Players2", new FakeClient());
        clients.put("Players3", new FakeClient());
        clients.put("Players4", new FakeClient());
    }

    @Test
    void initGame() {
        assertInstanceOf(CreationGameModelState.class, model.getGameState());
        assertDoesNotThrow(() -> model.initGame(clients, lock));

        assertInstanceOf(SetupGameModelState.class, model.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));

        model.setGameState(new RunningGameModelSate(model));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));

        model.setGameState(new ShowDownGameModelState(model));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));

        model.setGameState(new LastTurnGameModelState(model));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));

        model.setGameState(new EndGameModelState(model));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));

        model.setGameState(new BlockedGameModelState(model, null));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients, lock));
    }

    @Test
    void setNextPlayingPlayer() {
        utilityInitGame();

        assertEquals(0, model.getCurrIndexPlayer());

        utilitySkipSetupGame();

        assertEquals(0, model.getCurrIndexPlayer());
        assertEquals("notplaced", model.getCurrPlayer().infoState());

        model.setNextPlayingPlayer();
        assertEquals(1, model.getCurrIndexPlayer());
        assertEquals("notplaced", model.getCurrPlayer().infoState());

        model.setNextPlayingPlayer();
        assertEquals(2, model.getCurrIndexPlayer());
        assertEquals("notplaced", model.getCurrPlayer().infoState());

        model.setNextPlayingPlayer();
        assertEquals(3, model.getCurrIndexPlayer());
        assertEquals("notplaced", model.getCurrPlayer().infoState());

        model.setNextPlayingPlayer();
        assertEquals(0, model.getCurrIndexPlayer());
        assertEquals("notplaced", model.getCurrPlayer().infoState());

    }

    @Test
    void setNextPlayingPlayerDisconnections() {
        utilityInitGame();
        assertEquals(0, model.getCurrIndexPlayer());

        utilitySkipSetupGame();

        for (int i = 0; i < 3; i++) {
            assertEquals(i, model.getCurrIndexPlayer());
            model.setNextPlayingPlayer();
        }

        assertEquals(3, model.getCurrIndexPlayer());
        model.setNextPlayingPlayer();
        assertEquals(0, model.getCurrIndexPlayer());

        // all players are set to waiting, the player in turn is set to not-placed
        assertEquals("notplaced", model.getCurrPlayer().infoState());
        for (Player player : model.getPlayers().values()) {
            if (!player.getUsername().equals(model.getCurrPlayer().getUsername()))
                assertEquals("waiting", player.infoState());
        }
    }

    @Test
    void endTurn() throws EmptyDeckException {
        FakeGameModel model = new FakeGameModel();
        Deck<ObjectiveCard> deck = new Deck<>(CardType.OBJECTIVE);
        FakePlayer player;
        assertInstanceOf(CreationGameModelState.class, model.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, model::endTurn);
        utilityInitGame(model);

        assertInstanceOf(SetupGameModelState.class, model.getGameState());
        assertDoesNotThrow(model::endTurn);

        model.commonObjectives.add(deck.draw());
        model.commonObjectives.add(deck.draw());

        // if last player reach 20 points the state must change to Last turn
        model.setGameState(new RunningGameModelSate(model));
        assertEquals(0, model.getCurrIndexPlayer());
        for (int i = 0; i < 3; i++) {
            player = model.getCurrPlayer();
            player.setObjectiveCard(deck.draw());
            model.setNextPlayingPlayer();
        }

        assertEquals(3, model.getCurrIndexPlayer());
        player = model.getCurrPlayer();
        player.setObjectiveCard(deck.draw());
        player.setScore(20);
        try {
            model.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(LastTurnGameModelState.class, model.getGameState());

        for (int i = 0; i < 3; i++) {
            try {
                model.endTurn();
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
        }

        assertEquals(3, model.getCurrIndexPlayer());
        try {
            model.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        assertInstanceOf(EndGameModelState.class, model.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, model::endTurn);

        // reset game
        System.out.println("\n\n\n\n");
        FakeGameModel model2 = new FakeGameModel();
        utilityInitGame(model2);

        model2.commonObjectives.add(deck.draw());
        model2.commonObjectives.add(deck.draw());

        model2.setGameState(new RunningGameModelSate(model2));
        model2.setNextPlayingPlayer();
        for (int i = 0; i < 4; i++) {
            player = model2.getCurrPlayer();
            player.setObjectiveCard(deck.draw());
            model2.setNextPlayingPlayer();
        }
        assertEquals(model2.getCurrIndexPlayer(), 0);

        // if first (or non-last player reach 20 points the state must change to
        // showdown
        player = model2.getCurrPlayer();
        player.setScore(20);

        for (int i = 0; i < 3; i++) {
            try {
                model2.endTurn();
                assertInstanceOf(ShowDownGameModelState.class, model2.getGameState());
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
        }
        assertEquals(3, model2.getCurrIndexPlayer());
        try {
            model2.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(LastTurnGameModelState.class, model2.getGameState());
        assertEquals(0, model2.getCurrIndexPlayer());

        for (int i = 0; i < 4; i++) {
            try {
                model2.endTurn();
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
        }
        assertInstanceOf(EndGameModelState.class, model2.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, model2::endTurn);
    }

    @Test
    void getPlayers() {
        Map<String, Player> players = model.getPlayers();

        assertNotNull(players);

        assertTrue(clients.keySet().containsAll(players.keySet()));
    }

    @Test
    void endTurnBothDeckEmptyShowDown() throws EmptyDeckException {
        utilityInitGame();
        utilitySkipSetupGame();

        for (int i = 0; i < 34; i++) {
            model.getBoard().getDeckGold().draw();
        }
        for (int i = 0; i < 30; i++) {
            model.getBoard().getDeckResource().draw();
        }
        assertTrue(model.getBoard().getDeckGold().isEmpty());
        assertTrue(model.getBoard().getDeckResource().isEmpty());

        assertEquals(0, model.getCurrIndexPlayer());
        try {
            model.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(ShowDownGameModelState.class, model.getGameState());

    }

    @Test
    void endTurnBothDeckEmptyLastTurn() throws EmptyDeckException {
        utilityInitGame();
        utilitySkipSetupGame();

        for (int i = 0; i < 34; i++) {
            model.getBoard().getDeckGold().draw();
        }
        for (int i = 0; i < 30; i++) {
            model.getBoard().getDeckResource().draw();
        }
        assertTrue(model.getBoard().getDeckGold().isEmpty());
        assertTrue(model.getBoard().getDeckResource().isEmpty());

        for (int i = 0; i < 3; i++) {
            model.setNextPlayingPlayer();
        }
        assertEquals(3, model.getCurrIndexPlayer());
        try {
            model.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(LastTurnGameModelState.class, model.getGameState());

    }

    @Test
    void getCurrPlayer() {
        utilityInitGame();

        assertNull(model.getCurrPlayer());

        utilitySkipSetupGame();

        assertNotNull(model.getCurrPlayer());
    }

    @Test
    void chooseSecretObjective() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        utilityInitGame();

        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.chooseSecretObjective(username, 0));
            try {
                model.playStarter(username);
            } catch (IllegalStateOperationException | ObjectiveCardNotChosenException e) {
                fail("Exception should not have been thrown");
            }
        }

        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new RunningGameModelSate(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new ShowDownGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new LastTurnGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new EndGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }
    }

    @Test
    void playStarter() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(username));
        }

        utilityInitGame();

        for (String username : clients.keySet()) {
            assertThrowsExactly(ObjectiveCardNotChosenException.class, () -> model.playStarter(username));
        }

        for (String username : clients.keySet()) {
            try {
                model.chooseSecretObjective(username, 0);
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
            assertDoesNotThrow(() -> model.playStarter(username));
        }

        for (String userame : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

        model.setGameState(new ShowDownGameModelState(model));
        for (String userame : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

        model.setGameState(new LastTurnGameModelState(model));
        for (String userame : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

        model.setGameState(new EndGameModelState(model));
        for (String userame : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String userame : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

    }

    @Test
    void play() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
        }
        utilityInitGame();

        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
        }
        utilitySkipSetupGame();

        for (String username : clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        model.setNextPlayingPlayer();

        model.setGameState(new ShowDownGameModelState(model));

        for (String username : clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        model.setNextPlayingPlayer();

        model.setGameState(new LastTurnGameModelState(model));

        for (String username : clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        model.setNextPlayingPlayer();

        model.setGameState(new EndGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
        }
    }

    @Test
    void drawGold() throws EmptyDeckException {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawGold(username, 0));
        }
        utilityInitGame();

        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawGold(username, 0));
        }
        utilitySkipSetupGame();

        // draw resource change current player
        assertEquals(model.getCurrIndexPlayer(), 0);
        model.getCurrPlayer().setInGameState(new Placed());
        try {
            model.drawGold(model.getCurrPlayer().getUsername(), 0);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(model.getCurrIndexPlayer(), 1);

        // wrong index doesn't end the player turn
        model.getCurrPlayer().setInGameState(new Placed());
        try {
            model.drawGold(model.getCurrPlayer().getUsername(), 3);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(model.getCurrIndexPlayer(), 1);

        model.setGameState(new ShowDownGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertDoesNotThrow(() -> model.drawGold(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new LastTurnGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawGold(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new EndGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawGold(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new BlockedGameModelState(model, null));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawGold(model.getCurrPlayer().getUsername(), 0));
    }

    @Test
    void drawResource() throws EmptyDeckException {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawResource(username, 0));
        }
        utilityInitGame();

        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawResource(username, 0));
        }
        utilitySkipSetupGame();

        // draw resource change current player
        assertEquals(model.getCurrIndexPlayer(), 0);
        model.getCurrPlayer().setInGameState(new Placed());
        try {
            model.drawResource(model.getCurrPlayer().getUsername(), 0);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(model.getCurrIndexPlayer(), 1);

        // wrong index doesn't end the player turn
        model.getCurrPlayer().setInGameState(new Placed());
        try {
            model.drawResource(model.getCurrPlayer().getUsername(), 3);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertEquals(model.getCurrIndexPlayer(), 1);

        model.setGameState(new ShowDownGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertDoesNotThrow(() -> model.drawResource(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new LastTurnGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawResource(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new EndGameModelState(model));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawResource(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new BlockedGameModelState(model, null));
        model.getCurrPlayer().setInGameState(new Placed());
        assertThrowsExactly(IllegalStateOperationException.class,
                () -> model.drawResource(model.getCurrPlayer().getUsername(), 0));
    }

    @Test
    void setSelectCard() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }
        utilityInitGame();

        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
        }
        utilitySkipSetupGame();

        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
            assertDoesNotThrow(() -> model.setSelectCard(username, 1));
            assertDoesNotThrow(() -> model.setSelectCard(username, 2));
            assertThrowsExactly(WrongIndexSelectedCard.class, () -> model.setSelectCard(username, 3));
            assertThrowsExactly(WrongIndexSelectedCard.class, () -> model.setSelectCard(username, -1));
        }

        model.setGameState(new ShowDownGameModelState(model));
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
        }

        model.setGameState(new LastTurnGameModelState(model));
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
        }

        model.setGameState(new EndGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }
    }

    @Test
    void changeSide() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changeSide(username));
        }

        utilityInitGame();
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.changeSide(username));
        }

        utilitySkipSetupGame();
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.changeSide(username));
        }

        model.setGameState(new ShowDownGameModelState(model));
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.changeSide(username));
        }

        model.setGameState(new LastTurnGameModelState(model));
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.changeSide(username));
        }

        model.setGameState(new EndGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changeSide(username));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changeSide(username));
        }
    }

    @Test
    void changStarterSide() {
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }
        utilityInitGame();

        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.changStarterSide(username));
        }

        utilitySkipSetupGame();
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }

        model.setGameState(new ShowDownGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }

        model.setGameState(new LastTurnGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }

        model.setGameState(new EndGameModelState(model));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }

        model.setGameState(new BlockedGameModelState(model, null));
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.changStarterSide(username));
        }
    }

    @Test
    void endGame() {
        FakeGameModel model = new FakeGameModel();
        FakePlayer player;
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.endGame(null));
        utilityInitGame(model);
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.endGame(null));

        model.commonObjectives.add(new ObjectiveCard(2,
                new Count(Arrays.asList(Resources.MUSHROOM, Resources.MUSHROOM, Resources.MUSHROOM)), null, null));
        model.commonObjectives
                .add(new ObjectiveCard(2, new Count(Arrays.asList(Resources.INK, Resources.INK)), null, null));

        // last player reach 20 points the game must directly enter in lastTurn state
        model.setGameState(new RunningGameModelSate(model));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.endGame(null));

        // the first player reach 15 points and achieve his secret objective card
        // he achieves first common objective
        model.setNextPlayingPlayer();
        assertEquals(model.getCurrIndexPlayer(), 0);
        player = model.getCurrPlayer();
        player.setScore(15);
        player.setObjectiveCard(new ObjectiveCard(2,
                new Count(Arrays.asList(Resources.MUSHROOM, Resources.MUSHROOM, Resources.MUSHROOM)), null, null));
        player.play(new StarterCard(CardColor.NOCOLOR,
                new CardFront(0, Arrays.asList(Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY),
                        Collections.emptyMap(), null, null),
                new CardBack(
                        Arrays.asList(Resources.MUSHROOM, Resources.MUSHROOM, Resources.MUSHROOM, Resources.MUSHROOM),
                        null)));

        // the second player reach 0 points and achieve his secret objective card
        // he achieves second common objective
        model.setNextPlayingPlayer();
        assertEquals(model.getCurrIndexPlayer(), 1);
        player = model.getCurrPlayer();
        player.setScore(0);
        player.setObjectiveCard(
                new ObjectiveCard(2, new Count(Arrays.asList(Resources.SCROLL, Resources.SCROLL)), null, null));
        player.play(new StarterCard(CardColor.NOCOLOR,
                new CardFront(0, Arrays.asList(Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY),
                        Collections.emptyMap(), null, null),
                new CardBack(Arrays.asList(Resources.SCROLL, Resources.SCROLL, Resources.INK, Resources.INK), null)));

        // the third player reach 0 points and doesn't achieve any objective card
        model.setNextPlayingPlayer();
        assertEquals(model.getCurrIndexPlayer(), 2);
        player = model.getCurrPlayer();
        player.setScore(0);
        player.setObjectiveCard(new ObjectiveCard(3,
                new Count(Arrays.asList(Resources.FEATHER, Resources.INK, Resources.SCROLL)), null, null));
        player.play(new StarterCard(CardColor.NOCOLOR,
                new CardFront(0,
                        Arrays.asList(Resources.MUSHROOM, Resources.MUSHROOM, Resources.HIDDEN, Resources.EMPTY),
                        Collections.emptyMap(), null, null),
                new CardBack(Arrays.asList(Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY), null)));

        // the fourth player reach 20 points and achieve his secret objective card
        // he achieves also both common objective
        model.setNextPlayingPlayer();
        assertEquals(model.getCurrIndexPlayer(), 3);
        player = model.getCurrPlayer();
        player.setScore(20);
        player.setObjectiveCard(new ObjectiveCard(2,
                new Count(Arrays.asList(Resources.MUSHROOM, Resources.MUSHROOM, Resources.MUSHROOM)), null, null));
        player.play(new StarterCard(CardColor.NOCOLOR,
                new CardFront(0, Arrays.asList(Resources.EMPTY, Resources.EMPTY, Resources.EMPTY, Resources.EMPTY),
                        Collections.emptyMap(), null, null),
                new CardBack(Arrays.asList(Resources.MUSHROOM, Resources.INK, Resources.MUSHROOM, Resources.INK,
                        Resources.MUSHROOM), null)));

        try {
            model.endTurn();
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertInstanceOf(LastTurnGameModelState.class, model.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.endGame(null));

        for (int i = 0; i < 4; i++) {
            try {
                assertInstanceOf(LastTurnGameModelState.class, model.getGameState());
                model.endTurn();
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
        }
        assertInstanceOf(EndGameModelState.class, model.getGameState());

        List<String> turnPlayer = model.getTurnPlayer();
        assertEquals(model.getPlayers().get(turnPlayer.get(0)).getScore(), 19);
        assertEquals(model.getPlayers().get(turnPlayer.get(1)).getScore(), 4);
        assertEquals(model.getPlayers().get(turnPlayer.get(2)).getScore(), 0);
        assertEquals(model.getPlayers().get(turnPlayer.get(3)).getScore(), 26);

        model.setGameState(new BlockedGameModelState(model, null));
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.endGame(null));
    }

    @Test
    void executeDisconnectPlayerSetupState() {
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.disconnectPlayer(username));
        }
        utilityInitGame();

        Player player = model.getPlayers().get("Players1");

        assertTrue(model.getPlayerConnection().get("Players1"));
        assertNull(player.getObjectiveCard());
        assertNull(player.getPlayArea().getPlacedCards().get(new Point(0, 0)));
        // if first player disconnect
        try {
            model.disconnectPlayer("Players1");
        } catch (LastPlayerRemainedException ignored) {

        }

        assertFalse(model.getPlayerConnection().get("Players1"));
        assertNotNull(player.getObjectiveCard());
        assertNotNull(player.getPlayArea().getPlacedCards().get(new Point(0, 0)));

    }

    @Test
    void executeDisconnectPlayerAfterSetupState() {
        for (String username : clients.keySet()) {
            assertDoesNotThrow(() -> model.disconnectPlayer(username));
        }
        utilityInitGame();
        utilitySkipSetupGame();

        // if the player in turn disconnect
        assertEquals(0, model.getCurrIndexPlayer());
        try {
            model.disconnectPlayer("Players1");
        } catch (LastPlayerRemainedException ignored) {

        }
        assertEquals(1, model.getCurrIndexPlayer());

        // if the player not in turn disconnect
        try {
            model.disconnectPlayer("Players4");
        } catch (LastPlayerRemainedException ignored) {
        }
        assertEquals(1, model.getCurrIndexPlayer());
    }

    @Test
    void blockedGameModelStateTest() {
        utilityInitGame();
        utilitySkipSetupGame();

        assertEquals(0, model.getCurrIndexPlayer());

        assertDoesNotThrow(() -> model.disconnectPlayer("Players1"));
        assertDoesNotThrow(() -> model.disconnectPlayer("Players2"));
        assertThrowsExactly(LastPlayerRemainedException.class, () -> model.disconnectPlayer("Players3"));
        assertInstanceOf(BlockedGameModelState.class, model.getGameState());
    }

    public static class FakeGameModel extends GameModel {
        public FakeGameModel() {
            super(new Object(), 0);
        }

        public void initGame(Map<String, VirtualClient> clients, Object lock) {
            players = new HashMap<>();
            super.clients = new HashMap<>();
            for (String username : clients.keySet()) {
                Player player = new FakePlayer(pawnAssignment(), username, getBoard());
                players.put(username, player);
            }
            for (String username : clients.keySet()) {
                playerConnection.put(username, true);
            }
            setGameState(new SetupGameModelState(this));
        }

        public FakePlayer getCurrPlayer() {
            if (turnPlayer != null) {
                return (FakePlayer) players.get(turnPlayer.get(getCurrIndexPlayer()));
            }
            return null;
        }

        public List<String> getTurnPlayer() {
            return turnPlayer;
        }
    }

    public static class FakePlayer extends Player {

        public FakePlayer(PawnColor pawnColor, String username, Board board) {
            super(pawnColor, username, board);
        }

        public void setScore(int score) {
            this.score = score;
        }

        @Override
        public void setObjectiveCard(ObjectiveCard card) {
            super.setObjectiveCard(card);
        }

        public void play(PlayableCard card) {
            getPlayArea().placeStarter(card);
        }
    }

    public static class FakeClient implements VirtualClient {
        @Override
        public boolean isReady() throws RemoteException {
            return false;
        }

        @Override
        public void setGameID(int gameID) throws RemoteException {

        }

        @Override
        public void sendCommand(ClientQueueObject obj) throws RemoteException {

        }

        @Override
        public void setController(IController controller) throws RemoteException {

        }

        @Override
        public void setGameController(IGameController gameController) throws RemoteException {

        }
    }

    private void utilityInitGame() {
        try {
            model.initGame(clients, lock);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void utilityInitGame(GameModel model) {
        try {
            model.initGame(clients, lock);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
    }

    private void utilitySkipSetupGame() {
        try {
            for (String username : clients.keySet()) {
                model.chooseSecretObjective(username, 0);
                model.playStarter(username);
            }
        } catch (IllegalStateOperationException | ObjectiveCardNotChosenException e) {
            fail("Exception should not have been thrown");
        }
    }
}