package it.polimi.ingsw.gc31;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.text.Font;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {
        // Carica il file FXML
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/gc31/Views/start-view.fxml"));
        Parent root = loader.load();
        Font font = Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                10);

        // Imposta il controller se necessario
        // Esempio:
        // MyController controller = loader.getController();

        // Imposta la scena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);

        // Imposta le dimensioni della finestra
        primaryStage.setWidth(640);
        primaryStage.setHeight(480);
        // primaryStage.setFullScreen(true);

        // Imposta il titolo e l'icona della finestra
        primaryStage.setTitle("CODEX Naturalis");
        primaryStage.getIcons().add(new Image(Client.class.getResourceAsStream("AppIcons/icon.png")));

        // Mostra la finestra
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
