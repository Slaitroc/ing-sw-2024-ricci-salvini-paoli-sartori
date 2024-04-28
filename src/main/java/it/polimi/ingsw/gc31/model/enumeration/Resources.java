package it.polimi.ingsw.gc31.model.enumeration;

/**
 * Enum that represents every color inside the game
 */
public enum Resources {
    ANIMAL("ANIMAL", "🐺"),
    INSECT("INSECT", "🦋"),
    PLANT("PLANT", "🌿"),
    MUSHROOM("MUSHROOM", "🍄"),
    FEATHER("FEATHER", "FE"),
    INK("INK", "IN"),
    SCROLL("SCROLL", "📜"),
    EMPTY("EMPTY", "  "),
    HIDDEN("HIDDEN", "  ");

    /**
     * it is the string representation of a Resources object
     */
    private final String stringName;
    private final String symbol;

    /**
     * when a Resources object is created the constructor automatically set the
     * string representation base on the value
     * of the object
     */
    Resources(String stringName, String symbol) {
        this.stringName = stringName;
        this.symbol = symbol;
    }

    /**
     * used to get the string representation of a Resources object
     */
    @Override
    public String toString() {
        return stringName;
    }

    public String getSymbol() {
        return symbol;
    }
}
