package it.polimi.ingsw.gc31.view.gui;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.exceptions.GUISceneInitializationException;
import it.polimi.ingsw.gc31.view.gui.controllers.ViewController;

import java.util.Map;
import java.util.HashMap;

public class GUIApplication extends Application {

    private ClientCommands client;
    public static Stage primaryStage;
    private Map<SceneTag, Scene> scenesMap;
    private Map<SceneTag, ViewController> wcMap;
    @SuppressWarnings("unused")
    private SceneTag activeScene;

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        initializeGUIResources();
        try {
            initializeScenesFXML();
        } catch (GUISceneInitializationException e) {
            e.printStackTrace();
            System.err.println("GUI Scene Initialization Error! Wrong path...");
        }

        // Imposta la scena
        primaryStage = stage;
        loadScene(SceneTag.START);

        // Imposta le dimensioni della finestra
        primaryStage.setWidth(640);
        primaryStage.setHeight(480);
        //primaryStage.setFullScreen(true);

        // Imposta il titolo e l'icona della finestra
        primaryStage.setTitle("CODEX Naturalis");

        Image icon = new Image(getClass().getResource("/it/polimi/ingsw/gc31/AppIcons/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);

        // Mostra la finestra
        primaryStage.show();
    }

    private void initializeScenesFXML() throws GUISceneInitializationException {
        scenesMap = new HashMap<>();
        wcMap = new HashMap<>();
        FXMLLoader loader;
        // Carica i file FXML
        for (Map.Entry<SceneTag, String> entry : DefaultValues.getGuiFxmlScenes().entrySet()) {
            loader = new FXMLLoader(getClass().getResource(entry.getValue()));
            System.out.println("Loading " + entry.getValue() + "...");
            try {
                Scene scene = new Scene(loader.load());
                scenesMap.put(entry.getKey(), scene);
                ViewController wc = loader.getController();
                wc.setGUIApplication(this);
                wcMap.put(entry.getKey(), wc);
            } catch (IOException e) {
                e.printStackTrace();
                // throw new GUISceneInitializationException();
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

    public void loadScene(SceneTag tag) {
        primaryStage.setScene(choseScene(tag));
    }

    public ClientCommands getClient() {
        return client;
    }

    public void setClient (ClientCommands client) {
        this.client = client;
    }


}
