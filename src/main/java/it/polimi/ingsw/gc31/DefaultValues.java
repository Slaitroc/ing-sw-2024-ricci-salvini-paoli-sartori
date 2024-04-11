package it.polimi.ingsw.gc31;

public class DefaultValues {
    public final static String GAME_CONTROLLER_MAP_KEY = "GC";

    public final static String RMI_SERVER_TAG = "[RMI server] ";
    public final static String TCP_SERVER_TAG = "[TCP server] ";
    public static final String CONTROLLER_TAG = "- [Controller]: ";
    public static final String MAINCONTROLLER_TAG = "- [MainController]: ";

    public final static String TUI_START_LINE_SYMBOL = "> ";
    public final static String TUI_TAG = "[TUI] ";

    public final static String DEFAULT_USERNAME = "New User";

    public static String mainControllerTag(String id) {
        return "- [MainController " + id + "]: ";
    }
}
