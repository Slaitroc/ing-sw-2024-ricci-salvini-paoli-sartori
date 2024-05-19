package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ChatMessage;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.GetGameListObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ChooseSecretObjectiveObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawGoldObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ReadyStatusObj;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

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
    public RmiClient() throws RemoteException, NotBoundException {
        this.server = (VirtualServer) LocateRegistry.getRegistry("127.0.0.1", 1100).lookup("VirtualServer");
        this.server.RMIserverWrite("New connection detected...");
        this.username = DefaultValues.DEFAULT_USERNAME;
        this.controller = null;
        this.callsList = new LinkedBlockingQueue<>();
        new Thread(this::executor).start();
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
    }

    // CLIENT COMMANDS
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public void setUsername(String username) throws RemoteException {
        if (controller == null) {
            controller = server.connect(this, username);
            this.username = username; // FIX sarebbe meglio fosse final ma complica molto le cose
        }

    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException {
        gameController = controller.createGame(username, maxNumberPlayer);
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        gameController = controller.joinGame(username, idGame);
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
    public void drawGold() throws RemoteException {
        gameController.sendCommand(new DrawGoldObj(username));
    }

    @Override
    public void drawGoldCard1() throws RemoteException {
        gameController.drawGoldCard1(username);
    }

    @Override
    public void drawGoldCard2() throws RemoteException {
        gameController.drawGoldCard2(username);
    }

    @Override
    public void drawResource() throws RemoteException {
        gameController.drawResource(username);
    }

    @Override
    public void drawResourceCard1() throws RemoteException {
        gameController.drawResourceCard1(username);
    }

    @Override
    public void drawResourceCard2() throws RemoteException {
        gameController.drawResourceCard2(username);
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 1));
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 2));
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

    // FIX parlare con christian e eventualmente togliere il metodo anche
    // dall'interfaccia
    @Override
    public ShowUpdate getUI() throws RemoteException {
        return this.ui;
    }

    @Override
    public void sendChatMessage(String username, String message) throws RemoteException {
        gameController.sendCommand(new ChatMessage(username, message));
    }

}