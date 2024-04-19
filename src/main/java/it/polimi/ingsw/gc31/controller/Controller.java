package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

//NOTE creation of GameController for match creation
// Does the GameController related to the first match get created immediately after the first player has logged in?
// It seems easier to do it this way than to manage waits for the creation of GameControllers in the Controller
// Manages interaction with clients

/**
 * This class represents the main controller of the game.
 * It manages the interaction with the clients and the creation of game controllers for each match.
 * It implements the IController interface and extends the UnicastRemoteObject to allow remote method invocation.
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

    private final List<GameController> mgcList;
    private Map<String, VirtualClient> tempClients;
    private Set<String> nicknames;

    /**
     * Private constructor for the Controller class.
     * It initializes the tempClients map and the nickname's set.
     *
     * @throws RemoteException if an RMI error occurs.
     */
    private Controller() throws RemoteException {
        tempClients = new HashMap<>();
        nicknames = new HashSet<>();
        mgcList = new ArrayList<>();
    }

    /**
     * @return the singleton instance.
     */
    public static synchronized Controller getController() {
        return singleton;
    }

    /**
     * @param id the id of the MainGameController.
     * @return the MainGameController with the specified id.
     */
    public MainGameController getMGController(int id) {
        synchronized (mgcList) {
            return mgcList.get(id);
        }
    }

    /**
     * Prints a message to the console with a specific format.
     *
     * @param text the message to print.
     */
    private void controllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_BLUE
                + DefaultValues.CONTROLLER_TAG + DefaultValues.ANSI_RESET + text);
    }

    /**
     * Connects a client to the server.
     *
     * @param client   the client to connect.
     * @param username the username of the client.
     * @throws PlayerNicknameAlreadyExistsException if the username is already in use.
     */
    @Override
    public void connect(VirtualClient client, String username) throws PlayerNicknameAlreadyExistsException {
        if (!nicknames.add(username)) {
            throw new PlayerNicknameAlreadyExistsException();
        }
        tempClients.put(username, client);

        // TODO mandare un messaggio di conferma al client
    }

    @Override
    public IGameController createGame(String username, int maxNumberPlayers) throws RemoteException {
        VirtualClient client = tempClients.get(username);
        mgcList.add(new GameController(username, client, maxNumberPlayers, mgcList.size() - 1));
        client.setGameID(mgcList.size() - 1);
        tempClients.remove(username);
        controllerWrite("New Game Created with ID: " + (mgcList.size()));
        return mgcList.get(mgcList.size() - 1);
    }

    /**
     * Returns a list of the current games.
     *
     * @return a list of the current games.
     * @throws RemoteException  if an RMI error occurs.
     * @throws NoGamesException if there are no current games.
     */
    @Override
    public void getGameList(String username) throws RemoteException, NoGamesException {
        if (mgcList.isEmpty()) {
            throw new NoGamesException();
        } else {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < mgcList.size(); i++) {
                res.add(
                        i + " "
                                + mgcList.get(i).getMaxNumberPlayers() + " / "
                                + mgcList.get(i).getCurrentNumberPlayers());
            }
            // TODO gestire qua l'eccezione?
            tempClients.get(username).showListGame(res);
        }
    }

    @Override
    public IGameController joinGame(String username, int idGame) throws RemoteException {
        mgcList.get(idGame).joinGame(username, tempClients.get(username));
        tempClients.remove(username);

        return mgcList.get(idGame);
    }
}