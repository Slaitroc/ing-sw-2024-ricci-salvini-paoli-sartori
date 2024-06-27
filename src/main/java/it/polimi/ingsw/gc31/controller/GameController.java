package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.listeners.GameListenerHandler;
import it.polimi.ingsw.gc31.client_server.listeners.ListenerType;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.*;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.EndGameOnePlayerLeftObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.QuitGameObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {

    /**
     * Reference to the Game model, containing the cards, the players and the
     * playAreas
     */
    protected GameModel model;

    /**
     * Map of Usernames linked to Virtual clients of the players in the current game
     */
    protected final Map<String, VirtualClient> clientList;

    /**
     * Object to lock when operating on the client list
     */
    protected final Object clientListLock = new Object();
    /**
     * Is the number of the player for the particular game
     */
    private final int maxNumberPlayers;
    /**
     * Is a number that identifies a unique game
     */
    private final int idGame;

    /**
     * List of ServerQueObject for synchronization of client commands
     */
    protected final LinkedBlockingQueue<ServerQueueObject> callsList;

    /**
     * Map of Usernames of in game Players, linked to their ready status
     * Used in Lobby to detect when all players are ready to play
     */
    protected final LinkedHashMap<String, Boolean> readyStatus;

    /**
     * A scheduler that is used to count how much time passes starting from the
     * moment a player is the only one
     * left connected to a match. If enough time passes the player wins the match.
     */
    ScheduledExecutorService schedulerLastPlayerConnected;

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
        this.model = new GameModel(clientListLock, idGame);
        new Thread(this::executor).start();

        notifyListPlayers();
    }

    /**
     * This public method is used to add the object received to the list (callsList)
     * of object that needs to be executed
     *
     * @param obj is the object received from the client to be executed
     * @throws RemoteException if an error occurs during the execution of a remote
     *                         method
     */
    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    /**
     * This method is invoked by the sendCommand method. It synchronizes on the
     * callsList and add the object received as a
     * parameter in the callList
     *
     * @param obj is the object that needs to be added in the callList
     */
    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    /**
     * A method executed only by a thread that has the only purpose to poll from the
     * callList and execute the
     * objects stored inside. If there aren't any object the thread wait for a new
     * one to arrive.
     */
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
     * It adds the player to the clientList and sets the gameController to the
     * client.
     * It sends a {@link JoinedToGameObj} to the client.
     * 
     *
     * @param username the username of the player.
     * @param client   the client of the player.
     * 
     * @throws RemoteException if an error occurs during the execution of a remote
     *                         method
     * 
     * @see it.polimi.ingsw.gc31.view.UI#show_joinedToGame(int, int)
     */
    public void joinGame(String username, VirtualClient client) throws RemoteException {
        synchronized (clientListLock) {
            clientList.put(username, client);
        }
        readyStatus.put(username, false);
        client.setGameController(this);
        sendUpdateToClient(username, new JoinedToGameObj(idGame, maxNumberPlayers));
        if (maxNumberPlayers == this.clientList.size()) {
            ServerLog.gControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached",
                    idGame);
        }
        notifyListPlayers();
    }

    // Un giocatore può riconnettersi alla partita solo se si è disconnesso per un
    // problema di rete
    // se il virtual client del giocatore che vuole riconnettersi non è presente
    // nella mappa dei VirtualClient
    // vuol dire che si è disconnesso usando il tasto quit e quindi non può
    // riconnettersi.
    // Se il giocatore si era disconnesso per un problema di rete nella lobby allora
    // entra come

    /**
     * This method is invoked by either the Controller or a ReJoinObj. In both cases
     * a previously disconnected player
     * return in the game it left. The countdown timer is cancelled, the client is
     * added again in the clientList of the game,
     * the gameController is given to the client and the reconnectPlayer method of
     * the model is invoked
     *
     * @param username  is the username of the previously disconnected player
     * @param newClient is the new VirtualClient associated to the client after the
     *                  reconnection
     * @throws RemoteException if an error occurs during the execution of a remote
     *                         method
     */
    public void reJoinGame(String username, VirtualClient newClient) throws RemoteException {
        synchronized (clientListLock) {
            if (clientList.containsKey(username)) {
                if (schedulerLastPlayerConnected != null) {
                    schedulerLastPlayerConnected.shutdownNow();
                }
                clientList.put(username, newClient);
                // model.setGameState();
                model.getListeners().values().forEach(GameListenerHandler::setEnabled);
                model.reconnectPlayer(username);
                newClient.setGameController(this);
                // newClient.sendCommand(new ReJoinedObj(true));
                ServerLog.gControllerWrite("Welcome back " + username + "!", idGame);
            } else {
                ServerLog.gControllerWrite("C'è stato qualche problema con la rejoin di " + username, idGame);
            }
        }
        model.notifyAllGameListeners();
    }

    /**
     * Make the current user quit from his game. If the game is started the player
     * will not have the possibility to rejoin the game. If the game was not yet
     * started, the player will
     * quit the game with the possibility to rejoin the same match or another one
     *
     * @param username of the player that quit
     * @throws RemoteException for generic problem with server connection
     */
    public void quitGame(String username) throws RemoteException {
        synchronized (clientListLock) {
            VirtualClient client = clientList.get(username);
            clientList.remove(username);
            readyStatus.remove(username);
            Controller.getController().quitGame(username, client);
        }

        if (model.isStarted()) {
            ServerLog.gControllerWrite(
                    "Player " + username + " has quit from the game, but the game has already started", idGame);
            disconnectPlayer(username);
            model.notifyAllGameListeners();
        } else {
            ServerLog.gControllerWrite(
                    "Player " + username + " has quit from the game, but the game has not started yet", idGame);
        }
        notifyListPlayers();
    }

    /**
     * Change the status from ready to not ready and vice versa
     *
     * @param ready    boolean value. If True, player is ready to start the game
     * @param username Player in match that called this method
     */
    public void setReadyStatus(boolean ready, String username) {
        readyStatus.replace(username, ready);
        notifyListPlayers();
        sendUpdateToClient(new ShowReadyStatusObj(username, readyStatus.get(username)));
        checkReady();
    }

    /**
     * Checks if two conditions are met:
     * If all the player in the lobby are ready
     * If the maximum number of the players for the game has been reached
     * If both these conditions are true it starts the game for all players
     */
    public void checkReady() {
        int counter = 0;
        for (Boolean status : readyStatus.values()) {
            if (status) {
                counter++;
            }
        }
        if (counter == maxNumberPlayers) {
            try {
                sendUpdateToClient(new StartGameObj());
                synchronized (clientListLock) {
                    model.initGame(clientList, clientListLock);
                }
                ServerLog.gControllerWrite("The game has started", idGame);
                model.notifyAllGameListeners();
                model.notifyAllGameListeners();
            } catch (IllegalStateOperationException e) {
                // throw new RuntimeException(e);
            }
        }
    }

    /**
     * Same method used to send a public or a private message
     *
     * @param message ClientQueueObject containing fromUsername, toUsername (for PM)
     *                and message content
     */
    public void sendChatMessage(NewChatMessage message) {
        sendUpdateToClient(message);
    }

    /**
     * Draws a gold card from the deck for the player and then through then sends
     * the following update to the listener:
     * <ul>
     * <li>Hand</li>
     * <li>GoldDeck</li>
     * <li>ResourceDeck</li>
     * <li>Turn</li>
     * <li>PlayerScore</li>
     * </ul>
     * If the deck is empty, the player will receive the
     * {@link ShowInvalidActionObj} object to notify him that the deck is empty or
     * the game is in the wrong state and the action can't be performed
     * 
     * @param username of the player that wants to draw a gold card
     * @param index    of the card in the deck
     * 
     */
    public void drawGold(String username, int index) {
        try {
            model.drawGold(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        } catch (EmptyDeckException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj("Gold deck is empty"));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.HAND, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.GOLD_DECK, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.RESOURCE_DECK, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.TURN, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
    }

    /**
     * Draws a resource card from the deck for the player and then shows the
     * player's hand.
     */
    public void drawResource(String username, int index) {
        try {
            model.drawResource(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        } catch (EmptyDeckException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj("Resource deck is empty"));
        }

        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.GOLD_DECK, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.HAND, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.RESOURCE_DECK, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.TURN, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
    }

    /**
     * This method is invoked to choose the secret objective for the player. The
     * method invokes the corresponding method of
     * the model. If the game is not in the right state a
     * IllegalStateOperationException and a specific Object is sent
     * to the client. Also, the listener of the player score and objective card are
     * notified
     *
     * @param username is the username of the player that wants to choose a secret
     *                 objective
     * @param index    is the value used to identify which card to choose
     */
    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }

        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.OBJECTIVE_CARD, model));
    }

    /**
     * This method is invoked when a player wants to play a card. It invokes the
     * method of the model, in case the
     * state of the game is not the correct one the specific object is sent. The
     * listeners for the play area, hand, player
     * score and turn are notified.
     *
     * @param username is the username of the client that wants to play a card
     * @param point    is the pair of coordinates where the player wants to play the
     *                 card
     */
    public void play(String username, Point point) {
        try {
            model.play(username, point);
        } catch (IllegalStateOperationException | IllegalPlaceCardException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }

        model.getListeners().get(username).notifyListener(ListenerType.PLAYAREA, model);
        model.getListeners().get(username).notifyListener(ListenerType.HAND, model);
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.TURN, model));
    }

    /**
     * This method is invoked when the player wants to play the starter card. It
     * invokes the corresponding method of the model,
     * if the state of the game is not correct a specific object is sent, another
     * specific object is sent if the player have not
     * yet chosen the objective card. The listener for the play area, player score
     * and turn are notified
     *
     * @param username is the username of the client that wants to play the starter
     *                 card
     */
    public void playStarter(String username) {
        try {
            model.playStarter(username);
            ServerLog.gControllerWrite("Player" + username + " has played starter card", idGame);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj("You are in the wrong state"));
        } catch (ObjectiveCardNotChosenException e) {
            sendUpdateToClient(username,
                    new ShowInvalidActionObj("You must first choose your secret objective"));
        }

        model.getListeners().get(username).notifyListener(ListenerType.PLAYAREA, model);
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.TURN, model));
    }

    /**
     * This method is invoked when a player wants to select a card in its hand. It
     * invokes the corresponding
     * method of the model, if the state of the game is not the correct one a
     * ShowInvalidActionObj is sent to the client and
     * the same object is sent if the player wrote a non-coherent index for the
     * card. The hand's listener is notified
     *
     * @param username is the username of the client that wants to select a card in
     *                 its hand
     * @param index    is the value that specify which card in its hand needs to be
     *                 selected
     */
    public void selectCard(String username, int index) {
        try {
            model.setSelectCard(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj("You can't select a card"));
        } catch (WrongIndexSelectedCard e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.HAND, model));
    }

    /**
     * This method is invoked when a player wants to change the side of a card. It
     * invokes the
     * corresponding method of the model, if the state of the game is not the
     * correct one a ShowInvalidActionObj is sent to
     * the client. The hand listener is notified
     *
     * @param username is the username of the player that wants to change the side
     *                 of a card
     */
    public void changeSide(String username) {
        try {
            model.changeSide(username);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.HAND, model));
    }

    /**
     * This method is invoked when the player wants to change the side of the
     * starter card. The corresponding method of
     * the model is invoked, if the state of the game is not the correct one a
     * {@link ShowInvalidActionObj} is sent to the client.
     * The starter card listener is notified
     *
     * @param username is the username of the player that wants to change the siede
     *                 of the starter card
     */
    public void changeStarterSide(String username) {
        try {
            model.changStarterSide(username);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.STARTER_CARD, model));
    }

    /**
     * This method is invoked to send to the clients the updated info about the
     * client and their ready status.
     * It sends a {@link ShowInGamePlayerObj} to the client.
     */
    private void notifyListPlayers() {
        sendUpdateToClient(new ShowInGamePlayerObj(readyStatus));
    }

    /**
     * This method create a thread that, synchronizing on the clientListLock, sends
     * to all the clients a clientQueueObj
     * received as a parameter
     *
     * @param username          is the username of the client that is the receiver
     *                          of the object
     * @param clientQueueObject is the object to be sent to the client
     */
    private void sendUpdateToClient(String username, ClientQueueObject clientQueueObject) {
        new Thread(() -> {
            VirtualClient client;
            synchronized (clientListLock) {
                client = clientList.get(username);
            }
            try {
                client.sendCommand(clientQueueObject);
            } catch (RemoteException ignored) {

            }
        }).start();
    }

    /**
     * Sends an update, containing a specific ClientQueueObject to all clients.
     *
     * @param clientQueueObject The object containing the update information to be
     *                          sent to the clients.
     */
    private void sendUpdateToClient(ClientQueueObject clientQueueObject) {
        List<String> usernameList;
        synchronized (clientListLock) {
            usernameList = new ArrayList<>(clientList.keySet());
        }
        for (String username : usernameList) {
            sendUpdateToClient(username, clientQueueObject);
        }
    }

    /**
     * This method is invoked when a player needs to be disconnected from the game.
     * If the game is already started the
     * corresponding method of the model is invoked, in this case if the player is
     * the last one in the game the timer
     * schedulerLastPlayerConnected is started (if the timer expires the player wins
     * the match).
     * If the game is not yet started a QuitGameObj is sent to the client
     *
     * @param username is the username of the client that is disconnected
     */
    public void disconnectPlayer(String username) {
        if (model.isStarted()) {
            try {
                model.disconnectPlayer(username);
            } catch (LastPlayerRemainedException e) {
                schedulerLastPlayerConnected = Executors.newScheduledThreadPool(1);
                timerLastPlayerConnected(e.lastConnected);
            }
        } else {
            try {
                this.sendCommand(new QuitGameObj(username));
            } catch (RemoteException ignored) {
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
        if (model.isStarted()) {
            return model.getPlayerConnection().size();
        }
        int clientListSize;
        synchronized (clientListLock) {
            clientListSize = clientList.size();
        }
        return clientListSize;
    }

    /**
     * @return the game model reference
     */
    public GameModel getModel() {
        return model;
    }

    /**
     * @return the idGame
     */
    protected synchronized int getIdGame() {
        return idGame;
    }

    /**
     * This method is invoked by the disconnectPlayer if a single client remains
     * active in the game.
     * This method creates two runnable. The first is used to check how many clients
     * are connected to the game and
     * the second one is used to end the game after a fixed time of time if none
     * reconnected.
     * So if in a game all the players disconnect except for one, after a fixed
     * amount of time the only remaining player
     * wins the match
     *
     * @param lastPlayerConnected is the username of the last connected player in
     *                            the game
     */
    public void timerLastPlayerConnected(String lastPlayerConnected) {
        int period = 5;
        int totalTime = 30;
        final int[] remainingTime = { totalTime };

        Runnable periodiTask = () -> {
            int numberConnected = 0;
            synchronized (model.getPlayerConnection()) {
                for (String user : model.getPlayerConnection().keySet()) {
                    if (model.getPlayerConnection().get(user)) {
                        numberConnected++;
                    }
                }
            }
            if (numberConnected == 1) {
                ServerLog.gControllerWrite("no one has reconnected yet", idGame);
                sendUpdateToClient(lastPlayerConnected, new TimerLastPlayerConnectedObj(remainingTime[0]));
                remainingTime[0] -= period;
            }
        };

        Runnable finalTask = () -> {

            try {
                this.sendCommand(new EndGameOnePlayerLeftObj(lastPlayerConnected));
            } catch (RemoteException ignored) {
            }
            schedulerLastPlayerConnected.shutdownNow();
        };

        schedulerLastPlayerConnected.scheduleAtFixedRate(periodiTask, 0, period, TimeUnit.SECONDS);
        schedulerLastPlayerConnected.schedule(finalTask, totalTime, TimeUnit.SECONDS);
    }
}