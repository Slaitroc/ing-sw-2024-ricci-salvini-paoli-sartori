package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
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
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.CreateReMatch;
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
     * Reference to the Game model, containing the cards, the players and the playAreas
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

    private final int maxNumberPlayers;
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
//        this.rematchAnswers = 0;
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
    public void reJoinGame(String username, VirtualClient newClient) throws RemoteException {
        // TODO controllare se il client era presente nella lista? oppure viene fatto
        // nel controller
        // TODO cosa fare con readyStatus?
        // the old client is replaced with the new one
        synchronized (clientListLock) {
            if (clientList.containsKey(username)) {
                schedulerLastPlayerConnected.shutdownNow();
                clientList.put(username, newClient);
//                model.setGameState();
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
     * will not have the possibility to rejoin the game. If the game was not yet started, the player will
     * quit the game with the possibility to rejoin the same match or another one
     *
     * @param username of the player that quitted
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
                    "Player " + username + " has quited from the game, but the game has already started", idGame);
            disconnectPlayer(username);
            model.notifyAllGameListeners();
        } else {
            ServerLog.gControllerWrite(
                    "Player " + username + " has quited from the game, but the game has not started yet", idGame);
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
     * <p>Checks if two conditions are met:</p>
     * <ul>
     * <li>If all the player in the lobby are ready</li>
     * <li>If the maximum number of the players for the game has been reached</li>
     * </ul>
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
            } catch (IllegalStateOperationException e) {
                // throw new RuntimeException(e);
            }
        }
    }

    /**
     * Same method used to send a public or a private message
     *
     * @param message ClientQueueObject containing fromUsername, toUsername (for PM) and message content
     */
    public void sendChatMessage(NewChatMessage message) {
        sendUpdateToClient(message);
    }

    /**
     * Draws a gold card from the deck for the player and then shows the player's
     * hand.
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

    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }

        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.PLAYER_SCORE, model));
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.OBJECTIVE_CARD, model));
    }

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

    public void changeSide(String username) {
        try {
            model.changeSide(username);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.HAND, model));
    }

    public void changeStarterSide(String username) {
        try {
            model.changStarterSide(username);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
        model.getListeners().values().forEach(listener -> listener.notifyListener(ListenerType.STARTER_CARD, model));
    }

    private void notifyListPlayers() {
        sendUpdateToClient(new ShowInGamePlayerObj(readyStatus));
    }

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
     * @param clientQueueObject The object containing the update information to be sent to the clients.
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

    protected synchronized int getIdGame() {
        return idGame;
    }

//    // REMATCH RESOURCES
//    /**
//     * The map contains the boolean value representing if the specific player wants
//     * to rematch
//     */
//    protected Map<String, Boolean> rematchPlayers;
//    protected int rematchAnswers;
//    protected Timer rematchTimer;
//
//    /**
//     * This method is invoked when the first response arrives from a player. A map
//     * for the response (rematchPlayers) is created initializing
//     * the values to null and a timer is created. If some responses have not been
//     * received when the timer expire their value
//     * is assumed to be false. A new game is created only when the GameController
//     * receives all the responses (or the timer expires)
//     */
//    public void startRematchTimer() {
//        GameController gc = this;
//        rematchPlayers = new HashMap<>();
//        for (String username : clientList.keySet()) {
//            rematchPlayers.put(username, null);
//        }
//
//        rematchTimer = new Timer(true);
//        rematchTimer.schedule(new TimerTask() {
//            @Override
//            public void run() {
//                for (String username : rematchPlayers.keySet()) {
//                    if ((rematchPlayers.get(username)).equals(null))
//                        rematchPlayers.replace(username, false);
//                }
//
//                // create a bew match
//                try {
//                    gc.startRematch();
//                } catch (RemoteException e) {
//                    ServerLog.gControllerWrite("An error occurred creating a new " + idGame + " game [Rematch Error]",
//                            idGame);
//                    e.printStackTrace();
//                }
//            }
//        }, 120000);
//    }
//
//    /**
//     * This method is invoked on the execution of the AnotherMatchResponseObj. Based
//     * on the response obtained the GameController knows
//     * if the client wants to rematch or not.
//     *
//     * @param username       is the username of the player giving its response
//     * @param wantsToRematch is the string representing the answer
//     */
//    public void anotherMatch(String username, Boolean wantsToRematch) {
//        System.out.println(username + " player " + wantsToRematch);
//        // As soon as the first response is received the timer is created
//        if (rematchAnswers == 0)
//            startRematchTimer();
//
//        // The boolean value in the rematchPlayers is updated and the number of
//        // responses received is incremented
//        rematchPlayers.replace(username, wantsToRematch);
//        rematchAnswers++;
//
//        // If all the players responses are received the timer is cancelled (useless)
//        // and a new match is created
//        if (rematchAnswers == rematchPlayers.size()) {
//            rematchTimer.cancel();
//
//            // Firstly the number of players that wants a rematch is counted, if it <2 a new
//            // game can't be created and the
//            // player is disconnected
//            int count = 0;
//            for (String user : rematchPlayers.keySet())
//                if ((rematchPlayers.get(user)).equals(true))
//                    count++;
//
//            // If the number of players that wants a rematch is >1 a new game is created
//            if (count > 1) {
//                try {
//                    this.startRematch();
//                } catch (RemoteException e) {
//                    ServerLog.gControllerWrite("An error occurred creating a new " + idGame + " game [Rematch Error]",
//                            idGame);
//                    e.printStackTrace();
//                }
//
//                // If the number of players that wants a rematch is 1 or 0 a new game can't be
//                // created
//                // and all the players are disconnected. At this point the gameController is
//                // refereeing
//                // an empty game, so it's now useless and its reference is removed from the
//                // controller
//            } else {
//                for (String user : rematchPlayers.keySet()) {
//
//                    try {
//                        Controller.getController().quitGame(user, clientList.get(user));
//                        clientList.remove(user);
//                        readyStatus.remove(user);
//                        Controller.getController().gameControlList.remove(this);
//                    } catch (RemoteException e) {
//                        ServerLog.gControllerWrite("The client " + user + "couldn't be disconnected from the game",
//                                idGame);
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * The method is invoked by either the startRematchTimer or the anotherMatch
//     * method. This method modified all the
//     * values contained in the gameController accordingly to the remaining player in
//     * the game. The maxNumberPlayer is
//     * modified and all the players that doesn't want to rematch are removed from
//     * all the maps
//     */
//    protected void startRematch() throws RemoteException {
//
//
//
//        Map<String, VirtualClient> temp = new HashMap<>();
//        for (String username : rematchPlayers.keySet()) {
//            if (rematchPlayers.get(username)) {
//                temp.put(username, clientList.get(username));
//            }
//        }
//
//        Controller.getController().sendCommand(new CreateReMatch(temp));
//        // The callsList is re-initialized at the start of the new game
////        synchronized (this.callsList) {
////            this.callsList.clear();
////
////            // For every player:
////            // if the player wants to rematch the new game the playersInNewMatch is
////            // increased and the readyStatus is set to false
////            // otherwise the client doesn't want to rematch, so it is removed from the maps
////            // The final value of playersInNewMatch is the updated value of
////            // maxNumbersPlayers
////            int playersInNewMatch = 0;
////            for (String username : rematchPlayers.keySet()) {
////                if ((rematchPlayers.get(username)).equals(true)) {
////                    playersInNewMatch++;
////                    readyStatus.replace(username, false);
////                } else {
////                    Controller.getController().quitGame(username, clientList.get(username));
////                    readyStatus.remove(username);
////                    clientList.remove(username);
////                }
////            }
////            this.maxNumberPlayers = playersInNewMatch;
////            this.rematchAnswers = 0;
////
////            // In the end a new gameModel is created and notify is sent to all the players
////            this.model = new GameModel(clientListLock, idGame);
////        }
////
////        notifyListPlayers();
//    }

    public void timerLastPlayerConnected(String lastPlayerConnected) {
        int period = 5;
        int totalTime = 30;
        final int[] remainingTime = { totalTime };
        // final GameModelState savedGameModelState = gameModelState;

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
            // else {
            // // FIXME funziona????????
            // try {
            // this.sendCommand(new RestartGameOnePlayerLeftObj(savedGameModelState));
            // } catch (RemoteException ignored) {
            // }
            // ServerLog.gControllerWrite("Someone has reconnected", idGame);
            // schedulerLastPlayerConnected.shutdownNow();
            // }
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