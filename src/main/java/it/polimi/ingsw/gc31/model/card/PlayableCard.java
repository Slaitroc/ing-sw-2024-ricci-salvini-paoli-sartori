package it.polimi.ingsw.gc31.model.card;

import com.google.gson.JsonObject;

import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**
 * This class represents a generic card that can be placed on the
 * {@link it.polimi.ingsw.gc31.model.player.PlayArea}. It extends {@link Card}
 * All PlayableCard must extend this class.
 *
 * @author Christian Salvini
 */
public abstract class PlayableCard implements Card {
    /**
     * The front side of a Card
     */
    protected final CardFront front;
    /**
     * The back side of a Card
     */
    protected final CardBack back;
    /**
     * Side is a boolean parameter that represents which side of the card is active:
     * side = false → back is active
     * side = true → front is active
     */
    protected boolean side;
    /**
     * It represents the color of a card. It must be set by subclasses, and could be
     * set to null.
     * It is final because the color of the card can't change.
     */
    protected final Color color;

    /**
     * The constructor of the PlayableCard.
     */
    public PlayableCard(Color color, CardFront front, CardBack back) {
        this.front = front;
        this.back = back;
        this.side = false;
        this.color = color;
    }

    /**
     * @return the color of the card
     */
    public Color getColor() {
        return color;
    }

    /**
     * @param corner is the index (0 to 3) of the 4 corners of the card (central
     *               resources included from number 4 to 7)
     * @return False only if the checked corner is HIDDEN, True else
     */
    public boolean checkCorner(int corner) {
        if (side)
            return front.checkCorner(corner);
        else
            return back.checkCorner(corner);
    }

    public Resources coverCorner(int corner) {
        if (side)
            return front.coverCorner(corner);
        else
            return back.coverCorner(corner);
    }

    public List<Resources> getResources() {
        if (side)
            return front.getResources();
        else
            return back.getResources();
    }

    public Map<Resources, Integer> getRequirements() {
        if (side)
            return front.getRequirements();
        else
            return Collections.emptyMap();
    }

    @Override
    public boolean getSide() {
        return side;
    }

    @Override
    public void changeSide() {
        side = !side;
    }

    @Override
    public String getImage() {
        if (side)
            return front.getImage();
        else
            return back.getImage();
    }

    @Override
    public int getScore() {
        if (side)
            return front.getScore();
        else
            return 0;
    }

    @Override
    public Card deepCopy() {
        return null;
    }

    @Override
    public JsonObject frontSerializeToJson() {
        return front.serializeToJson();
    }

    @Override
    public JsonObject backSerializeToJson() {
        return back.serializeToJson();
    }
}
