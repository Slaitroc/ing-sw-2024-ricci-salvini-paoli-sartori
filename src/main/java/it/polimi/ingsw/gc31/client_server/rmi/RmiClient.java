package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiClient extends UnicastRemoteObject implements VirtualClient, ClientCommands {
    private IController controller;
    private VirtualServer server;
    private IGameController gameController;
    private Integer idGame;
    private String username;
    private UI ui;

    /**
     * Creates a client with a default name and calls inner procedures to:
     * <p>
     * - choose the UI type;
     * <p>
     * - sets its name and assigning it the remote controller once the name is
     * verified by the server controller.
     */
    public RmiClient() throws RemoteException, NotBoundException {
        this.server = (VirtualServer) LocateRegistry.getRegistry("127.0.0.1", 1100).lookup("VirtualServer");
        this.server.RMIserverWrite("New connection detected...");
        this.username = DefaultValues.DEFAULT_USERNAME;
        this.controller = null;
    }

    // CLIENT COMMANDS
    @Override
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public void setUsername(String username) throws RemoteException, PlayerNicknameAlreadyExistsException {
        if (controller == null) {
            controller = server.connect(this, username);
        }

        if (this.username.equals(DefaultValues.DEFAULT_USERNAME)) {
            this.username = username;
        }

    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException {
        gameController = controller.createGame(username, maxNumberPlayer);
        if (gameController != null) {
            ui.show_gameCreated();
        }
    }

    @Override
    public void drawGold() throws RemoteException {
        gameController.drawGold(username);
    }

    @Override
    public void drawResource() throws RemoteException {
        gameController.drawResource(username);
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        gameController = controller.joinGame(username, idGame);
    }

    @Override
    public void getGameList() throws RemoteException, NoGamesException {
        controller.getGameList(username);
    }

    @Override
    public void setReady(boolean ready) {
        this.ready = ready;
        if (ready) {
            try {
                gameController.checkReady();
            } catch (RemoteException | IllegalStateOperationException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // VIRTUAL CLIENT
    private boolean ready = false;

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        ui.show_listGame(listGame);
    }

    @Override
    public void startGame() throws RemoteException {
        ui.updateToPlayingState();
    }

    // FIX parlare con christian e eventualmente togliere il metodo anche
    // dall'interfaccia
    @Override
    public ShowUpdate getUI() throws RemoteException {
        return this.ui;
    }

    // SHOW UPDATE
    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        ui.show_goldDeck(firstCardDeck, card1, card2);
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        ui.show_resourceDeck(firstCardDeck, card1, card2);
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        ui.show_objectiveDeck(firstCardDeck, card1, card2);
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        ui.show_starterCard(starterCard);
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        ui.show_objectiveCard(objectiveCard);
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        ui.show_playArea(username, playArea, achievedResources);
    }

    @Override
    public void show_handPlayer(String username, List<String> hand) throws RemoteException {
        ui.show_handPlayer(username, hand);
    }

    @Override
    public void show_scorePlayer(String key, Integer value) throws RemoteException {
        ui.show_scorePlayer(key, value);
    }

}