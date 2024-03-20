package it.polimi.ingsw.gc31;

import java.util.List;
import java.util.ArrayList;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.exceptions.*;
import it.polimi.ingsw.gc31.view.GameView;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;*/

public class Server /* extends Application */ {
    /*
     * @Override
     * public void start(Stage stage) throws IOException {
     * FXMLLoader fxmlLoader = new
     * FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
     * Scene scene = new Scene(fxmlLoader.load(), 320, 240);
     * stage.setTitle("Hello!");
     * stage.setScene(scene);
     * stage.show();
     * }
     */

    private GameModel gameModel;
    private Controller controller;
    private GameView view;
    private static List<String> usernamesList;

    public static void main(String[] args) {
        /* launch(); */

        Controller controller = new Controller();
        // NOTE questo controller sará unico e giá presente nel server
        // é il gestore di tutte le singole partite e degli username dei player
        

        // creo gli username per i futuri player
        usernamesList = new ArrayList<String>();
        usernamesList.add("Alessandro");
        usernamesList.add("Christian");
        usernamesList.add("Matteo");
        usernamesList.add("Lorenzo");

        //aggiungo gli username al controller (che verifica se esistono già lanciando se necessario un'eccezione)
        try {
            controller.addPlayerUsername(usernamesList.get(0));
            controller.addPlayerUsername(usernamesList.get(1));
            controller.addPlayerUsername(usernamesList.get(2));
            controller.addPlayerUsername(usernamesList.get(3));
        } catch (PlayerNicknameAlreadyExistsException e) {
            e.printStackTrace();
        }

        //creo il GameController specifico della partita 
        //il costruttore prende sono il player che vuole creare una nuova partita
        int gameControllerID = controller.createGameController(usernamesList.get(0), 2);
        GameController gameController = controller.getGameController(gameControllerID);

        //aggiungo gli altri player al GameController
        try {
            gameController.addPlayer(usernamesList.get(1));
            gameController.addPlayer(usernamesList.get(2));
            gameController.addPlayer(usernamesList.get(3));
        } catch (MaxPlayerNumberReachedException e) {
            e.printStackTrace();
        }

        //creo il modello
        //se i player non hanno raggiunto il numero massimo allora viene lanciata un'eccezione
        try {
            gameController.createGameModel();
        } catch (PlayerNumberNotReachedException e) {
            System.out.println("Player Number Not Reached!");
            e.printStackTrace();
        }

        


        
        System.out.println("FINE DEBUG");

    }

}