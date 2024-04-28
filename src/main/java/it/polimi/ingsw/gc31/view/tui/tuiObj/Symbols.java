package it.polimi.ingsw.gc31.view.tui.tuiObj;

public enum Symbols {
    ANIMAL("🐺"),
    INSECT("🦋"),
    PLANT("🌿"),
    MUSHROOM("🍄"),
    FEATHER("🪶"),
    INK("🖋️"),
    SCROLL("📜"),
    OBJ("🏆"); /* "╳" */

    /**
     * it is the string representation of a Resources object
     */
    private final String stringName;

    /**
     * when a Resources object is created the constructor automatically set the
     * string representation base on the value
     * of the object
     */
    Symbols(String stringName) {
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