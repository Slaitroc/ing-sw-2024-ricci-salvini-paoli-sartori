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
import it.polimi.ingsw.gc31.view.gui.controllers.ViewController;

import java.util.Map;
import java.util.HashMap;

public class GUIApplication extends Application {

    private static ClientCommands client;
    public static Stage primaryStage;
    private String username;
    private Integer numberOfPlayers;

    @SuppressWarnings("unused")

    public void run() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        initializeGUIResources();
        /*try {
            System.out.println("Initializing scenes...");
            //System.out.println("Client is set to " + client.toString());
            initializeScenesFXML();
        } catch (GUISceneInitializationException e) {
            e.printStackTrace();
            System.err.println("GUI Scene Initialization Error! Wrong path...");
        }*/

        // Set the scene
        primaryStage = stage;
        loadScene(SceneTag.START);

        // Set window size
        setDefaultSize();
        //primaryStage.setFullScreen(true);

        // Set Title and AppIcon
        primaryStage.setTitle("CODEX Naturalis");
        Image icon = new Image(getClass().getResource("/it/polimi/ingsw/gc31/Images/AppIcons/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        //primaryStage.resizableProperty().setValue(Boolean.FALSE);

        // Show window
        primaryStage.show();
    }

    /*private void initializeScenesFXML() throws GUISceneInitializationException {
        scenesMap = new HashMap<>();
        wcMap = new HashMap<>();
        FXMLLoader loader;
        // Carica i file FXML
        for (Map.Entry<SceneTag, String> entry : DefaultValues.getGuiFxmlScenes().entrySet()) {
            System.out.println("Loading " + entry.getValue() + " ...");
            loader = new FXMLLoader(getClass().getResource(entry.getValue()));
            try {
                Scene scene = new Scene(loader.load());
                scenesMap.put(entry.getKey(), scene);
                ViewController wc = loader.getController();
                wc.setGUIApplication(this);
                wc.setClient(client);
                wcMap.put(entry.getKey(), wc);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }*/

    private void initializeGUIResources() {
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                10);
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/glimmerOfLight.otf").toExternalForm(),
                10);
    }

    /*public void loadScene(SceneTag tag) {
        System.out.println("Loading scene " + tag.toString());
        primaryStage.setScene(scenesMap.get(tag));
        currentScene = tag;
    }*/

    public void loadScene(SceneTag tag) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(DefaultValues.getGuiFxmlScenes().get(tag)));
        System.out.println("Loading scene " + DefaultValues.getGuiFxmlScenes().get(tag) + " ...");
        try {
            Scene scene = new Scene(loader.load());
            ViewController wc = loader.getController();
            wc.setGUIApplication(this);
            wc.setClient(client);
            wc.setUp();
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientCommands getClient() {
        return client;
    }

    public void setClient (ClientCommands client) {
        this.client = client;
        //System.out.println("Client set to " + client.toString());
    }

    public void setRuleBookSize() {
        primaryStage.setWidth(1400);
        primaryStage.setHeight(900);
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(900);
        primaryStage.centerOnScreen();
    }

    public void setDefaultSize() {
        primaryStage.setWidth(720);
        primaryStage.setHeight(540);
        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(540);
        primaryStage.centerOnScreen();
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        this.numberOfPlayers = numberOfPlayers;
    }
}
