package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.NewChatMessage;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowInGamePlayerObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowReadyStatusObj;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.StartGameObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.PlayObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.FlipCardObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.FlipStarterCardObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {
    private final GameModel model;
    private final Map<String, VirtualClient> clientList;
    @SuppressWarnings("unused")
    private final int maxNumberPlayers;
    private final int idGame;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    private final Map<String, Boolean> readyStatus;

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
        this.clientList = new HashMap<>();
        this.clientList.put(username, client);
        this.readyStatus = new HashMap<>();
        this.readyStatus.put(username, false);
        this.model = new GameModel();
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
    public void joinGame(String username, VirtualClient client) throws RemoteException {
        clientList.put(username, client);
        readyStatus.put(username, false);
        if (maxNumberPlayers == this.clientList.size()) {
            gameControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached");
        }

        notifyListPlayers();
    }

    @Override
    public void setReadyStatus(boolean ready, String username) throws RemoteException, IllegalStateOperationException {
        readyStatus.replace(username, ready);

        for (String client: clientList.keySet()) {
            clientList.get(client).sendCommand(new ShowReadyStatusObj(client, readyStatus.get(client)));
        }
        checkReady();
    }

    // FIXME occuparsi di RemoteException
    @Override
    public void checkReady() throws RemoteException {
        int counter = 0;
        for (Boolean status : readyStatus.values()) {
            if (status == true) {
                counter++;
            }
        }
        if (counter == maxNumberPlayers) {
            for (VirtualClient clients : clientList.values()) {
                clients.sendCommand(new StartGameObj());
            }
            // TODO occuparsi dell'eccezione
            try {
                model.initGame(clientList);
                gameControllerWrite("The game has started");
            } catch (IllegalStateOperationException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void sendChatMessage(NewChatMessage message) {
        for (VirtualClient client : clientList.values()) {
            try {
                client.sendCommand(message);
            } catch (RemoteException e) {
                e.printStackTrace(); // TODO da gestire
            }

        }
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
        return clientList.size();
    }

    /**
     * Writes a message to the game controller.
     *
     * @param text the message to write.
     */
    private void gameControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_PURPLE
                + DefaultValues.gameControllerTag(String.valueOf(idGame)) + DefaultValues.ANSI_RESET + text);
    }

    // WARNING: methods receive username in input, instead of using
    // model.currPlayingPlayer.drawGold() etc.
    // because otherwise clients could play the turn of others

    /**
     * Draws a gold card from the deck for the player and then shows the player's
     * hand.
     *
     */
    public void drawGold(String username, int index) {
        try {
            model.drawGold(username, index);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }

    /**
     * Draws a resource card from the deck for the player and then shows the
     * player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    public void drawResource(String username) throws RemoteException {

    }

    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }

    public void play(String username, Point point) {
        try {
            model.play(username, point);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }

    public void playStarter(String username) {
        try {
            model.playStarter(username);
            gameControllerWrite("Player"+username+" has played starter card");
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }

    private void notifyListPlayers() {
        for (VirtualClient client : clientList.values()) {
            try {
                client.sendCommand(new ShowInGamePlayerObj(clientList.keySet().stream().toList()));
            } catch (RemoteException e) {
                gameControllerWrite(e.getMessage());
            }
        }
    }

    public void selectCard(String username, int index) {
        try {
            model.setSelectCard(username, index);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }
    public void changeSide(String username) {
        try {
            model.changeSide(username);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }

    public void changeStarterSide(String username) {
        try {
            model.changStarterSide(username);
        } catch (IllegalStateOperationException e) {
            gameControllerWrite(e.getMessage());
        }
    }


    public GameModel getModel() {
        return model;
    }
}
