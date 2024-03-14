package it.polimi.ingsw.gc31.Model.Enum;

/**
 * Enum that represents every color inside the game
 */
public enum Resources {
    ANIMAL("Animal"),
    INSECT("Insect"),
    PLANT("Plant"),
    MUSHROOM("Mushroom"),
    FEATHER("Feather"),
    INK("Ink"),
    SCROLL("Scroll"),
    EMPTY("Empty"),
    HIDDEN("Hidden");
    /**
     * it is the string representation of a Resources object
     */
    private final String stringName;

    /**
     * when a Resources object is created the constructor automatically set the string representation base on the value
     * of the object
     */
    Resources(String stringName) {
        this.stringName = stringName;
    }

    /**
     * used to get the string representation of a Resources object
     */
    @Override
    public String toString() {
        return stringName;
    }
}
