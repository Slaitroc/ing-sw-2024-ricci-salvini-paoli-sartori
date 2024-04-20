package it.polimi.ingsw.gc31;

import java.util.Map;

import it.polimi.ingsw.gc31.view.GUI.SceneTag;
import java.util.HashMap;

public class DefaultValues {

    // JSON directories
    public static final String DIRJsonGoldCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/GoldCard.json";
    public static final String DIRJsonResourceCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json";
    public static final String DIRJsonStarterCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/StarterCard.json";
    public static final String DIRJsonObjectiveCard = "src/main/resources/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json";

    // Server Log
    public final static String RMI_SERVER_TAG = "[RMI server] ";
    public final static String TCP_SERVER_TAG = "[TCP server] ";
    public static final String CONTROLLER_TAG = "\t[Controller]: ";
    public static final String PLAYER_CONTROLLER_TAG = "\t[PlayerController]: ";

    public static String gameControllerTag(String id) {
        return "\t[GameController " + id + "]: ";
    }

    public static String playerControllerTag(String name) {
        return "\t[PlayerController " + name + "]: ";
    }

    // TUI
    public final static String TUI_START_LINE_SYMBOL = "> ";
    public final static String TUI_TAG = "[TUI] ";
    public final static String STOP_CURRENT_TUI_STRING = "quit";

    // ANSI color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // GUI
    public final static String GUI_SCENE_startScene = "/it/polimi/ingsw/gc31/Views/startScene.fxml";
    public final static String GUI_SCENE_usernameScene = "/it/polimi/ingsw/gc31/Views/usernameScene.fxml";

    public static Map<SceneTag, String> getGuiFxmlScenes() {
        Map<SceneTag, String> sceneList = new HashMap<>();
        sceneList.put(SceneTag.START, GUI_SCENE_startScene);
        sceneList.put(SceneTag.USERNAME, GUI_SCENE_usernameScene);

        return sceneList;
    }

    // Model
    public final static String DEFAULT_USERNAME = "New User";

}
