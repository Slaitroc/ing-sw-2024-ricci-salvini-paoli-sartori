package it.polimi.ingsw.gc31;

public class DefaultValues {
    public static final String DIRJsonGoldCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json";
    public static final String DIRJsonResourceCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json";
    public static final String DIRJsonStarterCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/StarterCard.json";
    public static final String DIRJsonObjectiveCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json";

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
