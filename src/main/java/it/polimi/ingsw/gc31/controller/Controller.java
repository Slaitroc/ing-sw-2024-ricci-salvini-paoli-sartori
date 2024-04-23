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

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller

// gestisce l'interazione con i client

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

    private Controller() throws RemoteException {
        tempClients = new HashMap<>();
        nicknames = new HashSet<>();
        mgcList = new ArrayList<>();
    }

    public static synchronized Controller getController() {
        return singleton;
    }

    private void controllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_BLUE
                + DefaultValues.CONTROLLER_TAG + DefaultValues.ANSI_RESET + text);
    }

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
        mgcList.add(new GameController(username, client, maxNumberPlayers, mgcList.size()));
        client.setGameID(mgcList.size() - 1);
        tempClients.remove(username);
        controllerWrite("New Game Created with ID: " + (mgcList.size()));
        return mgcList.get(mgcList.size() - 1);
    }

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