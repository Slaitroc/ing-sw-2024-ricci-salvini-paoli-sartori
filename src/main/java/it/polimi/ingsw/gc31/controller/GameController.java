package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.*;
import java.util.List;
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
    protected GameModel model;
    // FIXME problema di sincronizzazione per la clientList
    // FIXME ho campiato clientList in cuncurrentList così che mi ci posso sincronizzare
    // aggiungre lista di stringhe per tenere l'ordine delle stringhe nel caso in cui serva, tipo mandare la lista dei player
    protected final Map<String, VirtualClient> clientList;
    protected Object clientListLock = new Object();
//    private final List<String>  clientListOrder;
//    @SuppressWarnings("unused")
    private int maxNumberPlayers;
    private int idGame;
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
        this.rematchAnswers = 0;
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

    // un giocatore può riconettersi alla partita solo se si è disconnesso per un
    // problema di rete
    // se il virtual client del giocatore che vuole riconettersi non è presente
    // nella mappa dei virtualclient
    // vuol dire che si è disconesso usando il tasto quit e quindi non può
    // riconettersi.
    // se il giocatore si era disconnesso per un problema di rete nella lobby allora
    // entra come
    public void reJoinGame(String username, VirtualClient newClient) throws RemoteException {
        // TODO controllare se il client era presente nella lista? oppure viene fatto
        // nel controller
        // TODO cosa fare con readyStatus?
        // the old client is replaced with the new one
        synchronized (clientListLock) {
            if (clientList.containsKey(username)) {
                clientList.put(username, newClient);
                model.reconnectPlayer(username);
                newClient.setGameController(this);
                newClient.sendCommand(new JoinedToGameObj(idGame, getMaxNumberPlayers()));
                ServerLog.gControllerWrite("Welcome back "+username+"!", idGame);
            } else {
                ServerLog.gControllerWrite("C'è stato qualche problema con la rejoin di " + username, idGame);
            }
        }
    }

    // se un giocatore si disconnette quando la partita è già iniziata non ha la
    // possibilità di rientrare
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
                synchronized (clientListLock) {
                    model.initGame(clientList, clientListLock);
                }
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
        int clientListSize;
        synchronized (clientListLock) {
            clientListSize = clientList.size();
        }
        return clientListSize;
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
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
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
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void chooseSecretObjective(String username, Integer index) {
        try {
            model.chooseSecretObjective(username, index);
        } catch (IllegalStateOperationException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
    }

    public void play(String username, Point point) {
        try {
            model.play(username, point);
        } catch (IllegalStateOperationException | IllegalPlaceCardException e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
        }
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
    }

    public void selectCard(String username, int index) {
        try {
            model.setSelectCard(username, index);
        } catch (IllegalStateOperationException e) {
            // TODO gestire meglio messaggio di questa eccezione
            sendUpdateToClient(username, new ShowInvalidActionObj("You can't select a card"));
        } catch (WrongIndexSelectedCard e) {
            sendUpdateToClient(username, new ShowInvalidActionObj(e.getMessage()));
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
        model.disconnectPlayer(username);
        // Controller.getController().disconnect(clientList.get(username), username,
        // idGame, ); // FIX @AleSarto mettila nel
        // // bruteforcing di
        // // checkheartbeat
    }

    public GameModel getModel() {
        return model;
    }

    // FIXME non sincronizzato
    protected int getIdGame(){ return idGame; }

    //REMATCH RESOURCES
    /**
     * The map contains the boolean value representing if the specific player wants to rematch
     */
    protected Map<String, Boolean> rematchPlayers;
    protected int rematchAnswers;
    protected Timer rematchTimer;

    /**
     * This method is invoked when the first response arrives from a player. A map for the response (rematchPlayers) is created initializing
     * the values to null and a timer is created. If some responses have not been received when the timer expire their value
     * is assumed to be false. A new game is created only when the GameController receives all the responses (or the timer expires)
     */
    public void startRematchTimer(){
        GameController gc = this;
        rematchPlayers = new HashMap<>();
        for(String username : clientList.keySet()){
            rematchPlayers.put(username, null);
        }

        rematchTimer = new Timer(true);
        rematchTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                for(String username : rematchPlayers.keySet()){
                    if ( (rematchPlayers.get(username)).equals(null) )
                        rematchPlayers.replace(username, false);
                }

                //create a bew match
                try {
                    gc.startRematch();
                } catch (RemoteException e) {
                    ServerLog.gControllerWrite("An error occurred creating a new " + idGame + " game [Rematch Error]", idGame);
                    e.printStackTrace();
                }
            }
        }, 60000);
    }

    /**
     * This method is invoked on the execution of the AnotherMatchResponseObj. Based on the response obtained the GameController knows
     * if the client wants to rematch or not.
     *
     * @param username is the username of the player giving it's response
     * @param wantsToRematch is the string representing the answer
     */
    public void anotherMatch(String username, Boolean wantsToRematch){
        //As soon as the first response is received the timer is created
        if (rematchAnswers == 0)
            startRematchTimer();

        //The boolean value in the rematchPlayers is updated and the number of responses received is incremented
        rematchPlayers.replace(username, wantsToRematch);
        rematchAnswers++;

        //If all the players responses are received the timer is cancelled (useless) and a new match is created
        if (rematchAnswers == rematchPlayers.size()) {
            rematchTimer.cancel();
            try {
                this.startRematch();
            } catch (RemoteException e) {
                ServerLog.gControllerWrite("An error occurred creating a new " + idGame + " game [Rematch Error]", idGame);
                e.printStackTrace();
            }
        }
    }

    /**
     * The method is invoked by either the startRematchTimer or the anotherMatch method. This method modified all the
     * values contained in the gameController accordingly to the remaining player in the game. The maxNumberPlayer is
     * modified and all the players that doesn't want to rematch are removed from all the maps
     */
    private void startRematch() throws RemoteException {
        //The callsList is re-initialized at the start of the new game
        synchronized (this.callsList) {
            this.callsList.clear();

        //For every player:
        // if the player wants to rematch the new game the playersInNewMatch is increased and the readyStatus is set to false
        // otherwise the client doesn't want to rematch so it is removed from the maps
        //The final value of playersInNewMatch is the updated value of maxNumbersPlayers
        int playersInNewMatch = 0;
        for(String username : rematchPlayers.keySet()){
            if ((rematchPlayers.get(username)).equals(true)) {
                playersInNewMatch++;
                readyStatus.replace(username, false);
            } else {
                Controller.getController().quitGame(username, clientList.get(username));
                readyStatus.remove(username);
                clientList.remove(username);

            }
        }
        this.maxNumberPlayers = playersInNewMatch;
        this.rematchAnswers = 0;

        //In the end a new gameModel is created and a notify is sent to all the players
        this.model = new GameModel(idGame);
        }

        notifyListPlayers();
    }
}
