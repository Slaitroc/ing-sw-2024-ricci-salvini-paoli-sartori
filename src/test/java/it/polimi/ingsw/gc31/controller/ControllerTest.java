package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import javafx.util.Pair;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.rmi.RemoteException;

import static org.junit.jupiter.api.Assertions.*;

class ControllerTest {
    private Controller controller;

    @BeforeEach
    public void setUp() {
        this.controller = Controller.getController();
        controller.newConnections.clear();
        controller.tempClients.clear();
        controller.disconnected.clear();
        controller.clientsHeartBeat.clear();
        controller.gameControlList.clear();
        controller.nicknames.clear();
    }

    /**
     * Tests the createGame method
     */
    @Test
    void createGameTest() {
        // Creates a new client and inserts the client in the newConnections and
        // tempClients map
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);

        // Checks if the correct instructions are executed when the maxNumberPlayer
        // parameter is out of correct bounds
        assertDoesNotThrow(() -> controller.createGame("username1", 1));
        assertDoesNotThrow(() -> controller.createGame("username1", 5));

        // At first the gameControlList is empty, and so the tempClients map
        assertEquals(0, controller.gameControlList.size());
        assertEquals(1, controller.tempClients.size());

        // If the maxNumberPlayer are in the correct bound a new gameController
        // is created and the client is removed from the tempClients map
        assertDoesNotThrow(() -> controller.createGame("username1", 2));
        assertEquals(1, controller.gameControlList.size());
        assertEquals(0, controller.tempClients.size());
    }

    /**
     * Tests the joinGame method
     */
    @Test
    void joinGameTest() {
        // Create a new client and add it to the maps
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);

        // Create a new game with the client in the maps
        assertTrue(() -> {
            try {
                controller.createGame("username1", 2);
                return true;
            } catch (RemoteException e) {
                return false;
            }
        });

        // Create a new client that tries to connect to a game
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);
        controller.tempClients.put("username2", mockClient2);

        // If the client tries to enter a game with the wrong gameId the Controller only
        // sends
        // to him a GameDoesNotExistObj
        assertDoesNotThrow(() -> controller.joinGame("username2", 1));

        // If the client tries to enter a game with the correct gameId, and the game is
        // not full, the client
        // can enter the game. The client is removed from the tempClients map
        assertDoesNotThrow(() -> controller.joinGame("username2", 0));
        assertEquals(0, controller.tempClients.size());

        // Creates a new client that tries to enter a game that is already full
        VirtualClient mockClient3 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(300, mockClient3);
        controller.tempClients.put("username3", mockClient3);
        assertEquals(1, controller.tempClients.size());

        // If the client tries to enter to a game that is already full the Controller
        // only sends
        // him a GameIsFullObj
        assertDoesNotThrow(() -> controller.joinGame("username3", 0));
        assertEquals(1, controller.tempClients.size());

        // Checks if the size of the newConnections map is left unchanged
        assertEquals(3, controller.newConnections.size());
    }

    /**
     * Tests the quitGame method, which is invoked only by the GameController
     */
    @Test
    void quitGameTest() {
        // Create a new player
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);

        // Create a game with the mockClient1
        assertDoesNotThrow(() -> controller.createGame("username1", 2));
        assertEquals(0, controller.tempClients.size());

        // Quit the game for the mockClient1. The client is removed from the game and is
        // added
        // to the tempClients map
        assertDoesNotThrow(() -> controller.quitGame("username1", mockClient1));
        assertEquals(1, controller.tempClients.size());
    }

    /**
     * Tests the getGameList method
     */
    @Test
    void getGameListTest() {
        // The controller sends to the client a ShowGamesObj,
        // whether the gameControlList is empty or not
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);

        // Checks the instructions executed if the gameControlList is empty
        assertDoesNotThrow(() -> controller.getGameList(13));

        // Checks the instructions executed if the gameControlList is not empty
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);
        controller.tempClients.put("username2", mockClient2);

        assertDoesNotThrow(() -> controller.createGame("username2", 2));
        assertDoesNotThrow(() -> controller.getGameList(13));
    }

    /**
     * Tests the generateToken method, invoked every time a VirtualClient is created
     */
    @Test
    void generateTokenTest() {
        // At the start the server doesn't have any client in the newConnections map
        assertEquals(0, controller.newConnections.size());

        // Simulate the token generation of new clients, the value of the tokens should
        // be between
        // 0 and 999. After every token generation the size of the newConnections map
        // should be
        // the old one +1
        assertEquals(500, controller.generateToken(null), 500);
        assertEquals(1, controller.newConnections.size());
        assertEquals(500, controller.generateToken(null), 500);
        assertEquals(2, controller.newConnections.size());
        assertEquals(500, controller.generateToken(null), 500);
        assertEquals(3, controller.newConnections.size());
        assertEquals(500, controller.generateToken(null), 500);
        assertEquals(4, controller.newConnections.size());
        assertEquals(500, controller.generateToken(null), 500);
        assertEquals(5, controller.newConnections.size());

        // Clear the newConnections map before the next test
        controller.newConnections.clear();

        // Populate the map with every possible token value between 0 and 998
        int i = 0;
        while (i < 999) {
            controller.newConnections.put(i, null);
            i++;
            // System.out.println("Inserito il token "+ i +" all'interno della mappa
            // newConnections");
        }

        // Checks if the correct (only one possible) value of the token is generated.
        // There can't be any
        // duplicated key value in the map
        assertEquals(999, controller.newConnections.size());
        assertEquals(999, controller.generateToken(null));
    }

    /**
     * Tests the sendToken method, invoked every time a client tries to connect to
     * the server. The corresponding token was generated previously by the
     * generate token method
     */
    @Test
    void sendTokenTest() {
        // Create 4 fake clients for testing
        /*
         * VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
         * controller.newConnections.put(1, mockClient1);
         * VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
         * controller.newConnections.put(100, mockClient2);
         * VirtualClient mockClient3 = Mockito.mock(VirtualClient.class);
         * controller.newConnections.put(500, mockClient3);
         * VirtualClient mockClient4 = Mockito.mock(VirtualClient.class);
         * controller.newConnections.put(999, mockClient4);
         *
         * //Checks if the correct flow of instructions is executed during the process
         * to send
         * //the generated token to the correct client
         * assertDoesNotThrow( () -> controller.sendToken(mockClient1));
         * assertDoesNotThrow( () -> controller.sendToken(mockClient3));
         * assertDoesNotThrow( () -> controller.sendToken(mockClient4));
         *
         * //Checks if the correct flow of instructions is executed if the client is not
         * //in the map anymore
         * controller.newConnections.remove(500);
         * assertThrows(NullPointerException.class, () ->
         * controller.sendToken(mockClient3));
         */
    }

    /**
     * Tests the connect method
     */
    @Test
    void connectTest() {
        // NOTE: xx -> tempToken, xxx -> token
        // Checks if the case where a player disconnected tries to reconnect.
        // mockClient1 is the old client while mockClient2 is the new client
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.disconnected.put(100, new Pair<>("username1", 0));
        controller.newConnections.put(20, mockClient2);

        // The mockClient1 (token = 100) disconnected, the mockClient2 (still token =
        // 100) is the
        // new VirtualClient of the user. mockClient2 tries to reconnect so the first if
        // statement
        // should be executed. The value associated to the token = 100 should be
        // replaced with the new VirtualClient and
        // should be returned the value true
        assertFalse(() -> {
            try {
                return controller.connect(mockClient2, "username1", 20, 100);
            } catch (RemoteException e) {
                return true;
            }
        });
        assertEquals(mockClient2, controller.newConnections.get(100));
        assertEquals(1, controller.newConnections.size());
        assertEquals(1, controller.clientsHeartBeat.size());

        // Clear the maps
        controller.newConnections.clear();
        controller.disconnected.clear();
        controller.tempClients.clear();
        controller.clientsHeartBeat.clear();

        // If the token is not contained in the disconnected map it means that
        // is the first time the client tries to connect to the server
        controller.newConnections.put(10, mockClient1);
        controller.newConnections.put(20, mockClient2);
        assertTrue(() -> {
            try {
                return controller.connect(mockClient1, "username1", 10, -1);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(1, controller.clientsHeartBeat.size());
        assertNull(controller.newConnections.get(10));
        assertEquals(2, controller.newConnections.size());
        assertEquals(1, controller.tempClients.size());

        assertTrue(() -> {
            try {
                return controller.connect(mockClient2, "username2", 20, -1);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(2, controller.clientsHeartBeat.size());
        assertEquals(2, controller.tempClients.size());
        assertEquals(0, controller.disconnected.size());

        controller.newConnections.clear();
        controller.tempClients.clear();
        controller.clientsHeartBeat.clear();
        controller.nicknames.clear();
        controller.disconnected.clear();

        // If the token is not contained but the username of the new client is already
        // contained in the nicknames list
        // the else branch should be executed and false should be returned, without
        // adding the client
        // to any map
        controller.newConnections.put(10, mockClient1);
        assertDoesNotThrow(() -> controller.connect(mockClient1, "username1", 10, -1));

        // Create 2 new clients. The first is the original client that connects for the
        // first time
        // so all the map are updated with the new connection.
        // Then the user disconnect and on reconnection chooses a username already taken
        // (by mockClient1)
        VirtualClient mockClient3 = Mockito.mock(VirtualClient.class);
        VirtualClient mockClientReconnect3 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(300, mockClient3);
        controller.tempClients.put("username3", mockClient3);
        controller.nicknames.add("username3");
        controller.clientsHeartBeat.put(mockClient3, (long) 0);
        controller.newConnections.put(30, mockClientReconnect3);
        assertFalse(() -> {
            try {
                return controller.connect(mockClientReconnect3, "username1", 30, 300);
            } catch (RemoteException e) {
                return true;
            }
        });
        assertEquals(2, controller.clientsHeartBeat.size());
        assertEquals(2, controller.tempClients.size());
        assertEquals(0, controller.disconnected.size());

        controller.newConnections.clear();
        controller.tempClients.clear();
        controller.clientsHeartBeat.clear();
        controller.nicknames.clear();
        controller.disconnected.clear();

        // If the client disconnected during a game, reconnects but doesn't want to
        // enter
        // the same game the last else branch is executed. This time is executed the
        // case where the
        // username is a valid one
        VirtualClient mockClient4 = Mockito.mock(VirtualClient.class);
        VirtualClient mockClientReconnect4 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(400, mockClient4);
        controller.tempClients.put("username4", mockClient4);
        controller.nicknames.add("username4");
        controller.clientsHeartBeat.put(mockClient4, (long) 0);
        controller.newConnections.put(40, mockClientReconnect4);
        assertTrue(() -> {
            try {
                return controller.connect(mockClientReconnect4, "username40", 40, 300);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(2, controller.clientsHeartBeat.size());
        assertEquals(2, controller.tempClients.size());
        assertEquals(0, controller.disconnected.size());

        controller.newConnections.clear();
        controller.tempClients.clear();
        controller.clientsHeartBeat.clear();
        controller.nicknames.clear();
        controller.disconnected.clear();

        // Check the cases where the username sent is null
        VirtualClient mockClient5 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(50, mockClient5);

        assertFalse(assertDoesNotThrow(() -> controller.connect(mockClient5, null, 50, -1)));
    }

    /**
     * Tests the rejoin method
     */
    @Test
    void rejoinTest() {
        // Create a client and connect it to the server, then create a game with that
        // client
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);
        controller.nicknames.add("username1");
        controller.clientsHeartBeat.put(mockClient1, (long) 0);
        assertDoesNotThrow(() -> controller.createGame("username1", 2));
        // assertDoesNotThrow( () -> controller.gameControlList.add(new
        // GameController("username1", mockClient1, 2, 0)));
        // controller.disconnected.put(100, new Pair<>("username1", 0));

        // Create a second client that will enter the game and then disconnect
        VirtualClient mockClientDisconnect = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(150, mockClientDisconnect);
        controller.tempClients.put("usernameDisconnect", mockClientDisconnect);
        controller.nicknames.add("usernameDisconnect");
        controller.clientsHeartBeat.put(mockClientDisconnect, (long) 0);
        assertDoesNotThrow(() -> controller.joinGame("usernameDisconnect", 0));

        // Start the game
        assertDoesNotThrow(() -> controller.gameControlList.get(0).setReadyStatus(true, "username1"));
        assertDoesNotThrow(() -> controller.gameControlList.get(0).setReadyStatus(true, "usernameDisconnect"));

        // Disconnect the mockClientDisconnect
        controller.gameControlList.get(0).disconnectPlayer("usernameDisconnect");
        controller.disconnect("usernameDisconnect", 0, 150);

        // Create the new client representing the new VirtualClient for
        // mockClientDisconnect
        VirtualClient mockClientRejoin = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(666, mockClientRejoin);
        assertDoesNotThrow(() -> controller.connect(mockClientRejoin, "usernameRejoin", 666, 150));

        // If the token of the client was in the disconnected map the user can choose to
        // rejoin or not
        // If the boolean value of the rejoin method is true the player wants to rejoin
        // and if the branch should be executed
        assertDoesNotThrow(() -> controller.rejoin(666, 150, true));

        // The VirtualClient associated with the username of the previously disconnected
        // client (the username
        // doesn't change after reconnection) is updated with the new one
        assertEquals(mockClientRejoin, controller.gameControlList.get(0).clientList.get("usernameDisconnect"));

        controller.newConnections.clear();
        controller.disconnected.clear();
        controller.nicknames.clear();
        controller.clientsHeartBeat.clear();

        // Create a new client with token = 200 that was previously connected and its
        // new client
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        VirtualClient mockClientReconnect1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);
        controller.nicknames.add("username2");
        controller.clientsHeartBeat.put(mockClient2, (long) 0);
        controller.newConnections.put(50, mockClientReconnect1);
        controller.disconnected.put(200, new Pair<>("username2", 0));
        assertDoesNotThrow(() -> controller.connect(mockClientReconnect1, "username2", 50, 200));

        // If the method rejoin is invoked with the boolean parameter false the else
        // branch is executed
        // and the method tries to connect the client as if it was the first time
        assertDoesNotThrow(() -> controller.rejoin(50, 200, false));
        // The rejoin add the new client to the nicknames list, to the tempClients map
        // and to the clientsHeartBeat map
        assertEquals(1, controller.newConnections.size());
        assertEquals(0, controller.disconnected.size());
        assertEquals(1, controller.nicknames.size());
        assertEquals(2, controller.clientsHeartBeat.size());
    }

    /**
     * Tests the getRightConnection method
     */
    @Test
    void getRightConnectionTest() {
        // Create 2 clients and add them to the newConnection map
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);

        // Check if the VirtualClient obtained is the same
        // Check if the value returned is null when the token is not contained in the
        // map
        assertEquals(mockClient1, controller.getRightConnection(100));
        assertEquals(mockClient2, controller.getRightConnection(200));
        assertNull(controller.getRightConnection(300));
    }

    /**
     * Tests the updateHeartBeat method which is invoked every time a heartBeatObj
     * is received
     */
    @Test
    void updateHeartBeatTest() {
        long value = 10;
        // Create a client
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        // Add the client to the clientsHeartBeat map
        controller.clientsHeartBeat.put(mockClient1, value);

        // Check if the updateHeartBeat execute the correct instructions when invoked
        // for a client
        // that is in the map
        assertDoesNotThrow(() -> controller.updateHeartBeat(mockClient1));
        assertNotEquals(value, controller.clientsHeartBeat.get(mockClient1));

        // Create a new client
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);

        // Checks if the updateHeartBeat method execute the right instructions when
        // is invoked on a client that is not present in the map
        assertDoesNotThrow(() -> controller.updateHeartBeat(mockClient2));
    }

    /**
     * This method tests the disconnect method.
     * It is invoked only when the methodCheckHeartBeats method finds a user, that
     * is already in a game,
     * that disconnected
     */
    @Test
    void disconnectTest() {
        // Create a new client
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);

        // At start the disconnected map is empty
        assertEquals(0, controller.disconnected.size());

        // Create a game with the client mockClient1
        assertDoesNotThrow(() -> controller.createGame("username1", 2));

        // Check if the disconnect map size changed accordingly to the method
        // instructions
        controller.disconnect("username1", 0, 100);
        assertEquals(1, controller.disconnected.size());
    }

    /**
     * Tests for the checkHeartBeats method. It is invoked every 10 seconds by a
     * scheduler and starts on the controller creation
     */
    @Test
    public void checkHeartBeatsTest() {
        long time = 1;
        // Create a new client
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);
        controller.tempClients.put("username1", mockClient1);
        controller.clientsHeartBeat.put(mockClient1, time);

        // The method is invoked with 1 client to disconnect
        controller.checkHeartBeats();

        // Check if the method detect correctly a disconnected client that is
        // not in a game and if the maps are modified coherently
        assertEquals(0, controller.clientsHeartBeat.size());
        assertEquals(0, controller.tempClients.size());

        // Create a new client that create a game
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);
        controller.clientsHeartBeat.put(mockClient2, time);
        controller.tempClients.put("username2", mockClient2);

        // A new game is created with the new client
        assertEquals(1, controller.tempClients.size());
        assertDoesNotThrow(() -> controller.createGame("username2", 2));
        assertEquals(0, controller.tempClients.size());

        // At the start the disconnect map is empty
        assertEquals(0, controller.disconnected.size());

        // The method is invoked directly for testing the correct execution
        controller.checkHeartBeats();

        // Check if the client is added to the disconnected map
        assertEquals(1, controller.disconnected.size());
    }
}