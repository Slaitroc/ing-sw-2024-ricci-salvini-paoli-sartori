package it.polimi.ingsw.gc31.model.card;

import it.polimi.ingsw.gc31.model.strategies.Objective;

/**
 * This class represents an Objective Card in the game. It implements the Card interface.
 * The objective cards, at the end of the game, give points if the player meets the objective conditions.
 *
 * @author sslvo
 */
public class ObjectiveCard implements Card {
    private boolean side;
    private final int score;
    private final Objective objective;
    private final String dirImgFront;
    private final String dirImgBack;

    public ObjectiveCard(int score, Objective objective, String dirImgFront, String dirImgBack) {
        this.score = score;
        this.objective = objective;
        this.dirImgFront = dirImgFront;
        this.dirImgBack = dirImgBack;
        side = false;
    }

    /**
     * Retrieves the score of the card.
     *
     * @return The score of the card.
     */
    public int getScore() {
        return score;
    }

    /**
     * Retrieves the objective associated with the card.
     * The o
     *
     * @return The objective of the card.
     */
    public Objective getObjective() {
        return objective;
    }

    /**
     * Retrieves the value of the "side" field.
     *
     * @return The value of the "side" field. True if the front side of the card is active, false if the back side is active.
     */
    @Override
    public boolean getSide() {
        return side;
    }

    /**
     * Changes the active side of the card.
     * If the current side is true, it will change it to false.
     * If the current side is false, it will change it to true.
     */
    @Override
    public void changeSide() {
        side = !side;
    }

    /**
     * Retrieves the path of the image associated with the active side of the card.
     *
     * @return The path of the image.
     */
    @Override
    public String getImage() {
        if (side)
            return dirImgFront;
        else
            return dirImgBack;
    }

}
