package it.polimi.ingsw.gc31.controller;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.*;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.JoinGameObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import javafx.util.Pair;
import java.util.concurrent.*;

//NOTE creation of GameController for match creation
// Does the GameController related to the first match get created immediately after the first player has logged in?
// It seems easier to do it this way than to manage waits for the creation of GameControllers in the Controller
// Manages interaction with clients

/**
 * This class represents the main controller of all the games that ar currently
 * running.
 * It manages the interaction with the clients and the creation of game
 * controllers for each match.
 * It implements the IController interface and extends the UnicastRemoteObject
 * to allow remote method invocation.
 */
public class Controller extends UnicastRemoteObject implements IController {
    private static final Controller singleton;

    static {
        try {
            singleton = new Controller();
        } catch (RemoteException e) {
            // Handle the exception appropriately
            e.printStackTrace();
            throw new RuntimeException("Failed to create Controller instance.", e);
        }
    }

    protected final List<GameController> gameControlList;
    protected Map<String, VirtualClient> tempClients;
    protected final Set<String> nicknames;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    protected final Map<Integer, VirtualClient> newConnections;
    protected final Map<Integer, Pair<String, Integer>> disconnected; // token - gameID

    protected ConcurrentHashMap<VirtualClient, Long> clientsHeartBeat;
    private ScheduledExecutorService scheduler;

    /**
     * This method generates a unique token (from 0 to 999) every time a new client
     * connects with the server
     * Every token value is associated with its client in the newConnections map
     *
     * @param newConnection is the VirtualClient that just connected to the server
     * @return the token generated
     */
    public int generateToken(VirtualClient newConnection) {
        int token;
        token = (int) (Math.random() * 1000);
        while (newConnections.containsKey(token)) {
            token = (int) (Math.random() * 1000);
        }
        this.newConnections.put(token, newConnection);
        ServerLog.controllerWrite("Generic token generated for the new connection: " + token);
        return token;
    }

    /**
     * This method is invoked every time a client wants to set its username
     * The method finds and sends the token associated with the VirtualClient that
     * tries to set its username
     *
     * @param newConnection is the VirtualClient that is trying to set its username
     */
    public void sendToken(VirtualClient newConnection, int token, boolean temporary) {
        // sendToken usato per mandare il token definitivo e non quello temporaneo
        try {
            newConnection.sendCommand(new SaveToken(token, temporary));
        } catch (RemoteException e) {
            ServerLog.controllerWrite("The token was not sent correctly");
        }
    }

    /**
     * Private constructor for the Controller class.
     * It initializes the tempClients map and the nickname's set.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    private Controller() throws RemoteException {
        disconnected = new HashMap<>();
        tempClients = new HashMap<>();
        nicknames = new HashSet<>();
        gameControlList = new ArrayList<>();
        callsList = new LinkedBlockingQueue<>();
        clientsHeartBeat = new ConcurrentHashMap<>();
        scheduler = Executors.newScheduledThreadPool(1);
        newConnections = new HashMap<>();
        executor();
        startHeartBeatCheck();
    }

    /**
     * This method request to add to the list of object the new object received from
     * a client
     *
     * @param obj is the new object to be added
     * @throws RemoteException if an error occurs in the rmi connection
     */
    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    /**
     * This method adds a new object to the queue of object to be executed
     *
     * @param obj is the object to be added to the queue
     */
    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
        new Thread(() -> {
            while (true) {
                ServerQueueObject action;
                synchronized (callsList) {
                    while (callsList.isEmpty()) {
                        try {
                            callsList.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    action = callsList.poll();
                }
                if (action != null) {
                    action.execute(this);
                }
            }
        }).start();
    }

    /**
     * Connects a client to the server.
     *
     * @param client   the client to connect.
     * @param username the username of the client.
     * @return false if wrong username, true if connection is successful
     * @throws RemoteException if general connection error occurs
     */
    // FIXME forse non serve client come parametro
    public boolean connect(VirtualClient client, String username, Integer tempToken, Integer token)
            throws RemoteException {
        if (token == -1) {
            // se l'username è valido genero il nuovo token e lo mando
            ServerLog.controllerWrite("First connection of " + username + " with temporary token " + tempToken);
            VirtualClient newConnectionClient = newConnections.get(tempToken);
            if (usernameValidation(username, newConnectionClient)) {
                int newToken = generateToken(client);

                // tolgo da newConnection il vecchio token e metto quello nuovo
                newConnections.remove(tempToken);
                newConnections.put(newToken, newConnectionClient);
                sendToken(newConnectionClient, newToken, false);
                ServerLog.controllerWrite("Token sent and saved for user: " + username + " | " + newToken);
                return true;
            } else
                return false;
        } else {
            ServerLog.controllerWrite(
                    "Reconnection of: " + "temporary token = " + tempToken + " | " + "token = " + token);
            if (disconnected.containsKey(token)) {
                // viene riconnesso alla partita
                // trovo il nuovo client in newConnections
                VirtualClient clientConnections = newConnections.get(tempToken);
                newConnections.remove(tempToken);
                newConnections.put(token, clientConnections);
                clientConnections.sendCommand(new WantsReconnectObj(disconnected.get(token).getKey()));
                clientConnections.setController(this);
                clientsHeartBeat.put(clientConnections, System.currentTimeMillis());
                ServerLog.controllerWrite("Reconnection request to game with id=" + disconnected.get(token).getValue()
                        + " sent to " + disconnected.get(token).getKey());
            } else {
                ServerLog.controllerWrite("First connection of " + username + "with temporary token " + tempToken);
                VirtualClient newConnectionClient = newConnections.get(tempToken);
                if (usernameValidation(username, newConnectionClient)) {
                    int newToken = generateToken(client);
                    ServerLog.controllerWrite("Generazione nuovo token per: " + username + " | " + newToken);

                    // tolgo da newConnection il vecchio token e metto quello nuovo
                    newConnections.remove(tempToken);
                    newConnections.put(newToken, newConnectionClient);
                    sendToken(newConnectionClient, newToken, false);
                    ServerLog.controllerWrite("Token sent and saved for user: " + username + " | " + newToken);
                    return true;
                } else
                    return false;
            }
            return false;
        }
    }

    private boolean usernameValidation(String username, VirtualClient client) {
        if (username == null) {
            try {
                client.sendCommand(new WrongUsernameObj(null));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }
        if (nicknames.add(username)) {
            tempClients.put(username, client);
            try {
                client.setController(this);
                client.sendCommand((new ValidUsernameObj(username)));
            } catch (RemoteException e) {
                e.printStackTrace();
            }

            clientsHeartBeat.put(client, System.currentTimeMillis());

            return true;
        } else {
            try {
                client.sendCommand(new WrongUsernameObj(username));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            return false;
        }
    }

    /**
     * Più un TODO ch una doc
     * Viene invocato dall'oggetto Reconnect.java (serverQueue) dopo che il
     * client ha specificato che vuole connettersi in risposta a
     * WantsReconnectObj.java
     * Deve innescare gli update dei listener e mandare la risposta al client
     * (pseudocode sotto)
     *
     * @param token is the token of the client
     * @param esito is true if the client wants to reconnect, false otherwise
     */
    public void rejoin(int tempToken, int token, boolean esito) {
        VirtualClient client = newConnections.get(token);
        if (esito) {
            try {
                synchronized (gameControlList.get(disconnected.get(token).getValue()).clientListLock) {
                    client.sendCommand(new ReJoinedObj(true, gameControlList.get(disconnected.get(token).getValue()).clientList.keySet().stream().toList()));
                } // mandare questo è importante perché la ui fa cose in //
                gameControlList.get(disconnected.get(token).getValue()).reJoinGame(disconnected.get(token).getKey(),
                        newConnections.get(token));
                // risposta a questo update
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            // If the player doesn't want to reconnect to the game a RejoinedObj with
            // parameter false is sent
            // to the client
            try {
                client.sendCommand(new ReJoinedObj(false, null));
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            newConnections.remove(token);
                newConnections.put(tempToken, client);
            disconnected.remove(token);
            // At this point the Controller tries to connect the client as if it was the
            // first connection of it
        }
    }

    /**
     * Creates a new game and adds it to the game control list.
     *
     * @param username     the username of the client creating the game.
     * @param maxNumPlayer the maximum number of players for the game.
     * @throws RemoteException if an RMI error occurs.
     */
    public void createGame(String username, int maxNumPlayer) throws RemoteException {
        VirtualClient client = tempClients.get(username);
        if (maxNumPlayer < 2 || maxNumPlayer > 4) {
            sendUpdateToClient(client, new WrongGameSizeObj());
        } else {
            try {
                gameControlList.add(new GameController(username, client, maxNumPlayer,
                        gameControlList.size()));
                client.setGameID(gameControlList.size() - 1);
            } catch (RemoteException ignored) {

            }
            tempClients.remove(username);
            sendUpdateToClient(client, new GameCreatedObj(gameControlList.size() - 1));
            client.setGameController(gameControlList.getLast());
            ServerLog.controllerWrite("A new game has been created with id: " + gameControlList.size());
        }
    }

    /**
     * Allows a client to join an existing game.
     *
     * @param username the username of the client joining the game.
     * @param idGame   the ID of the game to join.
     * @throws RemoteException if an RMI error occurs.
     */
    public void joinGame(String username, int idGame) throws RemoteException {
        VirtualClient client = tempClients.get(username);
        if (idGame >= gameControlList.size() || idGame < 0) {
            sendUpdateToClient(client, new GameDoesNotExistObj());
        } else {

            if (gameControlList.get(idGame).getCurrentNumberPlayers() != gameControlList.get(idGame)
                    .getMaxNumberPlayers()) {
                gameControlList.get(idGame).sendCommand(new JoinGameObj(client, username));
                tempClients.remove(username);
            } else {
                sendUpdateToClient(client, new GameIsFullObj(idGame));
            }
        }
    }

    /**
     * This method add the client (that just quit a game lobby) to the map
     * tempClients
     *
     * @param username is the username of the player that just quit
     * @param client   is the client that requested to quit from a lobby
     * @throws RemoteException if an error occurs in the rmi connection
     */
    public void quitGame(String username, VirtualClient client) throws RemoteException {
        tempClients.put(username, client);
        // se il gioco era costituito da una sola persona va eliminato il gameController
        // corrispondente
        sendUpdateToClient(client, new QuitFromGameRObj(username));
    }

    // GETTERS

    /**
     * Controller getter
     *
     * @return the singleton instance.
     */
    public static synchronized Controller getController() {
        return singleton;
    }

    /**
     * Returns a list of the current games.
     *
     * @param username username of player to whom sent the message
     * @throws RemoteException  if an RMI error occurs.
     * @throws NoGamesException if there are no current games.
     */
    public void getGameList(String username) throws RemoteException, NoGamesException {
        List<String> res = new ArrayList<>();
        if (gameControlList.isEmpty()) {
            res.add("NO GAMES AVAILABLE");
        } else {
            for (int i = 0; i < gameControlList.size(); i++) {
                res.add(
                        i + " "
                                + gameControlList.get(i).getCurrentNumberPlayers() + " / "
                                + gameControlList.get(i).getMaxNumberPlayers());
            }
        }
        sendUpdateToClient(tempClients.get(username), new ShowGamesObj(res));
    }

    /**
     * This method return the specific VirtualClient associated with the unique
     * token received as a parameter
     * The newConnections map contains every client connected with the server and
     * the unique token associated with it
     *
     * @param token is the token associated to the VirtualClient t
     * @return the VirtualClient that has the given token
     */
    @Override
    public VirtualClient getRightConnection(int token) {
        return newConnections.get(token);
    }

    // Heartbeat resources
    /**
     * This method creates a task that execute every 10 seconds the checkHeartBeats
     * method
     */
    private void startHeartBeatCheck() {
        scheduler.scheduleAtFixedRate(this::checkHeartBeats, 0, 10, TimeUnit.SECONDS);
    }

    /**
     * This method get the current time, expressed in milliseconds, in the variable
     * "now".
     * Then, for every VirtualClient in the clientsHeartBeat list, the method checks
     * if the last heart beat
     * received from the client is older than 10 seconds.
     * If the condition is true the client is considered crashed and the method
     * remove the client from the list,
     * also closes the connection towards the client.
     */
    protected void checkHeartBeats() {
        long now = System.currentTimeMillis();

        // Checks for every active client if the last heart beat was received at most 10
        // seconds ago
        // if the last heart beat was received more than 10 seconds ago the client is
        // considered crashed
        for (VirtualClient client : clientsHeartBeat.keySet()) {
            if (now - clientsHeartBeat.get(client) > 10000) {
                // The crashed client is removed from the map with all the active clients
                clientsHeartBeat.remove(client);

                // I need to find the client, if it is in tempClients it can't be in any
                // gameController.clientList
                // so the second for will not be executed
                boolean found = false;

                // Checks if the disconnected client was in the tempClients map (it was not in a
                // game)
                for (String username : tempClients.keySet()) {
                    if ((tempClients.get(username)).equals(client)) {
                        // If the client is found it is removed from the tempClients map
                        tempClients.remove(username);
                        found = true;
                    }
                }

                // If the client was not found in tempClients => it is in a clientList of a
                // GameController (it was
                // in a game). The for searches the client in all the gameController.clientList,
                // if it is found the
                // disconnectPlayer method of the gameController is invoked with also the
                // disconnect method of the Controller
                if (!found) {
                    for (GameController gc : gameControlList) {
                        synchronized (gc.clientListLock) {
                            for (String u : gc.clientList.keySet()) {
                                if ((gc.clientList.get(u)).equals(client)) {
                                    gc.disconnectPlayer(u);

                                    // I need to know the token of the disconnected client for the disconnect method
                                    for (int t : newConnections.keySet()) {
                                        if ((newConnections.get(t)).equals(client)) {
                                            disconnect(u, gc.getIdGame(), t);
                                            ServerLog.controllerWrite("Client disconnected " + u + " | " + t);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * This method is invoked for every heartBeatObj received.
     * The method updates the time value kept in the clientsHeartBeat for the client
     * that sent it.
     * Furthermore, if an heartBeat arrives but the client is not in the Map a
     * specific message is written
     *
     * @param client is the client that sent the heart beat
     * @throws RemoteException if an error occurs in the rmi connection
     */
    @Override
    public void updateHeartBeat(VirtualClient client) throws RemoteException {
        if (!clientsHeartBeat.containsKey(client)) {
            ServerLog.controllerWrite("HeartBeat received is not bound to any client");
        } else {
            clientsHeartBeat.replace(client, System.currentTimeMillis());
            client.sendCommand(new HeartBeatObj());
        }
    }

    public void disconnect(String username, int idGame, int token) {
        disconnected.put(token, new Pair<>(username, idGame));
        ServerLog.controllerWrite("Client disconnected due to timeout");
    }

    private void sendUpdateToClient(VirtualClient client, ClientQueueObject clientQueueObject) {
        new Thread(() -> {
            try {
                client.sendCommand(clientQueueObject);
            } catch (RemoteException ignore) {

            }
        }).start();
    }
}