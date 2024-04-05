package it.polimi.ingsw.gc31;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class ClientFxml extends Application {
    public static Stage clientPrimaryStage;

    @Override
    public void start(Stage primaryStage) throws IOException {
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                10);
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/glimmer of light.otf").toExternalForm(),
                10);

        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/gc31/Views/startScene.fxml"));
        Parent root = loader.load();

        // Imposta il controller se necessario
        // Esempio:
        // MyController controller = loader.getController();

        // Imposta la scena
        clientPrimaryStage = primaryStage;
        Scene scene = new Scene(root);
        clientPrimaryStage.setScene(scene);

        // Imposta le dimensioni della finestra
        clientPrimaryStage.setWidth(640);
        clientPrimaryStage.setHeight(480);
        // primaryStage.setFullScreen(true);

        // Imposta il titolo e l'icona della finestra
        clientPrimaryStage.setTitle("CODEX Naturalis");
        clientPrimaryStage.getIcons().add(new Image(ClientFxml.class.getResourceAsStream("AppIcons/icon.png")));

        // Mostra la finestra
        clientPrimaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }

    // public static Stage getStage() {
    // return clientPrimaryStage;
    // }

    // public static void setStage(Stage stage) {
    // clientPrimaryStage = stage;
    // }
}
