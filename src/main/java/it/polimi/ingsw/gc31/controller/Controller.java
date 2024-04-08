package it.polimi.ingsw.gc31.controller;

import java.rmi.RemoteException;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.rmi.VirtualView;
import it.polimi.ingsw.gc31.utility.DeepCopy;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;
import javafx.scene.layout.CornerRadii;

import java.awt.Color;

//NOTE creazione GameController x la creazione del match
//il GameController relativo al primo match viene creato subito dopo che il primo player si è loggato? 
//Mi sembra più semplice fare così che gestire le attese per la creazione dei GameController nel Controller

// gestisce l'interazione con i client

public class Controller implements VirtualController{
    private final Map<Integer, MainGameController> gameList;
    private Map<String, VirtualView> tempClients;
    private Set<String> usernameList;
    private Integer progressiveIdGame;
    Registry registry;

    public Controller() throws RemoteException{
        gameList = new HashMap<>();
        tempClients = new HashMap<>();
        this.registry = registry;
        this.usernameList = new HashSet<>();
        progressiveIdGame = 0;
    }
    @Override
    public void connect(VirtualView client, String username) throws PlayerNicknameAlreadyExistsException {
        if (usernameList.contains(username)) {
            throw  new PlayerNicknameAlreadyExistsException();
        }

        System.out.println("[SERVER-Controller] new client connected");
        usernameList.add(username);
        tempClients.put(username, client);
    }
    @Override
    public void getGameList(String username) throws RemoteException {
        if (gameList.isEmpty()) {
            tempClients.get(username).reportError("Non sono presenti game creati. Digita 'crea game' per creare un nuovo game");
        } else {
            List<String> res = new ArrayList<>();
            for (Integer idGame : gameList.keySet()) {
                res.add(
                        idGame.toString() + " "
                                + gameList.get(idGame).getMaxNumberPlayers() + " / "
                                + gameList.get(idGame).getCurrentNumberPlayers()
                );
            }
            tempClients.get(username).showGameList(res);
        }
    }

    @Override
    public VirtualMainGameController createGame(String username, int maxNumberPlayers) throws RemoteException {
        gameList.put(progressiveIdGame, new MainGameController(username, tempClients.get(username), maxNumberPlayers));

        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);

//        VirtualMainGameController stubMainGameController = (VirtualMainGameController) UnicastRemoteObject.exportObject(gameList.get(progressiveIdGame), 0);
//        registry.rebind("VirtualMainGameController"+progressiveIdGame.toString(), stubMainGameController);

        System.out.println("[SERVER] Creato game con id: " + progressiveIdGame);

        //viene ritornato l'id del game associato al client, poi viene incrementato
        return gameList.get(progressiveIdGame++);
    }

    @Override
    public VirtualMainGameController joinGame(String username, Integer idGame) throws RemoteException {
        // viene aggiunto il client al game corrispondente
        gameList.get(idGame).joinGame(username, tempClients.get(username));

        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);

        return gameList.get(idGame);
    }
}

// public class Controller implements Cloneable, DeepCopy<Controller> {

// private final List<GameController> gamesList; // NOTE meglio Set o List?
// private final Set<String> globalUsernameSet;

// public Controller() {
// this.gamesList = new ArrayList<GameController>();
// this.globalUsernameSet = new HashSet<String>();

// }

// /**
// *
// * @param username : player's nickname
// * @return true if the player's nickname already exists, false otherwise
// * @autor Slaitroc
// */
// private boolean doesNameAlreadyExist(String username) {
// if (globalUsernameSet.contains(username))
// return true;
// else
// return false;

// }

// /**
// * Add the new GameController to the Controller gameList
// *
// * @param username player's nickname
// * @return the gamePosition of the GameController in gameSet
// */
// public int createGameController(String username, int numPlayers) {
// gamesList.add(new GameController(numPlayers, username));
// return gamesList.size() - 1;
// }

// /**
// * @apiNote add the player's nickname to the globalPlayersSet
// * @param username
// * @throws PlayerNicknameAlreadyExistsException if the nickname is already in
// * use
// */
// public void addPlayerUsername(String username) throws
// PlayerNicknameAlreadyExistsException {
// if (!doesNameAlreadyExist(username))
// globalUsernameSet.add(username);
// else
// throw new PlayerNicknameAlreadyExistsException();
// }

// public GameController getGameController(int gameID) {
// return gamesList.get(gameID);
// }

// public Set<String> getGlobalUsernameSet() {
// return globalUsernameSet;
// }

// @Override
// public Controller deepCopy() {
// Controller cloned = new Controller();
// for (GameController gc : gamesList) {
// cloned.gamesList.add(gc.deepCopy());
// }
// for (String nickname : globalUsernameSet) {
// cloned.globalUsernameSet.add(new String(nickname));
// }
// return cloned;
// }

// }