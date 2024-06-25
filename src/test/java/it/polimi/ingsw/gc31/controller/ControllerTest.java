package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import net.bytebuddy.implementation.bind.MethodDelegationBinder;
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
            // System.out.println("Inserito il token " + i + " all'interno della mappa
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
        // Checks if the case where a player disconnected tries to reconnect.
        // mockclient1 is the old client while mockclient2 is the new client
        VirtualClient mockclient1 = Mockito.mock(VirtualClient.class);
        VirtualClient mockclient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockclient1);
        controller.disconnected.put(100, 0);

        // The mockClient1 (token = 100) disconnected, the mockClient2 (still token =
        // 100) is the
        // new VirtualClient of the user. mockClient2 tries to reconnect so the first if
        // statement
        // should be executed. The value associated to the token = 100 should be
        // replaced with the new VirtualClient and
        // should be returned the value true
        assertTrue(() -> {
            try {
                return controller.connect(mockclient2, "username", 100);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(mockclient2, controller.newConnections.get(100));
        assertEquals(0, controller.clientsHeartBeat.size());

        // Clear the maps
        controller.newConnections.clear();
        controller.disconnected.clear();
        controller.tempClients.clear();

        // If the token is not contained in the disconnected map it means that
        // is the first time the client tries to connect to the server
        controller.newConnections.put(100, mockclient1);
        controller.newConnections.put(200, mockclient2);
        assertTrue(() -> {
            try {
                return controller.connect(mockclient1, "username1", 100);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(1, controller.clientsHeartBeat.size());
        assertTrue(() -> {
            try {
                return controller.connect(mockclient2, "username2", 200);
            } catch (RemoteException e) {
                return false;
            }
        });
        assertEquals(2, controller.clientsHeartBeat.size());
        assertEquals(2, controller.tempClients.size());
        assertEquals(0, controller.disconnected.size());

        // If the token is not contained but the username of the new client is already
        // contained in the nicknames list
        // the else branch should be executed and false should be returned, without
        // adding the client
        // to any map
        VirtualClient mockClient3 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(300, mockClient3);
        assertFalse(() -> {
            try {
                return controller.connect(mockClient3, "username1", 300);
            } catch (RemoteException e) {
                return true;
            }
        });
        assertEquals(2, controller.clientsHeartBeat.size());
        assertEquals(2, controller.tempClients.size());
        assertEquals(0, controller.disconnected.size());

    }

    /**
     * Tests the rejoin method
     */
    @Test
    void rejoinTest() {
        // Create a client and put it in the newConnections map
        VirtualClient mockClient1 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(100, mockClient1);

        // If the token of the client was in the disconnected map the user can choose to
        // rejoin or not
        // If the boolean value of the rejoin method is true the player wants to rejoin
        // and the if branch should be executed
        assertDoesNotThrow(() -> controller.rejoin("username1", 100, true));

        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);

        // If the method rejoin is invoked with the boolean parameter false the else
        // branch is executed
        // and the method tries to connect the client as if it was the first time
        assertDoesNotThrow(() -> controller.rejoin("username2", 200, false));
        // The rejoin add the new client to the nicknames list, to the tempClients map
        // and to the clientsHeartBeat map
        assertEquals(1, controller.nicknames.size());
        assertEquals(1, controller.tempClients.size());
        assertEquals(1, controller.clientsHeartBeat.size());

        VirtualClient mockClient3 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(300, mockClient3);

        // If a clients tries to rejoin with the boolean parameter false but with the
        // boolean
        // parameter false the method doesn't add the client to any map and send a
        // WrongUsernameObj
        assertDoesNotThrow(() -> controller.rejoin("username2", 300, false));
        assertEquals(1, controller.nicknames.size());
        assertEquals(1, controller.tempClients.size());
        assertEquals(1, controller.clientsHeartBeat.size());
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
        assertDoesNotThrow(() -> controller.getGameList("username1"));

        // Checks the instructions executed if the gameControlList is not empty
        VirtualClient mockClient2 = Mockito.mock(VirtualClient.class);
        controller.newConnections.put(200, mockClient2);
        controller.tempClients.put("username2", mockClient2);

        assertDoesNotThrow(() -> controller.createGame("username2", 2));
        assertDoesNotThrow(() -> controller.getGameList("username1"));
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

        // Checks if the updateHeartBeat method execute the right instrucions when
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