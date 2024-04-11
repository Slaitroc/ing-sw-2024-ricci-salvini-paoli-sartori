package it.polimi.ingsw.gc31.controller;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller

// gestisce l'interazione con i client

public class Controller extends UnicastRemoteObject implements IController, Serializable {
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

    private static final List<MainGameController> mgcList = new ArrayList<>();
    private Map<String, VirtualClient> tempClients;
    private Set<String> nicknames;
    private static String nextID = "0";

    private Controller() throws RemoteException {
        tempClients = new HashMap<>();
        this.nicknames = new HashSet<>();
    }

    public static synchronized Controller getController() {
        return singleton;
    }

    public MainGameController getMGController(int id) {
        synchronized (mgcList) {
            return mgcList.get(id);
        }
    }

    @Override
    public void connect(VirtualClient client, String username) throws PlayerNicknameAlreadyExistsException {
        if (!nicknames.add(username)) {
            throw new PlayerNicknameAlreadyExistsException();
        }
        tempClients.put(username, client);
    }

    @Override
    public void getGameList(String username) throws RemoteException {
        if (mgcList.isEmpty()) {
            tempClients.get(username)
                    .sendMessage("Non sono presenti game creati. Digita 'crea game' per creare un nuovo game");
        } else {
            List<String> res = new ArrayList<>();
            for (int i = 0; i < mgcList.size(); i++) {
                res.add(
                        i + " "
                                + mgcList.get(i).getMaxNumberPlayers() + " / "
                                + mgcList.get(i).getCurrentNumberPlayers());
            }
            tempClients.get(username).showGameList(res);
        }
    }

    @Override
    public IMainGameController createGame(String username, int maxNumberPlayers) throws RemoteException {
        System.out.println(DefaultValues.RMI_SERVER_TAG + DefaultValues.CONTROLLER_TAG + "Creato game con id: "
                + (mgcList.size()));
        VirtualClient client = tempClients.get(username);
        mgcList.add(new MainGameController(username, client, maxNumberPlayers, nextID));
        updateNextID();
        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);
        client.setGameID(mgcList.size() - 1);
        return mgcList.get(mgcList.size() - 1);
    }

    @Override
    public IMainGameController joinGame(String username, Integer idGame) throws RemoteException {
        // viene aggiunto il client al game corrispondente
        mgcList.get(idGame).joinGame(username, tempClients.get(username));

        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);

        return mgcList.get(idGame);
    }

    private void updateNextID() {
        nextID = String.valueOf(mgcList.size());
    }
}