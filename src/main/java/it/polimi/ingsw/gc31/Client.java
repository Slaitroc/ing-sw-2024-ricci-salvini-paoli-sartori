package it.polimi.ingsw.gc31;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

public class Client extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Client.class.getResource("Views/start-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 643, 374);
        stage.getIcons().add(new Image(Client.class.getResourceAsStream("AppIcons/icon.png")));
        stage.setTitle("CODEX Naturalis-30L Version");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        System.out.println("Ciao sono il client");
    }

    public static int test_prova(int x){
        return x*x;
    }
}