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
    private boolean ready = false;

    /**
     * Creates a client with a default name and calls inner procedures to:
     * <p>
     * - choose the UI type;
     * <p>
     * - sets its name and assigning it the remote controller once the name is
     * verified by the server controller.
     * 
     * @param server_stub : the server to connect to
     * @throws RemoteException
     */
    public RmiClient(VirtualServer server_stub) throws RemoteException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        this.UI = setUI();
        this.controller = UI.choose_username(server_stub, this);
        this.idGame = null;
    }

    /**
     * Private procedure that allow the clients to choose its UI type during its
     * creation.
     * 
     * @return the chosen UI
     */
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

    @Override
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
    }

    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }

    /**
     * Runs the client UI
     * 
     * @throws RemoteException
     * @throws NotBoundException
     * @throws PlayerNicknameAlreadyExistsException
     */
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
        if (mainGameController.checkReady()) {
            mainGameController.startGame();
        }
        return this.ready;
    }

    /**
     * Stops the current TUI sets it to game TUI and rerun it
     * 
     * @throws RemoteException
     */
    @Override
    public void startGame() throws RemoteException {
        UI.setQuitRun(true);
        UI.setInGame(true);
        UI.runUI();
    }

    @Override
    public boolean isReady() {
        return ready;
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

    // TODO in questo caso chri sta giustamente facendo in modo che una volta
    // inizializzati i PlayerController sia il MainGameController a chiamare questo
    // metodo per settare nel client il corretto player controller
    // Al momento dell'assegnazione del nome del client invece io lo stavo facendo
    // fare alla TUI tramite il metodo setUsername(String) (a seguire). Vanno bene
    // entrambi i casi ma penso che seguirò l'esempio di chri (big up to my man).
    // Però il commento l'ho scritto perché prima o poi, senza fare questo avanti
    // indietro terribile, che magari in questi casi (inizializzazione) è pure
    // necessario, dovremmo implementare un pattern ObserverObservable... mondo cane
    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {
        this.playerController = playerController;
    }

    @Override
    public void setUsername(String n) throws RemoteException {
        if (username.equals(DefaultValues.DEFAULT_USERNAME))
            username = n;
    }

}