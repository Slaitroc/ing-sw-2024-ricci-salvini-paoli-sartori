package it.polimi.ingsw.gc31.model.player;

import java.awt.*;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.model.Board;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.PawnColor;

import java.util.ArrayList;
import java.util.List;

/**
 * This class represents a player in the game.
 * It manages the player's state, hand, score, and other game-related
 * attributes.
 */
public class Player{

    private final Board board;
    private int selectedCard;
    private PlayableCard selectedStarterCard;
    private ObjectiveCard objectiveCard;
    private final List<ObjectiveCard> objectiveCardToChoose;
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
     * @param deck the deck to draw from.
     *
     */
    private void addToHand(Deck<PlayableCard> deck,Deck<PlayableCard> subsistuteDeck, int index) throws IllegalStateOperationException{
        try {
            inGameState.addToHand(deck, subsistuteDeck, this, index);
        } catch (FullHandException e) {
            System.out.println("Player " + username + "'s hand is full");
        } catch (InvalidCardDraw e) {
            System.out.println("Player " + username + " tried to draw an invalid card");
        }
    }

    // PUBLIC METHODS

    /**
     * Draws a gold card directly from the goldDeck and adds it to the player's
     * hand.
     *
     * @throws EmptyDeckException if the deck is empty.
     */

    public boolean drawGold(int index) throws EmptyDeckException, IllegalStateOperationException {
        if (index < 0 || index > 2) return false;

        addToHand(board.getDeckGold(), board.getDeckResource(), index);
        return true;
    }

    /**
     * Draws a resource card directly from the resourceDeck and adds it to the
     * player's hand.
     *
     * @throws EmptyDeckException if the deck is empty.
     */
    public boolean drawResource(int index) throws EmptyDeckException, IllegalStateOperationException {
        if (index < 0 || index > 2) return false;

        addToHand(board.getDeckResource(), board.getDeckGold(), index);
        return true;
    }
    /**
     * Method let the player place the selectedCard in the map
     *
     * @param point: coordinate of where in the map to place the card
     */
    public void play(Point point) throws IllegalStateOperationException {
        inGameState.play(point, this);
        board.updateScore(username, score);
    }

    /**
     * Method let the player place the starterCard in the map on position (0,0)
     */
    public void playStarter() throws IllegalStateOperationException, ObjectiveCardNotChosenException {
        inGameState.playStarter(this);
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
    }

    public List<ObjectiveCard> getChooseSecretObjective() {
        return objectiveCardToChoose;
    }

    public void calculateObjectiveCard() {
        score += objectiveCard.getObjective().isObjectiveDone(playArea.getPlacedCards(), null, playArea.getAchievedResources());
    }
    /**
     *
     * Method to calculate the COMMON objective card of the game
     *
     * @param obj: Objective Card to calculate
     */
    public void calculateObjectiveCard(ObjectiveCard obj) {
        score += obj.getObjective().isObjectiveDone(playArea.getPlacedCards(), null, playArea.getAchievedResources());
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

    public int getIndexSelectedCard() {
        return this.selectedCard;
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

    // SETTERS

    /**
     * Notice that the index is from 0 to 2
     *
     * @param selectedCard: index of the card in the hand
     */
    public void setSelectedCard(int selectedCard) throws WrongIndexSelectedCard {
        if (selectedCard < 0 || selectedCard >= this.hand.size()) {
            throw new WrongIndexSelectedCard();
        }
        this.selectedCard = selectedCard;
    }

    public void setStarterCard() throws EmptyDeckException {
        this.selectedStarterCard = board.getDeckStarter().draw();
    }

    public void changeSide() {
        hand.get(selectedCard).changeSide();
    }
    public void changeStarterSide() {
        selectedStarterCard.changeSide();
    }

    // NOTICE: This setter is not supposed to be called from anyone except the Start
    // state of the player
    protected void setObjectiveCard(ObjectiveCard card) {
        this.objectiveCard = card;
    }

    public void setInGameState(PlayerState inGameState) {
        this.inGameState = inGameState;
    }

    public String infoState() {
        return this.inGameState.stateInfo();
    }
}
