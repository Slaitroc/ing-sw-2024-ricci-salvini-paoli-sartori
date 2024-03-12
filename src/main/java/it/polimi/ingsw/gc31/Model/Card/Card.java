package it.polimi.ingsw.gc31.Model.Card;

import it.polimi.ingsw.gc31.Model.Enum.Color;
import it.polimi.ingsw.gc31.Model.Enum.Resources;

import java.util.*;

public abstract class Card{
    protected CardFront front;
    protected CardBack back;
    protected boolean side;
    protected Color color;

    public Card() {
        side = false;
    }

    public void changeSide() {
        if (side) side = false;
        else side = true;
    }
    public boolean getSide() {
        return side;
    }
}
