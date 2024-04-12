package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.OurScanner;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.GUI;
import it.polimi.ingsw.gc31.view.TUI;
import it.polimi.ingsw.gc31.view.UI;
import javafx.beans.property.ReadOnlyMapPropertyBase;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;

public class RmiClient extends UnicastRemoteObject implements VirtualClient {
    private IController controller;
    private IMainGameController mainGameController;
    private Integer idGame;
    private String username;
    private IPlayerController playerController;
    private UI UI;
    private boolean ready;

    public RmiClient(VirtualServer server_stub) throws RemoteException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        this.UI = setUI();
        this.controller = chooseUsername(server_stub);
        this.idGame = null;
    }

    private UI setUI() {
        boolean isValid = false;
        String message = "Chose UI:\n\t1 -> TUI\n\t2 -> GUI:";

        String input;
        do {
            System.out.println(message);
            input = OurScanner.scanner.nextLine();
            if (input.equals("1") || input.equals("2")) {
                isValid = true;
            }
            message = "Invalid input";
        } while (!isValid);
        if (input.equals("1"))
            UI = new TUI(this);
        else
            UI = new GUI(this);
        return UI;
    }

    private IController chooseUsername(VirtualServer server_stub) throws RemoteException {
        return UI.choose_username(server_stub, this);
    }

    @Override
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
    }

    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }

    public void setUsername(String n) throws RemoteException {
        if (username.equals(DefaultValues.DEFAULT_USERNAME))
            username = n;
    }

    public void run() throws RemoteException, NotBoundException, PlayerNicknameAlreadyExistsException {
        UI.runUI();
        // runCli();
    }

    /* commands */
    @Override
    public boolean createGame(int maxNumberPlayer) throws RemoteException {
        mainGameController = controller.createGame(username, maxNumberPlayer);
        if (mainGameController != null)
            return true;
        return false;

    }

    @Override
    public List<String> showGames() throws RemoteException, NoGamesException {
        return controller.getGameList();
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        mainGameController = controller.joinGame(username, idGame);
    }

    @Override
    public boolean ready() throws RemoteException {
        this.ready = !this.ready;
        return this.ready;
        // TODO qui bisognerebbe far partire un check sul game controller per verificare
        // se anche gli altri player sono pronti e in caso avviare il modello
    }

    /* game commands */
    @Override
    public List<String> showHand() throws RemoteException {
        return playerController.getHand();
    }

    @Override
    public void drawGold() throws RemoteException {
        playerController.drawGold();
    }

    /* altra roba */

    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {
        this.playerController = playerController;
    }

    @Override
    public void sendMessage(String details) throws RemoteException {
        System.err.println("\n[ERROR] " + details + "\n> ");
    }
}