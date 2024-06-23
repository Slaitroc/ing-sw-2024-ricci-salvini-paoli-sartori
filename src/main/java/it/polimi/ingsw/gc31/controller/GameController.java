package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.LinkedHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.*;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.IllegalPlaceCardException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {
    protected final GameModel model;
    protected final LinkedHashMap<String, VirtualClient> clientList;
    @SuppressWarnings("unused")
    private final int maxNumberPlayers;
    private final int idGame;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    protected final LinkedHashMap<String, Boolean> readyStatus;

    /**
     * Constructor for the GameController class.
     * It initializes the game model, players, clientList, and game states.
     *
     * @param username         the username of the player.
     * @param client           the client of the player.
     * @param maxNumberPlayers the maximum number of players.
     * @param idGame           the id of the game.
     */
    public GameController(String username, VirtualClient client, int maxNumberPlayers, int idGame)
            throws RemoteException {
        this.callsList = new LinkedBlockingQueue<>();
        this.maxNumberPlayers = maxNumberPlayers;
        this.idGame = idGame;
        this.clientList = new LinkedHashMap<>();
        this.clientList.put(username, client);
        this.readyStatus = new LinkedHashMap<>();
        this.readyStatus.put(username, false);
        this.model = new GameModel(idGame);
        new Thread(this::executor).start();

        notifyListPlayers();
    }

    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
        while (true) {
            ServerQueueObject action;
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
                action.execute(this);
            }
        }
    }

    /**
     * Allows a player to join the game.
     *
     * @param username the username of the player.
     * @param client   the client of the player.
     */
    public void joinGame(String username, VirtualClient client) throws RemoteException{
        clientList.put(username, client);
        readyStatus.put(username, false);
        client.setGameController(this);
        sendUpdateToClient(client, new JoinedToGameObj(idGame, maxNumberPlayers));
        if (maxNumberPlayers == this.clientList.size()) {
            ServerLog.gControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached",
                    idGame);
        }
        notifyListPlayers();
    }

    // un giocatore può riconettersi alla partita solo se si è disconnesso per un
    // problema di rete
    // se il virtual client del giocatore che vuole riconettersi non è presente
    // nella mappa dei virtualclient
    // vuol dire che si è disconesso usando il tasto quit e quindi non può
    // riconettersi.
    // se il giocatore si era disconnesso per un problema di rete nella lobby allora
    // entra come
    public void reJoinGame(String username, VirtualClient newClient) {
        // TODO controllare se il client era presente nella lista? oppure viene fatto
        // nel controller
        // TODO cosa fare con readyStatus?
        if (clientList.containsKey(username)) {
            clientList.put(username, newClient);
            model.reconnectPlayer(username);
            newClient.setGameController(this);
            sendUpdateToClient(newClient, new JoinedToGameObj(idGame, getMaxNumberPlayers()));
            ServerLog.gControllerWrite("Welcome back "+username+"!", idGame);
        } else {
            ServerLog.gControllerWrite("C'è stato qualche problema con la rejoin di " + username, idGame);
        }
    }

    // se un giocatore si disconnette quando la partita è già iniziata non ha la
    // possibilità di rientrare
    public void quitGame(String username) throws RemoteException {
        VirtualClient client = clientList.get(username);
        clientList.remove(username);
        readyStatus.remove(username);
        Controller.getController().quitGame(username, idGame, client);

        if (model.isStarted()) {
            ServerLog.gControllerWrite(
                    "Player " + username + " has quited from the game, but the game has already started", idGame);
            model.disconnectPlayer(username);
        } else {
            ServerLog.gControllerWrite(
                    "Player " + username + " has quited from the game, but the game has not started yet", idGame);
        }
        notifyListPlayers();
    }

    public void setReadyStatus(boolean ready, String username) {
        readyStatus.replace(username, ready);
        notifyListPlayers();
        sendUpdateToClient(new ShowReadyStatusObj(username, readyStatus.get(username)));
        checkReady();
    }

    public void checkReady() {
        int counter = 0;
        for (Boolean status : readyStatus.values()) {
            if (status) {
                counter++;
            }
        }
        if (counter == maxNumberPlayers) {
            try {
                model.initGame(clientList);
                ServerLog.gControllerWrite("The game has started", idGame);
                sendUpdateToClient(new StartGameObj());
            } catch (IllegalStateOperationException e) {
                // throw new RuntimeException(e);
            }
        }
    }

    public void sendChatMessage(NewChatMessage message) {
        sendUpdateToClient(message);
    }

    /**
     * @return the maximum number of players.
     */
    public int getMaxNumberPlayers() {
        return maxNumberPlayers;
    }

    /**
     * @return the current number of players.
     */
    public int getCurrentNumberPlayers() {
        if (model.isStarted()) {
            return model.getPlayerConnection().size();
        }
        return clientList.size();
    }

    /**
     * Draws a gold card from the deck for the player and then shows the player's
     * hand.
     *
     */
    public void drawGold(String username, int index) {
        try {
            model.drawGold(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj(e.getMessage()));
        }
    }

    /**
     * Draws a resource card from the deck for the player and then shows the
     * player's hand.
     */
    public void drawResource(String username, int index) {
        try {
            model.drawResource(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void play(String username, Point point) {
        try {
            model.play(username, point);
        } catch (IllegalStateOperationException | IllegalPlaceCardException e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void playStarter(String username) {
        try {
            model.playStarter(username);
            ServerLog.gControllerWrite("Player" + username + " has played starter card", idGame);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj("You are in the wrong state"));
        } catch (ObjectiveCardNotChosenException e) {
            sendUpdateToClient(clientList.get(username),
                    new ShowInvalidActionObj("You must first choose your secret objective"));
        }
    }

    public void selectCard(String username, int index) {
        try {
            model.setSelectCard(username, index);
        } catch (IllegalStateOperationException e) {
            // TODO gestire meglio messaggio di questa eccezione
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj("You can't select a card"));
        } catch (WrongIndexSelectedCard e) {
            sendUpdateToClient(clientList.get(username), new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void changeSide(String username) {
        try {
            model.changeSide(username);
        } catch (IllegalStateOperationException e) {
            ServerLog.gControllerWrite(e.getMessage(), idGame);
        }
    }

    public void changeStarterSide(String username) {
        try {
            model.changStarterSide(username);
        } catch (IllegalStateOperationException e) {
            ServerLog.gControllerWrite(e.getMessage(), idGame);
        }
    }

    private void notifyListPlayers() {
        sendUpdateToClient(new ShowInGamePlayerObj(readyStatus));
    }

    private void sendUpdateToClient(VirtualClient client, ClientQueueObject clientQueueObject) {
        new Thread(() -> {
            try {
                client.sendCommand(clientQueueObject);
            } catch (RemoteException e) {

            }
        }).start();
    }

    private void sendUpdateToClient(ClientQueueObject clientQueueObject) {
        for (VirtualClient client : clientList.values()) {
            sendUpdateToClient(client, clientQueueObject);
        }
    }

    public void disconnectPlayer(String username) {
        model.disconnectPlayer(username);
        // Controller.getController().disconnect(clientList.get(username), username,
        // idGame, ); // FIX @AleSarto mettila nel
        // // bruteforcing di
        // // checkheartbeat
    }

    public GameModel getModel() {
        return model;
    }

    protected int getIdGame(){ return idGame; }
}
