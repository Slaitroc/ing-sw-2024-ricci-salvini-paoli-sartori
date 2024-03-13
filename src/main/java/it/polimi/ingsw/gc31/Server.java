package it.polimi.ingsw.gc31;

import java.util.List;
import java.util.ArrayList;

import it.polimi.ingsw.gc31.Controller.Controller;
import it.polimi.ingsw.gc31.Controller.GameController;
import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.Model.Exceptions.*;
import it.polimi.ingsw.gc31.View.GameView;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;*/

public class Server /*extends Application*/ {
    /*@Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 320, 240);
        stage.setTitle("Hello!");
        stage.setScene(scene);
        stage.show();
    }*/
    
    private GameModel gameModel;
    private Controller controller;
    private GameView view;
    private static List<String> usernamesList;
    public static void main(String[] args) {
        /*launch();*/


  /*   public void creaDatiXInizializzazioneGameController() { */
        //creo gli username per i futuri player
        Controller controller = new Controller();
        //NOTE questo controller sará unico e giá presente nel server
        //é il gestore di tutte le singole partite e degli username dei player
        usernamesList = new ArrayList<String>();
        usernamesList.add("Alessandro");
        
        
        try {
            controller.addPlayerUsername(usernamesList.get(0));
            usernamesList.add("Christian");
            controller.addPlayerUsername(usernamesList.get(1));
            usernamesList.add("Matteo");
            controller.addPlayerUsername(usernamesList.get(2));
            usernamesList.add("Lorenzo");
            controller.addPlayerUsername(usernamesList.get(3));
        } catch (PlayerNicknameAlreadyExistsException e) {
            e.printStackTrace();
        }
        
        int gameControllerID = controller.createGame(usernamesList.get(0));
        GameController gameController = controller.getGameController(gameControllerID);
        
        try {
            //WARN qui sto creando il player prima di aver creato il GameModel... non mi piace
            //IDEA  nei controller voglio usare solo i nickname che possono anche tornare utili come identificativo esterno dei player del Model
            gameController.addPlayer(usernamesList.get(1));
            gameController.addPlayer(usernamesList.get(2));
            gameController.addPlayer(usernamesList.get(3));     
        } catch (MaxPlayerNumberReachedException e) {
            e.printStackTrace();
        }

        System.out.println("FINE DEBUG");



        
        
    }

    public static int test_prova(int x){
        return x*x;
    }


    
}