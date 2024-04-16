package it.polimi.ingsw.gc31.view.GUI;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.exceptions.GUISceneInitializationException;

import java.util.Map;
import java.util.HashMap;

public class GUIApplication extends Application {

    public static Stage primaryStage;
    private Map<SceneTag, Scene> scenesMap;
    private SceneTag activeScene;

    @Override
    public void start(Stage stage) throws IOException {
        initializeGUIResources();
        try {
            initializeScenesFXML();
        } catch (GUISceneInitializationException e) {
            System.err.println("GUI Scene Initialization Error! Wrong path...");
        }

        // Imposta la scena
        primaryStage = stage;
        primaryStage.setScene(choseScene(SceneTag.START));

        // Imposta le dimensioni della finestra
        primaryStage.setWidth(640);
        primaryStage.setHeight(480);
        // primaryStage.setFullScreen(true);

        // Imposta il titolo e l'icona della finestra
        primaryStage.setTitle("CODEX Naturalis");
        // primaryStage.getIcons().add(new
        // Image(ClientFxml.class.getResourceAsStream("AppIcons/icon.png")));

        // Mostra la finestra
        primaryStage.show();
    }

    private void initializeScenesFXML() throws GUISceneInitializationException {
        scenesMap = new HashMap<>();
        FXMLLoader loader;
        // Carica il file FXML
        for (Map.Entry<SceneTag, String> entry : DefaultValues.getGuiFxmlScenes().entrySet()) {
            loader = new FXMLLoader(getClass().getResource(entry.getValue()));
            try {
                Scene scene = new Scene(loader.load());
                scenesMap.put(entry.getKey(), scene);
            } catch (IOException e) {
                throw new GUISceneInitializationException();
            }
        }

    }

    private void initializeGUIResources() {
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                10);
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/glimmer of light.otf").toExternalForm(),
                10);

    }

    private Scene choseScene(SceneTag tag) {
        activeScene = tag;
        return scenesMap.get(tag);

    }

}
