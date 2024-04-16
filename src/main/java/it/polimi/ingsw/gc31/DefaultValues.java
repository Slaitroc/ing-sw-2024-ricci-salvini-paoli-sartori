package it.polimi.ingsw.gc31;

import java.util.Map;

import it.polimi.ingsw.gc31.view.GUI.SceneTag;
import javafx.scene.Scene;

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
    public static final String CONTROLLER_TAG = "- [Controller]: ";

    public static String mainControllerTag(String id) {
        return "- [MainController " + id + "]: ";
    }

    // TUI
    public final static String TUI_START_LINE_SYMBOL = "> ";
    public final static String TUI_TAG = "[TUI] ";
    public final static String STOP_CURRENT_TUI_STRING = "quit";

    // GUI
    public final static String GUI_SCENE_startScene = "/it/polimi/ingsw/gc31/Views/startScene.fxml";
    public final static String GUI_SCENE_gameScene = "/it/polimi/ingsw/gc31/Views/gameScene.fxml";

    public static Map<SceneTag, String> getGuiFxmlScenes() {
        Map<SceneTag, String> sceneList = new HashMap<>();
        sceneList.put(SceneTag.START, GUI_SCENE_startScene);
        sceneList.put(SceneTag.GAME, GUI_SCENE_gameScene);

        return sceneList;
    }

    // Model
    public final static String DEFAULT_USERNAME = "New User";

}
