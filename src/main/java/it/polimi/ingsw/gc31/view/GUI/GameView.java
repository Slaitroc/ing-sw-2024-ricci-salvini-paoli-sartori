package it.polimi.ingsw.gc31.view.GUI;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class GameView {

    @FXML
    private ImageView riferimentoFxml;

    public GameView() {
        riferimentoFxml = new ImageView();
    }

    // Questo metodo inizializza i riferimenti della vista
    // Per ora sto caricando l'immagine Misc1.jpg quando viene creata la scena da
    // gamesScene.fxml nel metodo loadGamesScene() chiamato dal bottone di
    // startScene.
    // Prima inizializzo il riferimento dell'immagine per fxml (riferimentoFxml) poi
    // quando carico gamesScene.fxml il riferimento viene usato tramite ID.
    // È importante che il metodo si chiami in questo modo: initialize() --> a
    // fxml/javafx serve così
    @FXML
    public void initialize() {
        Image image = new Image(
                getClass().getResource("/it/polimi/ingsw/gc31/Images/Misc/Misc1.jpg").toExternalForm());
        riferimentoFxml.setImage(image);

    }

    // crea la scena dal file gameScene.fxml e fa si che il client la mostri nel suo
    // Stage primaryStage.
    // Ora sto accedento al Client staticamente ma ci saranno pià client e bisognerà
    // capire come gestirli
    @FXML
    private void loadGamesScene(ActionEvent event) {
        initialize();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/gc31/Views/gameScene.fxml"));
        Parent root = new Parent() {
        };
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        GUIApplication.primaryStage.setWidth(600);
        GUIApplication.primaryStage.setHeight(600);
        GUIApplication.primaryStage.setScene(scene);
        System.out.println("pulsante premuto");
    }

    // lo stesso di gamesScene ma per startScene.fxml
    @FXML
    private void loadStartScene(ActionEvent event) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/it/polimi/ingsw/gc31/Views/startScene.fxml"));
        Parent root = new Parent() {
        };
        try {
            root = loader.load();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Scene scene = new Scene(root);
        GUIApplication.primaryStage.setScene(scene);
        System.out.println("pulsante premuto");
    }

}