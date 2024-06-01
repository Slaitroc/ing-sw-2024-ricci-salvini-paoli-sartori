package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class GameModelTest {
    GameModel model;
    LinkedHashMap<String, VirtualClient> clients;
    @BeforeEach
    public void setUp() {
        model = new GameModel();
        clients = new LinkedHashMap<>();

        clients.put("Players1", new FakeClient());
        clients.put("Players2", new FakeClient());
        clients.put("Players3", new FakeClient());
        clients.put("Players4", new FakeClient());
    }

    @Test
    void initGame() {
        assertInstanceOf(CreationGameModelState.class, model.getGameState());
        assertDoesNotThrow(() -> model.initGame(clients));

        assertInstanceOf(SetupGameModelState.class, model.getGameState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients));

        model.setGameState(new RunningGameModelSate());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients));

        model.setGameState(new ShowDownGameModelState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients));

        model.setGameState(new LastTurnGameModelState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients));

        model.setGameState(new EndGameModelState());
        assertThrowsExactly(IllegalStateOperationException.class, () -> model.initGame(clients));
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
    void endTurn() {
        // TODO da fare??
    }

    @Test
    void getPlayers() {
        Map<String, Player> players = model.getPlayers();

        assertNotNull(players);

        assertTrue(clients.keySet().containsAll(players.keySet()));
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

        model.setGameState(new RunningGameModelSate());
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new ShowDownGameModelState());
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new LastTurnGameModelState());
        for (String username : clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.chooseSecretObjective(username, 0));
        }

        model.setGameState(new LastTurnGameModelState());
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

        for (String username: clients.keySet()) {
            assertThrowsExactly(ObjectiveCardNotChosenException.class, () -> model.playStarter(username));
        }

        for (String username: clients.keySet()) {
            try {
                model.chooseSecretObjective(username, 0);
            } catch (IllegalStateOperationException e) {
                fail("Exception should not have been thrown");
            }
            assertDoesNotThrow(() -> model.playStarter(username));
        }

        for (String userame: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.playStarter(userame));
        }

    }

    @Test
    void play() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1,1)));
        }
        utilityInitGame();

        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1,1)));
        }
        utilitySkipSetupGame();

        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        try {
            model.drawGold(model.getCurrPlayer().getUsername(), 0);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        model.setGameState(new ShowDownGameModelState());

        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        try {
            model.drawGold(model.getCurrPlayer().getUsername(), 0);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        model.setGameState(new LastTurnGameModelState());

        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        assertDoesNotThrow(() -> model.play(model.getCurrPlayer().getUsername(), new Point(1, 1)));
        try {
            model.drawGold(model.getCurrPlayer().getUsername(), 0);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
        }
    }

    @Test
    void drawGold() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawGold(username, 0));
        }
        utilityInitGame();

        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawGold(username, 0));
        }
        utilitySkipSetupGame();

        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        // wrong index doesn't end the player turn
        try {
            int beforeIndexPlayer = model.getCurrIndexPlayer();
            model.drawGold(model.getCurrPlayer().getUsername(), 3);
            assertEquals(model.getCurrIndexPlayer(), beforeIndexPlayer);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        // drawGold change the player in turn
        int beforeIndexPlayer = model.getCurrIndexPlayer();
        assertDoesNotThrow(() -> model.drawGold(model.getCurrPlayer().getUsername(), 0));
        assertNotEquals(model.getCurrIndexPlayer(), beforeIndexPlayer);

        model.setGameState(new ShowDownGameModelState());
        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertDoesNotThrow(() -> model.drawGold(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new LastTurnGameModelState());
        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertDoesNotThrow(() -> model.drawGold(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawGold(username, 0));
        }
    }

    @Test
    void drawResource() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawResource(username, 0));
        }
        utilityInitGame();

        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawResource(username, 0));
        }
        utilitySkipSetupGame();

        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        // wrong index doesn't end the player turn
        try {
            int beforeIndexPlayer = model.getCurrIndexPlayer();
            model.drawResource(model.getCurrPlayer().getUsername(), 3);
            assertEquals(model.getCurrIndexPlayer(), beforeIndexPlayer);
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }

        // drawResource(); change the player in turn
        int beforeIndexPlayer = model.getCurrIndexPlayer();
        assertDoesNotThrow(() -> model.drawResource(model.getCurrPlayer().getUsername(), 0));
        assertNotEquals(model.getCurrIndexPlayer(), beforeIndexPlayer);

        model.setGameState(new ShowDownGameModelState());
        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertDoesNotThrow(() -> model.drawResource(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new LastTurnGameModelState());
        for (String username: clients.keySet()) {
            if (!username.equals(model.getCurrPlayer().getUsername())) {
                assertThrowsExactly(IllegalStateOperationException.class, () -> model.play(username, new Point(1, 1)));
            }
        }
        try {
            model.play(model.getCurrPlayer().getUsername(), new Point(1,1));
        } catch (IllegalStateOperationException e) {
            fail("Exception should not have been thrown");
        }
        assertDoesNotThrow(() -> model.drawResource(model.getCurrPlayer().getUsername(), 0));

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.drawResource(username, 0));
        }
    }

    @Test
    void setSelectCard() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }
        utilityInitGame();

        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }
        utilitySkipSetupGame();

        for (String username: clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
            assertDoesNotThrow(() -> model.setSelectCard(username, 1));
            assertDoesNotThrow(() -> model.setSelectCard(username, 2));
            assertThrowsExactly(WrongIndexSelectedCard.class, () -> model.setSelectCard(username, 3));
            assertThrowsExactly(WrongIndexSelectedCard.class, () -> model.setSelectCard(username, -1));
        }

        model.setGameState(new ShowDownGameModelState());
        for (String username: clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
        }

        model.setGameState(new LastTurnGameModelState());
        for (String username: clients.keySet()) {
            assertDoesNotThrow(() -> model.setSelectCard(username, 0));
        }

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () -> model.setSelectCard(username, 0));
        }
    }

    @Test
    void changeSide() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changeSide(username));
        }

        utilityInitGame();
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changeSide(username));
        }

        utilitySkipSetupGame();
        for (String username: clients.keySet()) {
            assertDoesNotThrow(() ->model.changeSide(username));
        }

        model.setGameState(new ShowDownGameModelState());
        for (String username: clients.keySet()) {
            assertDoesNotThrow(() ->model.changeSide(username));
        }

        model.setGameState(new LastTurnGameModelState());
        for (String username: clients.keySet()) {
            assertDoesNotThrow(() ->model.changeSide(username));
        }

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changeSide(username));
        }
    }

    @Test
    void changStarterSide() {
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changStarterSide(username));
        }
        utilityInitGame();

        for (String username: clients.keySet()) {
            assertDoesNotThrow(() ->model.changStarterSide(username));
        }

        utilitySkipSetupGame();
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changStarterSide(username));
        }

        model.setGameState(new ShowDownGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changStarterSide(username));
        }

        model.setGameState(new LastTurnGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changStarterSide(username));
        }

        model.setGameState(new EndGameModelState());
        for (String username: clients.keySet()) {
            assertThrowsExactly(IllegalStateOperationException.class, () ->model.changStarterSide(username));
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
            model.initGame(clients);
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
            throw new RuntimeException(e);
        }
    }
}