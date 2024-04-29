package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.player.PlayArea;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.tui.tuiObj.CardTUI;
import it.polimi.ingsw.gc31.view.tui.tuiObj.PlayAreaTUI;

import com.google.gson.reflect.TypeToken;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Map;
import java.awt.Point;

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

    // metodi del ClientCommands
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
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
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
    public void joinGame(int idGame) throws RemoteException {
        gameController = controller.joinGame(username, idGame);
    }

    @Override
    public void getGameList() throws RemoteException, NoGamesException {
        controller.getGameList(username);
    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        ui.showListGame(listGame);
    }

    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }
    // Metodi del virtualController

    @Override
    public void show_handPlayer(String username, List<String> hand) throws RemoteException {
        // TODO temporaneo
        // System.out.println("Hand of " + username);
        ui.showHand(hand);
        // hand.forEach(System.out::println);
    }

    @Override
    public void show_scorePlayer(String username, Integer score) throws RemoteException {
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        // // TODO temporaneo
        // System.out.println("Starter card:");
        // System.out.println(starterCard);
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {

    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        Map<Point, PlayableCard> pA = gsonTranslater.fromJson(playArea, new TypeToken<Map<Point, PlayableCard>>() {
        }.getType());
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // // TODO temporaneo
        // System.out.println("Gold deck");
        // System.out.println("Card on top of the deck: " + firstCardDeck);
        // System.out.println("Card1: " + card1);
        // System.out.println("Card2: " + card2);
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {

    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {

    }

    @Override
    public void showMessage(String msg) throws RemoteException {
        ui.showMessage(msg);
    }

    private boolean ready = false;

    @Override
    public boolean isReady() {
        return ready;
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
    public void startGame() throws RemoteException {
        ui.updateToPlayingState();
    }

    // @Override
    // public void startGame() throws RemoteException {
    // UI.setQuitRun(true);
    // UI.setInGame(true);
    // UI.runUI();
    // }

    // @Override
    // public boolean isReady() {
    // return ready;
    // }

}