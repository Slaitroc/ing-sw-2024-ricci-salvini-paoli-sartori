package it.polimi.ingsw.gc31.view.gui;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.utility.DV;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

import it.polimi.ingsw.gc31.view.gui.controllers.ViewController;

import java.util.LinkedHashMap;
import java.util.List;

public class GUIApplication extends Application {

    public static Stage primaryStage;

    private static ClientCommands client;

    private static String username;
    private static Integer numberOfPlayers;
    private static Integer currentGameID;
    private static ObservableList<GameInstance> gameInstances = FXCollections.observableArrayList();
    private static ViewController currentController;
    private static LinkedHashMap<String, Boolean> playerList = new LinkedHashMap<>();

    @SuppressWarnings("unused")

    public static void run() {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {

        initializeGUIResources();

        // Set the scene
        primaryStage = stage;
        loadScene(SceneTag.START);

        // Set window size
        setDefaultWindowSize();
        // primaryStage.setFullScreen(true);

        // Set Title and AppIcon
        primaryStage.setTitle("CODEX Naturalis");
        Image icon = new Image(
                getClass().getResource("/it/polimi/ingsw/gc31/Images/AppIcons/icon.png").toExternalForm());
        primaryStage.getIcons().add(icon);
        // primaryStage.resizableProperty().setValue(Boolean.FALSE);

        // Show window
        primaryStage.show();
    }

    private void initializeGUIResources() {
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                10);
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/glimmerOfLight.otf").toExternalForm(),
                10);
    }

    public void loadScene(SceneTag tag) {

        FXMLLoader loader = new FXMLLoader(getClass().getResource(DV.getGuiFxmlScenes().get(tag)));
        System.out.println("Loading scene " + DV.getGuiFxmlScenes().get(tag) + " ...");
        try {
            Scene scene = new Scene(loader.load());
            currentController = loader.getController();
            currentController.setGUIApplication(this);
            currentController.setClient(client);
            currentController.setUp();
            primaryStage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public ClientCommands getClient() {
        return client;
    }

    @SuppressWarnings("static-access")
    public void setClient(ClientCommands client) {
        this.client = client;
        // System.out.println("Client set to " + client.toString());
    }

    public void setRuleBookWindowSize() {
        primaryStage.setWidth(1400);
        primaryStage.setHeight(900);
        primaryStage.setMinWidth(1400);
        primaryStage.setMinHeight(900);
        primaryStage.centerOnScreen();
    }

    public void setDefaultWindowSize() {
        primaryStage.setWidth(720);
        primaryStage.setHeight(540);
        primaryStage.setMinWidth(720);
        primaryStage.setMinHeight(540);
        primaryStage.centerOnScreen();
    }

    public void setLobbyWindowSize() {
        primaryStage.setWidth(1280);
        primaryStage.setHeight(720);
        primaryStage.setMinWidth(1280);
        primaryStage.setMinHeight(720);
        primaryStage.centerOnScreen();
    }

    public void setFullScreen(){
        primaryStage.setFullScreen(!primaryStage.isFullScreen());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        GUIApplication.username = username;
    }

    public Integer getNumberOfPlayers() {
        return numberOfPlayers;
    }

    public void setNumberOfPlayers(Integer numberOfPlayers) {
        GUIApplication.numberOfPlayers = numberOfPlayers;
    }

    public int getCurrentGameID() {
        return currentGameID;
    }

    public void setCurrentGameID(int gameID) {
        currentGameID = gameID;
    }

    public ObservableList<GameInstance> getListGames() {
        return gameInstances;
    }

    public void setListGames(List<String> gamesList) {
        gameInstances.clear();
        if(gamesList.getFirst().equals("NO GAMES AVAILABLE")) return;
        for (String game : gamesList) {
            String gameID = game.split(" ")[0];
            String players = game.split(" ")[1] + game.split(" ")[2] + game.split(" ")[3];
            gameInstances.add(new GameInstance(gameID, players));
        }
    }

    public ViewController getCurrentController() {
        return currentController;
    }

    public LinkedHashMap<String, Boolean> getPlayerList() {
        return playerList;
    }

    public void setPlayerList(LinkedHashMap<String, Boolean> playerList) {
        GUIApplication.playerList = playerList;
    }

}
