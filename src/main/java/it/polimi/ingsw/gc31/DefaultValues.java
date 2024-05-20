package it.polimi.ingsw.gc31;

import java.util.Map;

import it.polimi.ingsw.gc31.view.gui.SceneTag;

import java.util.HashMap;

public class DefaultValues {

    // JSON directories
    public static final String DIRJsonGoldCard = "/it/polimi/ingsw/gc31/CardsJson/GoldCard.json";
    public static final String DIRJsonResourceCard = "/it/polimi/ingsw/gc31/CardsJson/ResourceCard.json";
    public static final String DIRJsonStarterCard = "/it/polimi/ingsw/gc31/CardsJson/StarterCard.json";
    public static final String DIRJsonObjectiveCard = "/it/polimi/ingsw/gc31/CardsJson/ObjectiveCard.json";

    // Server Log
    public final static String RMI_SERVER_TAG = "[RMI server] ";
    public final static String SERVER_TAG = "[Server] ";
    public final static String TCP_SERVER_TAG = "[TCP server] ";
    public static final String CONTROLLER_TAG = "\t[Controller]: ";

    public static String gameControllerTag(String id) {
        return "\t[GameController " + id + "]: ";
    }

    public static String playerControllerTag(String name) {
        return "\t[PlayerController " + name + "]: ";
    }

    // ANSI color
    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    // TUI
    public final static String TUI_START_LINE_SYMBOL = "> ";
    public final static String TUI_TAG = "[TUI] ";

    // TUIobj
    public static final int cardHeight = 5;
    public static final int cardWidth = 14;

    public static final int width_intersect = 3;
    public static final int height_intersect = 2;;

    public static final int height_shift = cardHeight - height_intersect;
    public static final int width_shift = cardWidth - width_intersect;

    // GUI
    public final static String GUI_SCENE_startScene = "/it/polimi/ingsw/gc31/Views/startScene.fxml";
    public final static String GUI_SCENE_usernameScene = "/it/polimi/ingsw/gc31/Views/usernameScene.fxml";
    public final static String GUI_SCENE_lobbyScene = "/it/polimi/ingsw/gc31/Views/lobbyScene.fxml";

    public static Map<SceneTag, String> getGuiFxmlScenes() {
        Map<SceneTag, String> sceneList = new HashMap<>();
        sceneList.put(SceneTag.START, GUI_SCENE_startScene);
        sceneList.put(SceneTag.USERNAME, GUI_SCENE_usernameScene);
        sceneList.put(SceneTag.LOBBY, GUI_SCENE_lobbyScene);

        return sceneList;
    }

    // Model
    public final static String DEFAULT_USERNAME = "New User";

    // NETWORK
    public final static String RECIPIENT_CONTROLLER = "controller";
    public final static String RECIPIENT_GAME_CONTROLLER = "gameController";
    public final static String RECIPIENT_UI = "ui";
    public final static String RECIPIENT_VC = "virtualClient";
    public final static String RECIPIENT_CC = "clientCommands";

}
