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
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {
    private final GameModel model;
    private final LinkedHashMap<String, VirtualClient> clientList;
    @SuppressWarnings("unused")
    private final int maxNumberPlayers;
    private final int idGame;
    private final LinkedBlockingQueue<ServerQueueObject> callsList;
    private final LinkedHashMap<String, Boolean> readyStatus;

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
            ServerLog.gControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached",
                    idGame);
        }

        notifyListPlayers();
    }

    @Override
    public void setReadyStatus(boolean ready, String username) throws RemoteException, IllegalStateOperationException {
        readyStatus.replace(username, ready);

        notifyListPlayers();
        for (String client : clientList.keySet()) {
            clientList.get(client).sendCommand(new ShowReadyStatusObj(username, readyStatus.get(username)));
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
                ServerLog.gControllerWrite("The game has started", idGame);
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
            ServerLog.gControllerWrite(e.getMessage(), idGame);
        }
    }

    /**
     * Draws a resource card from the deck for the player and then shows the
     * player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    public void drawResource(String username, int index) throws RemoteException {
        try {
            model.drawResource(username, index);
        } catch (IllegalStateOperationException e) {
            throw new RuntimeException(e);
        }
    }

    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            ServerLog.gControllerWrite(e.getMessage(), idGame);
        }
    }

    public void play(String username, Point point) {
        try {
            model.play(username, point);
        } catch (IllegalStateOperationException e) {
            try {
                clientList.get(username).sendCommand(new ShowInvalidActionObj("You are in the wrong state"));
            } catch (RemoteException ex) {
                // TODO occuparsi dell'eccezione
                throw new RuntimeException(ex);
            }
        }
    }

    public void playStarter(String username) {
        try {
            model.playStarter(username);
            ServerLog.gControllerWrite("Player" + username + " has played starter card", idGame);
        } catch (IllegalStateOperationException e) {
            try {
                clientList.get(username).sendCommand(new ShowInvalidActionObj("You are in the wrong state"));
            } catch (RemoteException ex) {
                // TODO occuparsi dell'eccezione
                throw new RuntimeException(ex);
            }
        } catch (ObjectiveCardNotChosenException e) {
            try {
                clientList.get(username).sendCommand(new ShowInvalidActionObj("You must first choose your secret objective"));
            } catch (RemoteException ex) {
                // TODO occuparsi dell'eccezione
                throw new RuntimeException(ex);
            }
        }
    }

    private void notifyListPlayers() {
        for (VirtualClient client : clientList.values()) {
            try {
                client.sendCommand(new ShowInGamePlayerObj(readyStatus));
            } catch (RemoteException e) {
                ServerLog.gControllerWrite(e.getMessage(), idGame);
            }
        }
    }

    public void selectCard(String username, int index) {
        try {
            model.setSelectCard(username, index);
        } catch (IllegalStateOperationException e) {
            try {
                clientList.get(username).sendCommand(new ShowInvalidActionObj("You are in the wrong state"));
            } catch (RemoteException ex) {
                // TODO occuparsi dell'eccezione
                throw new RuntimeException(ex);
            }
        } catch (WrongIndexSelectedCard e) {
            try {
                clientList.get(username).sendCommand(new ShowInvalidActionObj("Selected index out of bounds"));
            } catch (RemoteException ex) {
                // TODO occuparsi dell'eccezione
                throw new RuntimeException(ex);
            }
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

    public GameModel getModel() {
        return model;
    }
}
