package it.polimi.ingsw.gc31;

import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.text.Font;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import io.github.palexdev.materialfx.enums.ButtonType;

import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.stage.Stage;
import java.io.IOException;

import io.github.palexdev.materialfx.controls.MFXButton;

public class ClientJava extends Application {
    @Override
    public void start(Stage primaryStage) throws IOException {

        // qui carico le risorse a cui si riferisce il css
        // font
        Font.loadFont(getClass().getResource("/it/polimi/ingsw/gc31/Fonts/FrakturNo2.ttf").toExternalForm(),
                30);
        Image startImg_codexLogo = new Image(
                getClass().getResource("/it/polimi/ingsw/gc31/Images/Misc/CodexLogo.png").toExternalForm());

        // qui definisco il nodo radice sul quale pplicare lo stile css
        VBox root = new VBox(20);
        root.setAlignment(Pos.CENTER);

        // applico lo stile desiderato al nodo radice
        root.getStylesheets().add(getClass().getResource("/it/polimi/ingsw/gc31/Views/stile1.css").toExternalForm());

        // immagine principale
        ImageView mainImage = new ImageView(startImg_codexLogo);
        StackPane.setAlignment(mainImage, javafx.geometry.Pos.CENTER);

        // button
        MFXButton button = new MFXButton("PlayTheGame", 400, 40);
        button.buttonTypeProperty().setValue(ButtonType.RAISED);
        button.getStyleClass().add("rounded-button");
        button.rippleColorProperty().setValue(Color.ANTIQUEWHITE);
        button.rippleAnimationSpeedProperty().setValue(0.5);

        // aggiungo i nodi creati al nodo radice
        root.getChildren().addAll(mainImage, button);

        // Imposta la scena
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        // primaryStage.setFullScreen(true);
        primaryStage.setResizable(true);
        primaryStage.setWidth(640);
        primaryStage.setHeight(480);
        primaryStage.setTitle("CODEX Naturalis");
        primaryStage.getIcons().add(new Image(Client.class.getResourceAsStream("AppIcons/icon.png")));
        primaryStage.show();

        // Imposta il controller se necessario
        // Esempio:
        // MyController controller = loader.getController();
    }

    public static void main(String[] args) {
        launch();
    }
}
