package it.polimi.ingsw.gc31.model.enumeration;

/**
 * Represents the type of a card.
 */
public enum CardType {
    GOLD("GOLD"),
    RESOURCE("RESOURCE"),
    OBJECTIVE("OBJECTIVE"),
    STARTER("STARTER");

    private final String stringName;
    CardType(String stringName) {
        this.stringName = stringName;
    }

    @Override
    public String toString() {
        return stringName;
    }
}
