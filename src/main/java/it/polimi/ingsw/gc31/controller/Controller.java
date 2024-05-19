package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameCreatedObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameDoesNotExistObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.GameIsFullObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.JoinedToGameObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowGamesObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ValidUsernameObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.WrongGameSizeObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.WrongUsernameObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import java.util.concurrent.LinkedBlockingQueue;

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

    /**
     * Private constructor for the Controller class.
     * It initializes the tempClients map and the nickname's set.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    private Controller() throws RemoteException {
        tempClients = new HashMap<>();
        nicknames = new HashSet<>();
        gameControlList = new ArrayList<>();
        callsList = new LinkedBlockingQueue<>();
        new Thread(this::executor).start();
    }

    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
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
    }

    /**
     * Prints a message to the console with a specific format.
     *
     * @param text the message to print.
     */
    public void controllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_BLUE
                + DefaultValues.CONTROLLER_TAG + DefaultValues.ANSI_RESET + text);
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
    @Override
    public void connect(VirtualClient client, String username)
            throws RemoteException, PlayerNicknameAlreadyExistsException {
        if (nicknames.add(username)) {
            tempClients.put(username, client);
            client.sendCommand(new ValidUsernameObj(username));
        } else {
            client.sendCommand(new WrongUsernameObj(username));
            throw new PlayerNicknameAlreadyExistsException();
        }
    }

    /**
     * Creates a new game and adds it to the game control list.
     *
     * @param username         the username of the client creating the game.
     * @param maxNumberPlayers the maximum number of players for the game.
     * @return the game controller for the newly created game.
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public IGameController createGame(String username, int maxNumPlayer) throws RemoteException {
        VirtualClient client = tempClients.get(username);
        if (maxNumPlayer < 2 || maxNumPlayer > 4) {
            client.sendCommand(new WrongGameSizeObj());
            return null;
        } else {
            try {
                gameControlList.add(new GameController(username, client, maxNumPlayer,
                        gameControlList.size()));
                client.setGameID(gameControlList.size() - 1);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            tempClients.remove(username);
            client.sendCommand(new GameCreatedObj(gameControlList.size() - 1));
            return gameControlList.get(gameControlList.size() - 1);
        }

    }

    /**
     * Allows a client to join an existing game.
     *
     * @param username the username of the client joining the game.
     * @param idGame   the ID of the game to join.
     * @return the game controller for the joined game.
     * @throws RemoteException if an RMI error occurs.
     */
    @Override
    public IGameController joinGame(String username, int idGame) throws RemoteException {

        if (idGame >= gameControlList.size() || idGame < 0) {
            tempClients.get(username).sendCommand(new GameDoesNotExistObj());
            return null;
        } else {

            if (gameControlList.get(idGame).getCurrentNumberPlayers() != gameControlList.get(idGame)
                    .getMaxNumberPlayers()) {
                gameControlList.get(idGame).joinGame(username, tempClients.get(username));
                tempClients.get(username).sendCommand(new JoinedToGameObj(idGame));
                tempClients.remove(username);
                return gameControlList.get(idGame);
            } else {
                tempClients.get(username).sendCommand(new GameIsFullObj(idGame));
                return null;
            }
        }
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
    @Override
    public void getGameList(String username) throws RemoteException, NoGamesException {
        if (gameControlList.isEmpty()) {
            List<String> res = new ArrayList<>();
            res.add("NO GAMES AVAILABLE");
            tempClients.get(username).sendCommand(new ShowGamesObj(res));
        } else {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < gameControlList.size(); i++) {
                res.add(
                        i + " "
                                + gameControlList.get(i).getCurrentNumberPlayers() + " / "
                                + gameControlList.get(i).getMaxNumberPlayers());
            }
            tempClients.get(username).sendCommand(new ShowGamesObj(res));
        }
    }
}