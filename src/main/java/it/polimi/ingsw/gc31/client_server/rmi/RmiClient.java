package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

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
    private boolean ready = false;

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
        System.out.println("Hand of "+username);
        hand.forEach(System.out::println);
    }

    @Override
    public void show_scorePlayer(String username, Integer score) throws RemoteException {
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        // TODO temporaneo
        System.out.println("Starter card:");
        System.out.println(starterCard);
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {

    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {

    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO temporaneo
        System.out.println("Gold deck");
        System.out.println("Card on top of the deck: "+firstCardDeck);
        System.out.println("Card1: "+card1);
        System.out.println("Card2: "+card2);
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


    // private UI setUI() {
    // boolean isValid = false;
    // String message = "Chose UI:\n\t1 -> TUI\n\t2 -> GUI:";
    //
    // String input;
    // do {
    // System.out.println(message);
    // input = OurScanner.scanner.nextLine();
    // if (input.equals("1") || input.equals("2")) {
    // isValid = true;switch
    // }
    // message = "Invalid input";
    // } while (!isValid);
    // if (input.equals("1"))
    // UI = new TUI(this);
    // else
    // UI = new GUI(this);
    // return UI;
    // }

    // @Override
    // public boolean ready() throws RemoteException {
    // this.ready = !this.ready;
    // if (mainGameController.checkReady()) {
    // mainGameController.startGame();
    // }
    // return this.ready;
    // }

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

    /* game commands */

    /* altra roba */

    // TODO in questo caso chri sta giustamente facendo in modo che una volta
    // inizializzati i PlayerController sia il MainGameController a chiamare questo
    // metodo per settare nel client il corretto player controller
    // Al momento dell'assegnazione del nome del client invece io lo stavo facendo
    // fare alla TUI tramite il metodo setUsername(String) (a seguire). Vanno bene
    // entrambi i casi ma penso che seguirò l'esempio di chri (big up to my man).
    // Però il commento l'ho scritto perché prima o poi, senza fare questo avanti
    // indietro terribile, che magari in questi casi (inizializzazione) è pure
    // necessario, dovremmo implementare un pattern ObserverObservable... mondo cane
}