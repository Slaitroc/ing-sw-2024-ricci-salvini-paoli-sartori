package it.polimi.ingsw.gc31.controller;

import java.awt.*;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.LinkedBlockingQueue;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.QueueObject;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.GameState;
import it.polimi.ingsw.gc31.model.player.NotPlaced;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;

/**
 * This class is the controller of one single game.
 * It manages the gameModel and the game states.
 */
public class GameController extends UnicastRemoteObject implements IGameController {
    private final GameModel model;
    private Map<String, Player> playerList;
    private final Map<String, VirtualClient> clientList;
    private final Gson gsonCard/*, gsonObjective*/;
    private final int maxNumberPlayers;
    private final int idGame;
    boolean lastTurn = false;
    private LinkedBlockingQueue<QueueObject> callsList;

    /**
     * Constructor for the GameController class.
     * It initializes the game model, players, clientList, and game states.
     *
     * @param username         the username of the player.
     * @param client           the client of the player.
     * @param maxNumberPlayers the maximum number of players.
     * @param idGame           the id of the game.
     */
    public GameController(String username, VirtualClient client, int maxNumberPlayers, int idGame) throws RemoteException {
        this.model = new GameModel();
        this.callsList = new LinkedBlockingQueue<>();
        this.maxNumberPlayers = maxNumberPlayers;
        this.idGame = idGame;
        this.playerList = new HashMap<>();
        this.clientList = new HashMap<>();
        this.clientList.put(username, client);
        /*gsonObjective = new GsonBuilder()
                .registerTypeAdapter(ObjectiveCard.class, new ObjectiveCardAdapter())
                .create();*/
        gsonCard = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();

        new Thread(this::executor);
    }

    private void addQueueObj(QueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
        }
    }

    private void executor() {
        QueueObject action;
        while (true) {
            synchronized (callsList) {
                action = callsList.poll();
            }
            action.execute();
        }
        // TODO ciclo da terminare alla fine del gioco altrimenti diventa demoooone
        // uuuuhhhhh
    }

    /**
     * Allows a player to join the game.
     *
     * @param username the username of the player.
     * @param client   the client of the player.
     */
    public void joinGame(String username, VirtualClient client) throws RemoteException {
        clientList.put(username, client);
        // TODO mandare messaggio al client di connessione al server
        if (maxNumberPlayers == this.clientList.size()) {
            gameControllerWrite("The number of players for the game " + maxNumberPlayers + " has been reached");
            try {
                initGame(); //somewhere it's supposed to start the game
            } catch (IllegalStateOperationException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * Initializes the game by creating players and dealing cards.
     * It also sets the player controller for each client.
     */
    public void initGame() throws RemoteException, IllegalStateOperationException {
        if(model.getGameState() == GameState.SETUP){
            playerList = model.createPlayers(clientList.keySet()); // Here the players are created and added to the playerList
            model.setObjectives(); // Here the common goals are initialized
            model.initSecretObj(); // Here the secret goals are drawn
            for (Player player : playerList.values()) {
                player.setStarterCard(); // Here the starter cards are drawn
                player.drawResource();
                player.drawResource();
                player.drawGold(); // Here the player hands are initialized
                //showHand(player); // Here the player's hands are shown
                //showObjectives(playerList.get(player)); // Here the common goals are supposed to be shown :)
            }
            model.getBoard().getDeckGold().refill(); // Here the GoldCard1 and GoldCard2 are drawn on the board
            model.getBoard().getDeckResource().refill(); // Here the ResourceCard1 and ResourceCard2 are drawn on the board
            model.startGame();
        }
        else {
            System.out.println("Failed to initialize game.");
            throw new RemoteException();
        }

        // TODO mandare messaggio al client di inizio partita
        // for (VirtualClient player: clientList.values()) {
        // player.sendMessage("[GameController] la partita è iniziata");
        // }
    }

    /*TODO Do I use this and create all the initStarters etc. methods in the GameController,
              or do I use the methods in the GameModel and call them from the GameController?*/

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

    /*public Player getPlayer(String username) {
        return playerList.get(username);
    }*/

    /**
     * Writes a message to the game controller.
     *
     * @param text the message to write.
     */
    private void gameControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_PURPLE
                + DefaultValues.gameControllerTag(String.valueOf(idGame)) + DefaultValues.ANSI_RESET + text);
    }

    //WARNING: methods receive username in input, instead of using model.currPlayingPlayer.drawGold() etc.
    // because otherwise clients could play the turn of others

    /**
     * Draws a gold card from the deck for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawGold(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawGold()) {
            showCard(player.getHand().getLast(), player);
            endTurn();
        }
    }

    /**
     * Draws the first gold card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawGoldCard1(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawGoldCard1()){
            showCard(player.getHand().getLast(), player);
            endTurn();
        }
    }

    /**
     * Draws the second gold card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawGoldCard2(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawGoldCard2()){
            showCard(player.getHand().getLast(), player);
            endTurn();
        }
    }

    /**
     * Draws a resource card from the deck for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResource(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawResource()){
            showCard(player.getHand().getLast(), player);
            endTurn();
        }
    }

    /**
     * Draws the first resource card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResourceCard1(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawResourceCard1()){
            showCard(player.getHand().getLast(), player);
            endTurn();
        }
    }

    /**
     * Draws the second resource card for the player and then shows the player's hand.
     *
     * @throws RemoteException If a remote invocation error occurs.
     */
    @Override
    public void drawResourceCard2(String username) throws RemoteException {
        Player player = playerList.get(username);
        if(player.drawResourceCard2()){
            endTurn();
        }
    }

    @Override
    public void chooseSecretObjective1(String username) {
        model.setPlayerObjective(username, 1);
    }

    @Override
    public void chooseSecretObjective2(String username) {
        model.setPlayerObjective(username, 2);
    }

    @Override
    public void play(String username, int x, int y) {
        Player player = playerList.get(username);
        player.play(new Point(x, y));
    }

    @Override
    public void playStarter(String username) {
        Player player = playerList.get(username);
        player.playStarter();
    }

    @Override
    public void changeSide(String username) {
        Player player = playerList.get(username);
        player.getSelectedCard().changeSide();
    }

    @Override
    public void selectCard(String username, int index) {
        Player player = playerList.get(username);
        player.setSelectedCard(index);
    }

    /*@Override
    public void play(String username, Point point) {
        Player player = playerList.get(username);
        player.play(point);
    }*/

    /*@Override
    public void selectCard(String username, int index) {
        Player player = playerList.get(username);
        player.setSelectedCard(index);
    }*/

    //PRIVATE METHODS:

    /**
     * This method is used to show a card to a player.
     *
     * @param card  the card to be shown.
     * @param player the player to whom the card is to be shown.
     */
    private void showCard(Card card, Player player) {
        List<String> res = new ArrayList<>();
        res.add(gsonCard.toJson(card, Card.class));
        try {
            clientList.get(player.getUsername()).showCards(res);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to show the player's hand.
     * It first retrieves the player's hand, which is a list of PlayableCard objects.
     * Then, it creates a new list of strings, where each string is a JSON representation of a PlayableCard.
     * Finally, it sends this list of strings to the client associated with the player, using the showHand method.
     * If a RemoteException is thrown during this process, it is caught and wrapped in a RuntimeException.
     *
     * @param player The player whose hand is to be shown.
     */
    private void showHand(Player player) {
        List<PlayableCard> hand = player.getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card : hand) {
            res.add(gsonCard.toJson(card, PlayableCard.class));
        }
        try {
            clientList.get(player.getUsername()).showCards(res);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * This method is used to end the turn of a player.
     * It also
     */
    private void endTurn() {
        detectEndGame();
        model.setNextPlayingPlayer();
        model.getCurrPlayingPlayer().setInGameState(new NotPlaced());
    }

    /**
     * This method is used to detect when a player reaches 20 points.
     */
    private void detectEndGame() {
        if (model.getCurrPlayingPlayer().getScore() >= 20) {
            lastTurn = true;
            model.startLastTurn();
        }
    }

    public GameModel getModel() {
        return model;
    }
}
