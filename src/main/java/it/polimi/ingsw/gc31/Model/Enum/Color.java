package it.polimi.ingsw.gc31.Model.Enum;

/**
 * Enum that represents every color inside the game
 */
public enum Color {
    RED("Red"),
    GREEN("Green"),
    BLUE("Blue"),
    BLACK("Black"),
    YELLOW("Yellow"),
    PURPLE("Purple"),
    NOCOLOR("NoColor"),
    ;
    /**
     * it is the string representation of a Color object
     */
    private final String stringName;

    /**
     * when a Color object is created the construtor automatically set the string representation based on the value
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
