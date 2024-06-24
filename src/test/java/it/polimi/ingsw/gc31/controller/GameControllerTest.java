package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ChatMessage;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.HeartBeatObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.QuitGameObj;
import it.polimi.ingsw.gc31.model.gameModel.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.mockito.Mockito;

import java.rmi.RemoteException;
import java.awt.*;

import static org.junit.jupiter.api.Assertions.*;

//FIXME modificato in "public" il metodo setGameState

public class GameControllerTest {
    private GameController gameController1;
    static VirtualClient mockClient;

//    public static class FakeGameModel extends GameModel {
//        public FakeGameModel(){
//            super();
//        }
//
//        @Override
//        public void setGameState(GameModelState gameState){
//            this.gameState = gameState;
//        }
//
//        @Override
//        public void drawResource(String username, int index){
//            gameState.drawResource(this, username, index);
//            listeners.values().forEach(listener -> listener.notifyResourcedDeckListener(this));
//            listeners.get(username).notifyHandListener(this);
//        }
//    }

//    public static class FakeGameController extends GameController {
//        private FakeGameModel fakeModel;
//        /**
//         * Constructor for the GameController class.
//         * It initializes the game model, players, clientList, and game states.
//         *
//         * @param username         the username of the player.
//         * @param client           the client of the player.
//         * @param maxNumberPlayers the maximum number of players.
//         * @param idGame           the id of the game.
//         */
//        public FakeGameController(String username, VirtualClient client, int maxNumberPlayers, int idGame) throws RemoteException {
//            super(username, client, maxNumberPlayers, idGame);
//            fakeModel = (FakeGameModel) super.getModel();
//        }
//
//        @Override
//        public FakeGameModel getModel(){
//            return fakeModel;
//        }
//    }
//
//    private Method publicates(GameModel model){
//        Class<?> clazz = model.getClass();
//        try{
//            Method method = clazz.getMethod("setGameState");
//            method.setAccessible(true);
//            return method;
//        } catch (NoSuchMethodException e) {
//            e.printStackTrace();
//            return  null;
//        }
//    }

    /**
     * Setup executed before each test method
     * @throws RemoteException
     */
    @BeforeEach
    public void setUp() throws RemoteException {
        mockClient = Mockito.mock(VirtualClient.class);
        gameController1 = new GameController("player1", mockClient, 4, 0);
    }

    /**
     * Tests the joinGame method
     */
    @Test
    public void joinGameTest() {
        //check if the maximum number of player is correct
        assertEquals(gameController1.getMaxNumberPlayers(), 4);
        assertEquals(gameController1.getCurrentNumberPlayers(), 1);
        //create a new client that tries to join the game and checks if it is added correctly
        assertDoesNotThrow( () ->gameController1.joinGame("player2", mockClient));
        assertEquals(gameController1.getCurrentNumberPlayers(), 2);

        //checks if the controller adds correctly the player to the maximum number possible
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertEquals(gameController1.getCurrentNumberPlayers(), 3);
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));
        assertEquals(gameController1.getCurrentNumberPlayers(), 4);

        //the case in which the clients exceed the max number players for the game
        // is managed in the method of the controller, not the gameController for this reason
        // it is not tested here
    }

    /**
     * Tests the quiGame method
     */
    @Test
    public void quitGameTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow(() -> gameController1.quitGame("player3"), "The method does not throw any exceptions");

        //checks if the method can remove correctly a player that
        // asked to quit from client
        assertEquals(gameController1.getCurrentNumberPlayers(), 2, "The number of the player in the game is diminished by one");
    }

    /**
     * The method checks if a player can change the value of the ready attribute correctly
     */
    @Test
    public void setReadyStatusTest() {
        //Populate the game
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));

        //Check if the clients are added to the game with the value of the ready attribute equals to false
        assertEquals(gameController1.readyStatus.get("player1"), false);
        assertEquals(gameController1.readyStatus.get("player2"), false);
        assertEquals(gameController1.readyStatus.get("player3"), false);

        //Check if the setReadyStatus works properly
        assertDoesNotThrow( () -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(true, "player3"));
        assertEquals(gameController1.readyStatus.get("player1"), true);
        assertEquals(gameController1.readyStatus.get("player3"), true);

        assertDoesNotThrow( () -> gameController1.setReadyStatus(false, "player1"));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(false, "player2"));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(false, "player3"));
        assertEquals(gameController1.readyStatus.get("player1"), false);
        assertEquals(gameController1.readyStatus.get("player2"), false);
        assertEquals(gameController1.readyStatus.get("player3"), false);

        //Check if the value of the ready attributes are still correct after multiple modifications
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow( () -> gameController1.setReadyStatus(true, "player3"));
        assertEquals(gameController1.readyStatus.get("player1"), true);
        assertEquals(gameController1.readyStatus.get("player2"), true);
        assertEquals(gameController1.readyStatus.get("player3"), true);

        //FIXME the method check ready can throw an IllegalStateOperationException... how?
    }

    /**
     * Tests the checkReady method
     */
    @Test
    public void checkReadyTest() {
        //Populate the game
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the client status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //FIXME why this method throw an RuntimeException?
        gameController1.checkReady();
//        assertThrows(RuntimeException.class, () -> gameController1.checkReady());
    }

    /**
     * Tests the sendChatMessage method
     */
    @Test
    public void sendChatMessageTest() {
        //Populate the game
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Check if the message is sent correctly
        assertDoesNotThrow(() -> gameController1.sendChatMessage(new NewChatMessage("player1", "Test")));
    }

    /**
     * Tests the selectCard method
     */
    @Test
    public void selectCardTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the correct flow of instruction is executed if the method is invoked properly
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player2", 1));
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player1", 2));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player4", 0));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player1", 1));


        //Checks if the WrongIndexSelectedCard exception is caught when necessary
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player2", 10));
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player1", -3));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player4", 4));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player1", -1));

        //Checks if the IllegalStateOperationException is caught when necessary
        gameController1.getModel().setGameState(new CreationGameModelState());
        assertDoesNotThrow( () -> gameController1.selectCard("player1", 2));
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.selectCard("player3", 1));
    }

    /**
     * Tests the changeSide method
     */
    @Test
    public void changeSideTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instruction are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        gameController1.changeSide("player1");
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        gameController1.changeSide("player2");
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        gameController1.changeSide("player3");
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        gameController1.changeSide("player4");

        //Checks if the catch branch is executed when necessary, if the game state is incorrect
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        gameController1.changeSide("player1");
        gameController1.getModel().setGameState(new CreationGameModelState());
        gameController1.changeSide("player2");


    }

    /**
     * Tests the drawGold method
     */
    @Test
    public void drawGoldTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instructions are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        gameController1.drawGold("player1", 1);
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        gameController1.drawGold("player2", 2);
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        gameController1.drawGold("player3", 0);

        //Checks if the IllegalStateOperationException branch is executed when needed
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        gameController1.drawGold("player1", 0);
        gameController1.getModel().setGameState(new CreationGameModelState());
        gameController1.drawGold("player4", 2);
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        gameController1.drawGold("player2", 1);
    }

    /**
     * Tests the drawResource method
     */
    @Test
    public void drawResourceTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instructions are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.drawResource("player1", 1));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.drawResource("player2", 2));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.drawResource("player3", 0));

        //Checks if the IllegalStateOperationException branch is executed when needed
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.drawResource("player1", 0));
        gameController1.getModel().setGameState(new CreationGameModelState());
        assertDoesNotThrow( () -> gameController1.drawResource("player4", 2));
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.drawResource("player2", 1));
    }

    /**
     * Tests the chooseSecretObjective method
     */
    @Test
    public void chooseSecretObjectiveTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instructions are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player2", 1));

        //Checks if the IllegalStateOperationException branch is executed when needed
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player1", 1));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player2", 1));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player3", 0));
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player1", 0));
        gameController1.getModel().setGameState(new CreationGameModelState());
        assertDoesNotThrow( () -> gameController1.chooseSecretObjective("player4", 1));
    }


    /**
     * Tests the playStarter method
     */
    @Test
    public void playStarterTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the IllegalStateOperationException is caught and the associated branch executed
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        gameController1.playStarter("player1");
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        gameController1.playStarter("player2");
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        gameController1.playStarter("player3");
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        gameController1.playStarter("player4");
        gameController1.getModel().setGameState(new CreationGameModelState());
        gameController1.playStarter("player1");

        //Checks if the correct instructions are executed when the method is invoked correctly
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        gameController1.playStarter("player4");
    }

    /**
     * Tests the play method
     */
    @Test
    public void playTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instructions are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.play("player1", new Point(1, 1)));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.play("player2", new Point(1, 1)));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.play("player3", new Point(1, 1)));

        //Checks if the IllegalStateOperationException branch is executed when needed
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.play("player1", new Point(-1, -1)));
        gameController1.getModel().setGameState(new CreationGameModelState());
        assertDoesNotThrow( () -> gameController1.play("player4", new Point(-1, -1)));
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.play("player2", new Point(-1, -1)));
    }

    /**
     * Tests the changeStarterSide method
     */
    @Test
    public void changeStarterSideTest() {
        //Populate the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player3", mockClient));
        assertDoesNotThrow( () -> gameController1.joinGame("player4", mockClient));

        //Set all the player status to true
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player1"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player2"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player3"));
        assertDoesNotThrow(() -> gameController1.setReadyStatus(true, "player4"));

        //Checks if the instructions are executed correctly if the method is invoked properly
        gameController1.getModel().setGameState(new SetupGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player2"));

        //Checks if the IllegalStateOperationException branch is executed when needed
        gameController1.getModel().setGameState(new ShowDownGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player1"));
        gameController1.getModel().setGameState(new RunningGameModelSate(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player2"));
        gameController1.getModel().setGameState(new LastTurnGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player3"));
        gameController1.getModel().setGameState(new EndGameModelState(gameController1.getModel()));
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player1"));
        gameController1.getModel().setGameState(new CreationGameModelState());
        assertDoesNotThrow( () -> gameController1.changeStarterSide("player4"));
    }

    /**
     * Tests the executor method
     */
    @Test
    public void executorTest(){
        //Send a new command to the list
        assertDoesNotThrow( () -> gameController1.sendCommand(new HeartBeatObj("player1")));

        //Creates a new player that enters the lobby
        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));

        //Checks the execution of the method with a newly entered player that
        // sends a new object
        assertDoesNotThrow( () -> gameController1.sendCommand(new QuitGameObj("player2")));
        assertDoesNotThrow( () -> gameController1.sendCommand(new ChatMessage("player1", "Test")));
    }

//Useless because the executor remove continuously object from the callsList
//    @Test
//    public void sendCommandTest(){
//        //Expected 0 obj at the start of the game
//        assertEquals(0, gameController1.callsList.size());
//
//        //Adding an object
//        assertDoesNotThrow( () -> gameController1.sendCommand(new HeartBeatObj("player1")));
//
//        //Expecting an obj in the list
//        assertEquals(1, gameController1.callsList.size());
//
//        assertDoesNotThrow( () -> gameController1.joinGame("player2", mockClient));
//        assertDoesNotThrow( () -> gameController1.sendCommand(new QuitGameObj("player2")));
//        assertDoesNotThrow( () -> gameController1.sendCommand(new FlipStarterCardObj("player2")));
//        assertEquals(3, gameController1.callsList.size());
//    }

}
