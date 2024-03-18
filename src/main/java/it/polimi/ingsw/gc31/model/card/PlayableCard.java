package it.polimi.ingsw.gc31.model.card;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Color;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.Objective;

import java.util.Collections;
import java.util.List;
import java.util.Map;

/**w
 * This class represents a generic card that can be placed on the
 * {@link it.polimi.ingsw.gc31.model.player.PlayArea}. It extends {@link Card}
 * All PlayableCard must extend this class.
 *
 * @author Christian Salvini
 */
public abstract class PlayableCard extends Card {
    /**
     * It represents the color of a card. It must be set by subclasses, and could be
     * set to null.
     * It is final because the color of the card can't change.
     */
    protected final Color color;

    /**
     * The constructor of the class calls the constructor of the upperclass
     * {@link Card} that sets side to the
     * dafault value.
     */
    public PlayableCard(Color color, CardFront front, CardBack back) {
        super(front, back);
        this.color = color;
    }

    /**
     * @return the color of the card
     */
    public Color getColor() {
        return color;
    }

    /**
     *
     * @param corner
     * @return
     */
    public boolean checkCorner(int corner) {
        if (side)
            return front.checkCorner(corner);
        else
            return back.checkCorner(corner);
    }

    public void coverCorner(int corner) {
        if (side)
            front.coverCorner(corner);
        else
            back.coverCorner(corner);
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
}
