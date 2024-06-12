package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.utility.DV;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiClient extends UnicastRemoteObject implements VirtualClient, ClientCommands {
    private IController controller;
    private VirtualServer server;
    private IGameController gameController;
    private Integer idGame;
    private String username;
    private UI ui;
    private final LinkedBlockingQueue<ClientQueueObject> callsList;

    /**
     * Creates a client with a default name and calls inner procedures to:
     * <p>
     * - choose the UI type;
     * <p>
     * - sets its name and assigning it the remote controller once the name is
     * verified by the server controller.
     */
    public RmiClient(String ipaddress) throws RemoteException, NotBoundException {
        this.server = (VirtualServer) LocateRegistry.getRegistry(ipaddress, DV.RMI_PORT)
                .lookup("VirtualServer");
        this.server.RMIserverWrite("New connection detected from ip: " + server.getClientIP());
        this.username = DV.DEFAULT_USERNAME;
        this.controller = null;
        this.callsList = new LinkedBlockingQueue<>();
        executor();
    }

    @Override
    public void sendCommand(ClientQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    // QUEUE
    private void addQueueObj(ClientQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
        new Thread(() -> {
            while (true) {
                ClientQueueObject action;
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
                    action.execute(ui);

                }
            }
        }).start();
    }

    // CLIENT COMMANDS
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public void setUsernameCall(String username) throws RemoteException {
        if (controller == null) {
            server.setVirtualClient(this);
            server.sendCommand(new ConnectObj(username));
        }

    }

    @Override
    public void setUsernameResponse(String username) {
        this.username = username;

    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException {
        controller.sendCommand(new CreateGameObj(username, maxNumberPlayer));
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        controller.sendCommand(new JoinGameObj(username, idGame));
    }

    @Override
    public void getGameList() throws RemoteException, NoGamesException {
        controller.sendCommand(new GetGameListObj(username));
    }

    @Override
    public void setReady(boolean ready) throws RemoteException {
        gameController.sendCommand(new ReadyStatusObj(ready, username));

        // this.ready = ready;
        // if (ready) {
        // try {
        // gameController.checkReady();
        // } catch (RemoteException | IllegalStateOperationException e) {
        // e.printStackTrace();
        // }
        // }
    }

    @Override
    public void drawGold(int index) throws RemoteException {
        gameController.sendCommand(new DrawGoldObj(username, index));
    }

    @Override
    public void drawResource(int index) throws RemoteException {
        gameController.sendCommand(new DrawResObj(username, index));
        // gameController.drawResource(username);
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 0));
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 1));
    }

    @Override
    public void playStarter() throws RemoteException {
        gameController.sendCommand(new PlayStarterObj(username));
    }

    @Override
    public void play(Point point) throws RemoteException {
        gameController.sendCommand(new PlayObj(username, point.x, point.y));
    }

    @Override
    public void selectCard(int index) throws RemoteException {
        gameController.sendCommand(new SelectCardObj(username, index));
    }

    @Override
    public void changeSide() throws RemoteException {
        gameController.sendCommand(new FlipCardObj(username));
    }

    @Override
    public void changeStarterSide() throws RemoteException {
        gameController.sendCommand(new FlipStarterCardObj(username));
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
    public void sendChatMessage(String username, String message) throws RemoteException {
        gameController.sendCommand(new ChatMessage(username, message));
    }

    @Override
    public void setController(IController controller) throws RemoteException {
        this.controller = controller;
    }

    @Override
    public void setGameController(IGameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    @Override
    public void quitGame() throws RemoteException {
        gameController.sendCommand(new QuitGameObj(username));
    }

}