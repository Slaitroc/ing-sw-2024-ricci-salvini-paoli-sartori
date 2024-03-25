package it.polimi.ingsw.gc31;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Paint;
import javafx.scene.paint.RadialGradient;
import javafx.stage.Stage;

import java.awt.Color;
import java.io.IOException;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws IOException {

        // Stage stage = new Stage();
        Group root = new Group();
        Scene scene = new Scene(root, Paint.valueOf("Black"));

        stage.getIcons().add(new Image(Client.class.getResourceAsStream("AppIcons/icon.png")));
        stage.setTitle("CODEX Naturalis");

        // stage.setFullScreen(true); // non funziona sul mio pc
        stage.setWidth(800);
        stage.setHeight(700);

        // stage.setResizable(false);

        stage.setScene(scene);
        stage.show();
    }

    // FXMLLoader fxmlLoader = new
    // FXMLLoader(Client.class.getResource("Views/start-view.fxml"));
    // Scene scene = new Scene(fxmlLoader.load(), 643, 374);
    // stage.getIcons().add(new
    // Image(Client.class.getResourceAsStream("AppIcons/icon.png")));
    // stage.setTitle("CODEX Naturalis-30L Version");
    // stage.setScene(scene);
    // stage.show();

    public static void main(String[] args) {
        launch();
    }

}