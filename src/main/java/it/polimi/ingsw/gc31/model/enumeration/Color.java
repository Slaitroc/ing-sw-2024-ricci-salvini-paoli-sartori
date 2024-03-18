package it.polimi.ingsw.gc31.model.enumeration;

/**
 * Enum that represents every color inside the game
 */
public enum Color {
    RED("RED"),
    GREEN("GREEN"),
    BLUE("BLUE"),
    BLACK("BLACK"),
    YELLOW("YELLOW"),
    PURPLE("PURPLE"),
    NOCOLOR("NOCOLOR"),
    ;
    /**
     * it is the string representation of a Color object
     */
    private final String stringName;

    /**
     * when a Color object is created the constructor automatically set the string representation based on the value
     * of the object
     */
    Color(String stringName) {
        this.stringName = stringName;
    }

    /**
     * used to get the string representation of a Color object
     * @return stringName
     */
    @Override
    public String toString() {
        return stringName;
    }
}
