package it.polimi.ingsw.gc31.model.player;

import java.awt.*;

import it.polimi.ingsw.gc31.client_server.listeners.PlayerObservable;
import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.exceptions.FullHandException;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.InvalidCardDraw;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import javafx.util.Pair;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the game.
 * It manages the player's state, hand, score, and other game-related
 * attributes.
 */
public class Player extends PlayerObservable {

    private final Board board;
    private int selectedCard;
    private PlayableCard selectedStarterCard;
    private ObjectiveCard objectiveCard;
    private List<ObjectiveCard> objectiveCardToChoose;
    private final String username;
    private final PlayArea playArea;
    private final PawnColor pawnColor;
    protected final List<PlayableCard> hand;
    protected PlayerState inGameState;
    protected int score;

    /**
     * Constructor for the Player class.
     * It initializes the player's state, hand, score, and other attributes.
     *
     * @param pawnColor the color of the player's pawn.
     * @param username  the username of the player.
     * @param board     the game board.
     */
    public Player(PawnColor pawnColor, String username, Board board) {
        this.board = board;
        this.username = username;
        this.playArea = new PlayArea();
        this.inGameState = new Start();
        this.pawnColor = pawnColor;
        this.objectiveCardToChoose = new ArrayList<>();
        hand = new ArrayList<>();
        score = 0;
    }

    // PRIVATE METHODS

    /**
     * Method add the selected card to the player hand
     * Notice that the logic of this class is specified in the states
     * and the method is called by the six public drawing methods.
     * Here are written the exceptions messages
     *
     * @param card:   address of the card to add in hand
     * @param byDeck: boolean that specifies if the card is drawn from the deck
     */
    private boolean addToHand(PlayableCard card, Boolean byDeck) {
        try {
            inGameState.addToHand(card, this, byDeck);
            notifyPlayerHandListener(new Pair<>(username, hand));
            return true;
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " cannot draw in current state");
            e.getStackTrace();
            return false;
        } catch (FullHandException e) {
            System.out.println("Player " + username + "'s hand is full");
            e.getStackTrace();
            return false;
        } catch (InvalidCardDraw e) {
            System.out.println("Player " + username + " tried to draw an invalid card");
            e.getStackTrace();
            return false;
        }
    }

    // PUBLIC METHODS

    /**
     * Draws a gold card directly from the goldDeck and adds it to the player's
     * hand.
     *
     * @throws EmptyDeckException if the deck is empty.
     */

    // FIXME potrebbe esserci un problema perchè se addToHand non va a buon fine la carta pesccata finisce in un buco nero
    public boolean drawGold(int index) throws EmptyDeckException {
        Deck<PlayableCard> deck = board.getDeckGold();
        if (deck.peekCard() == null || deck.peekCard1() == null || deck.peekCard2() == null) {
            deck.replaceDeck(board.getDeckResource().getQueueDeck());
        }

        if (index == 0) return addToHand(deck.draw(), true);
        else if (index == 1) return addToHand(deck.getCard1(), false);
        else if (index == 2) return addToHand(deck.getCard2(), false);

        return false;
    }

    /**
     * Draws the first gold card and adds it to the player's hand.
     */
    public boolean drawGoldCard1() {
        Deck<PlayableCard> deck = board.getDeckGold();
        if (deck.peekCard1() == null) {
            deck.replaceDeck(board.getDeckResource().getQueueDeck());
        }
        return addToHand(deck.getCard1(), false);
    }

    /**
     * Draws the second gold card and adds it to the player's hand.
     */
    public boolean drawGoldCard2() {
        Deck<PlayableCard> deck = board.getDeckGold();
        if (deck.peekCard2() == null) {
            deck.replaceDeck(board.getDeckResource().getQueueDeck());
        }
        return addToHand(deck.getCard2(), false);

    }

    /**
     * Draws a resource card directly from the resourceDeck and adds it to the
     * player's hand.
     *
     * @throws EmptyDeckException if the deck is empty.
     */
    public boolean drawResource() throws EmptyDeckException {
        Deck<PlayableCard> deck = board.getDeckResource();
        if (deck.peekCard() == null) {
            deck.replaceDeck(board.getDeckGold().getQueueDeck());
        }
        return addToHand(deck.draw(), true);
    }

    /**
     * Draws the first resource card and adds it to the player's hand.
     */
    public boolean drawResourceCard1() {
        Deck<PlayableCard> deck = board.getDeckResource();
        if (deck.peekCard1() == null) {
            deck.replaceDeck(board.getDeckGold().getQueueDeck());
        }
        return addToHand(deck.getCard1(), false);
    }

    /**
     * Draws the second resource card and adds it to the player's hand.
     */
    public boolean drawResourceCard2() {
        Deck<PlayableCard> deck = board.getDeckResource();
        if (deck.peekCard2() == null) {
            deck.replaceDeck(board.getDeckGold().getQueueDeck());
        }
        return addToHand(deck.getCard2(), false);
    }

    /**
     * Basic repositioning of the card in hand temporarily implemented
     * with an input/output System
     * TODO change I/O System with what we will actually use
     */
    public void moveCardInHand() {
        try {
            inGameState.moveCardInHand(this);
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " not allowed to move cards in hand in current state");
            e.getStackTrace();
        }
    }

    /**
     * Method let the player place the selectedCard in the map
     *
     * @param point: coordinate of where in the map to place the card
     */
    public void play(Point point) throws IllegalStateOperationException {
        try {
            inGameState.play(point, this);
            notifyPlayAreaListener(
                    new Pair<>(username, new Pair<>(playArea.getPlacedCards(), playArea.getAchievedResources())));
            notifyPlayerHandListener(
                    new Pair<>(username, hand));
        } catch (IllegalStateOperationException e) {
            System.out.println("Player " + username + " not allowed to place cards in current state");
            throw new IllegalStateOperationException();
        }
    }

    /**
     * Method let the player place the starterCard in the map on position (0,0)
     */
    public void playStarter() {
        try {
            inGameState.playStarter(this);
            notifyPlayerScoreListener(new Pair<>(username, score));
            notifyPlayAreaListener(
                    new Pair<>(username, new Pair<>(playArea.getPlacedCards(), playArea.getAchievedResources())));
        } catch (IllegalStateOperationException e) {
            System.out.println("Player" + username + " not allowed to place the starter card in current state");
            e.getStackTrace();
        } catch (ObjectiveCardNotChosenException e) {
            System.out.println("Player " + username + " has not chosen an objective card yet");
            e.getStackTrace();
        }
    }

    /**
     *
     */
    public void chooseSecretObjective(int index) throws IllegalStateOperationException {
        inGameState.chooseSecretObjective(objectiveCardToChoose.get(index), this);
    }

    /**
     *
     */
    public void drawChooseObjectiveCards() {
        objectiveCardToChoose.add(board.getDeckObjective().draw());
        objectiveCardToChoose.add(board.getDeckObjective().draw());
        notifyPlayerChooseObjectiveCardListener(new Pair<>(objectiveCardToChoose.get(0),this.objectiveCardToChoose.get(1)));
    }

    /**
     *
     */
    public void addObjectiveCardToChoose(List<ObjectiveCard> cards) {
        objectiveCardToChoose = cards;
    }

    /**
     * Method to calculate ONLY the SECRET objective card of the player
     * and the COMMON objective card of the game (not the playable objectives)
     *
     * @param obj: Objective Card to calculate
     */
    public void calculateObjectiveCard(ObjectiveCard obj) {
        obj.getObjective().isObjectiveDone(playArea.getPlacedCards(), null, playArea.getAchievedResources());
    }

    // GETTERS

    public Board getBoard() {
        return this.board;
    }

    public PlayableCard getSelectedCard() {
        return hand.get(selectedCard);
    }

    public PlayableCard getStarterCard() {
        return this.selectedStarterCard;
    }

    public String getUsername() {
        return this.username;
    }

    public int getScore() {
        return this.score;
    }

    public PlayArea getPlayArea() {
        return this.playArea;
    }

    public List<PlayableCard> getHand() {
        return this.hand;
    }

    public ObjectiveCard getObjectiveCard() {
        return this.objectiveCard;
    }

    public PawnColor getPawnColor() {
        return this.pawnColor;
    }

    // SETTERS

    /**
     * Notice that the index is from 0 to 2
     *
     * @param selectedCard: index of the card in the hand
     */
    public void setSelectedCard(int selectedCard) {
        this.selectedCard = selectedCard;
    }

    public void setStarterCard() throws EmptyDeckException {
        this.selectedStarterCard = board.getDeckStarter().draw();
        notifyPlayerStarterCardListener(selectedStarterCard);
    }

    // NOTICE: This setter is not supposed to be called from anyone except the Start
    // state of the player
    protected void setObjectiveCard(ObjectiveCard card) {
        this.objectiveCard = card;
        notifyPlayerObjectiveCardListener(objectiveCard);
    }

    public void setInGameState(PlayerState inGameState) {
        this.inGameState = inGameState;
        notifyPlayerTurnListener(new Pair<>(username, infoState()));
    }

    public String infoState() {
        return this.inGameState.stateInfo();
    }

}
