package it.polimi.ingsw.gc31.utility;

import java.util.Map;

import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.view.gui.SceneTag;

import java.util.HashMap;

public class DV {

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
    public final static String GUI_SCENE_startScene = "/it/polimi/ingsw/gc31/fxml/startScene.fxml";
    public final static String GUI_SCENE_usernameScene = "/it/polimi/ingsw/gc31/fxml/usernameScene.fxml";
    public final static String GUI_SCENE_lobbyScene = "/it/polimi/ingsw/gc31/fxml/lobbyScene.fxml";
    public final static String GUI_SCENE_mainMenuScene = "/it/polimi/ingsw/gc31/fxml/mainMenuScene.fxml";
    public final static String GUI_SCENE_ruleBookScene = "/it/polimi/ingsw/gc31/fxml/ruleBookScene.fxml";
    public final static String GUI_SCENE_gameListScene = "/it/polimi/ingsw/gc31/fxml/gameListScene.fxml";
    public final static String GUI_SCENE_inGameScene = "/it/polimi/ingsw/gc31/fxml/inGameScene.fxml";

    public static Map<SceneTag, String> getGuiFxmlScenes() {
        Map<SceneTag, String> sceneList = new HashMap<>();
        sceneList.put(SceneTag.START, GUI_SCENE_startScene);
        sceneList.put(SceneTag.USERNAME, GUI_SCENE_usernameScene);
        sceneList.put(SceneTag.LOBBY, GUI_SCENE_lobbyScene);
        sceneList.put(SceneTag.MAINMENU, GUI_SCENE_mainMenuScene);
        sceneList.put(SceneTag.RULEBOOK, GUI_SCENE_ruleBookScene);
        sceneList.put(SceneTag.GAMELIST, GUI_SCENE_gameListScene);
        sceneList.put(SceneTag.GAME, GUI_SCENE_inGameScene);

        return sceneList;
    }

    public final static int[] RGB_COLOR_RED = { 204, 76, 67 };
    public final static int[] RGB_COLOR_GREEN = { 73, 184, 105 };
    public final static int[] RGB_COLOR_BLUE = { 114, 202, 203 };
    public final static int[] RBG_COLOR_PURPLE = { 165, 85, 158 };
    public final static int[] RGB_COLOR_CORNER = { 223, 215, 176 };
    public final static int[] RGB_COLOR_STARTER = { 189, 175, 127 };

    public static int[] getRgbColor(CardColor color) {
        switch (color) {
            case RED:
                return RGB_COLOR_RED;
            case GREEN:
                return RGB_COLOR_GREEN;
            case BLUE:
                return RGB_COLOR_BLUE;
            case PURPLE:
                return RBG_COLOR_PURPLE;
            default:
                return RGB_COLOR_STARTER;
        }
    }

    // Model
    public final static String DEFAULT_USERNAME = "New User";

    // NETWORK
    public final static String RECIPIENT_CONTROLLER = "controller";
    public final static String RECIPIENT_GAME_CONTROLLER = "gameController";
    public static final String RECIPIENT_HEARTBEAT = "HEARTBEAT";
    public final static int RMI_PORT = 1100;
    public final static int TCP_PORT = 1200;

    // Token
    public final static int defaultToken = 0000;

}
