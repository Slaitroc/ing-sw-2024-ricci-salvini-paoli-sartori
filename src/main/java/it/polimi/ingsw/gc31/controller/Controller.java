package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.concurrent.*;

import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.*;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.JoinGameObj;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

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

    private final List<GameController> gameControlList;
    private Map<String, VirtualClient> tempClients;
    private final Set<String> nicknames;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    private final Map<Integer, VirtualClient> newConnections; // FIXME
    private final Map<Integer, Integer> disconnected; // token - gameID

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
        while (newConnections.containsValue(token)) {
            token = (int) (Math.random() * 1000);
        }
        this.newConnections.put(token, newConnection);
        return token;
    }

    /**
     * This method is invoked every time a client wants to set its username
     * The method finds and sends the token associated with the VirtualClient that
     * tries to set its username
     *
     * @param newConnection is the VirtualClient that is trying to set its username
     */
    public void sendToken(VirtualClient newConnection) {
        try {
            Integer getToken = null;
            for (Map.Entry<Integer, VirtualClient> t : newConnections.entrySet()) {
                if (t.getValue().equals(newConnection)) {
                    getToken = t.getKey();
                }

            }
            newConnection.sendCommand(new SaveToken(getToken));
        } catch (RemoteException e) {
            System.out.println("The token was not sent correctly");
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
     * @throws PlayerNicknameAlreadyExistsException if the username is already in
     *                                              use.
     * @throws RemoteException
     */
    public boolean connect(VirtualClient client, String username, Integer token)
            throws RemoteException {
        // client.sendCommand(new WantsReconnectObjI())
        if (disconnected.containsKey(token)) {
            // The element in the newConnections map is updated with the new VirtualClient
            newConnections.replace(token, client);
            client.sendCommand(new WantsReconnectObj());
            // Devo ritornare true o false?
            return true;
        } else {
            sendToken(client);
            if (nicknames.add(username)) {
                tempClients.put(username, client);
                client.setController(this);
                client.sendCommand((new ValidUsernameObj(username)));

                clientsHeartBeat.put(client, System.currentTimeMillis());

                return true;
            } else {
                client.sendCommand(new WrongUsernameObj(username));
                return false;
                // FIX PlayerAlreadyExistsException non più necessaria (da verificare)
            }
        }
    }

    /**
     * Più un TODO ch una doc
     * Viene invocato dall'oggetto Reconnect.java (serverQueue) dopo che il
     * client ha specificato che vuole connettersi in risposta a
     * WantsReconnectObj.java
     * Deve innescare gli update dei listener e mandare la risposta al client
     * (pseudocodice sotto)
     *
     * @param username is the username of the client
     * @param token    is the token of the client
     * @param esito    is true if the client wants to reconnect, false otherwise
     */
    public void rejoin(String username, int token, boolean esito) {
        VirtualClient client = newConnections.get(token); // FIXME non so se è il modo correto di prendere il virtual
                                                          // client
                                                          // giusto (non i ricordo come e quando si swappa)
        if (esito) {
            try {
                // TODO Chri deve aggiungere il metodo di rejoin qua
                client.sendCommand(new ReJoinedObj(true)); // mandare questo è importante perché la ui fa cose in
                                                           // risposta a questo update
            } catch (RemoteException e) {
                e.printStackTrace();
            }
        } else {
            try {
                // If the player doesn't want to reconnect to the game a RejoinedObj with
                // parameter false is sent
                // to the client
                client.sendCommand(new ReJoinedObj(false));

                // At this point the Controller tries to connect the client as if it was the
                // first connection of it
                sendToken(client);
                if (nicknames.add(username)) {
                    tempClients.put(username, client);
                    client.setController(this);
                    client.sendCommand((new ValidUsernameObj(username)));

                    clientsHeartBeat.put(client, System.currentTimeMillis());
                } else {
                    client.sendCommand(new WrongUsernameObj(username));
                }
            } catch (RemoteException e) {
                e.printStackTrace();
            }
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
            } catch (RemoteException e) {

            }
            tempClients.remove(username);
            sendUpdateToClient(client, new GameCreatedObj(gameControlList.size() - 1));
            client.setGameController(gameControlList.get(gameControlList.size() - 1));
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
        // se il gioco era costituito da una sola persona va eliminato il gamecontroller
        // corrispondente
        sendUpdateToClient(client, new QuitFromGameRObj(username));
    }

    // GETTERS

    /**
     * @return the singleton instance.
     */
    public static synchronized Controller getController() {
        return singleton;
    }

    /**
     * Returns a list of the current games.
     *
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
    // FIXME spostare in cima attributi e metodi
    private ConcurrentHashMap<VirtualClient, Long> clientsHeartBeat;
    private ScheduledExecutorService scheduler;

    /**
     * This method creates a task that execute every 10 seconds the checkHeartBeats
     * method
     */
    private void startHeartBeatCheck() {
        scheduler.scheduleAtFixedRate(() -> checkHeartBeats(), 0, 10, TimeUnit.MILLISECONDS);
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
    private void checkHeartBeats() {
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
                        for (String u : gc.clientList.keySet()) {
                            if ((gc.clientList.get(u)).equals(client)) {
                                gc.disconnectPlayer(u);

                                // I need to know the token of the disconnected client for the disconnect method
                                for (int t : newConnections.keySet()) {
                                    if ((newConnections.get(t)).equals(client)) {
                                        disconnect(u, gc.getIdGame(), t);
                                    }
                                }
                            }
                        }
                    }
                }
                // ^ Implementato sopra ^
                // gc.disconnectPlayer(...)
                // gc.getid()
                // disconnect(username, id, token )

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
        if (!clientsHeartBeat.containsKey(client))
            System.out.println("Il client da cui è arrivato l'HeartBeat non è presente nella mappa");
        clientsHeartBeat.replace(client, System.currentTimeMillis());
        // System.out.println(Ansi.ansi().cursor(1,
        // 1).a("\\033[5m💚\\033[0m\\").reset());
        // System.out.println("HeartBeat ricevuto");
        client.sendCommand(new HeartBeatObj());
    }

    // /**
    // * This method is invoked if a check on the heart beat is met.
    // * The method searches for the gameController of the client that needs to be
    // * disconnected,
    // * if the gameController and the correct couple username/VirtualClient is
    // found
    // * the method disconnectPlayer is invoked.
    // *
    // * @param username is the username of the client to be disconnected
    // */
    // private void disconnectFromGame(String username) {
    // for (int i = 0; i < gameControlList.size(); i++) {
    // for (String u : gameControlList.get(i).clientList.keySet())
    // if (u.equals(username))
    // gameControlList.get(i).disconnectPlayer(username);
    // }
    // }

    public void disconnect(String username, int idGame, int token) {
        disconnected.put(token, idGame);
        ServerLog.controllerWrite("Client disconnesso per timeout" + username);
    }

    private void sendUpdateToClient(VirtualClient client, ClientQueueObject clientQueueObject) {
        new Thread(() -> {
            try {
                client.sendCommand(clientQueueObject);
            } catch (RemoteException e) {

            }
        }).start();
    }
}