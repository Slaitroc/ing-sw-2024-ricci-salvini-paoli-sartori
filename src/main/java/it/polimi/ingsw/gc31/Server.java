package it.polimi.ingsw.gc31;

import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.exceptions.*;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.view.GameView;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;*/

public class Server /* extends Application */ {
    // /*
    // * @Override
    // * public void start(Stage stage) throws IOException {
    // * FXMLLoader fxmlLoader = new
    // * FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
    // * Scene scene = new Scene(fxmlLoader.load(), 320, 240);
    // * stage.setTitle("Hello!");
    // * stage.setScene(scene);
    // * stage.show();
    // * }
    // */
    //
    // //private static final Controller controller = new Controller();
    //
    //// public static Controller getController() {
    //// return controller.deepCopy();
    //// }
    //
    // public static void main(String[] args) {
    // /* launch(); */
    //
    // // aggiungo il player1 al Controller
    // String player1 = "Christian";
    // addPlayerToController(player1);
    //
    // // creo il GameController corrispondente al player1
    // int numPlayer = 2;
    // GameController gameController = createGameController(player1, numPlayer);
    //
    // // aggiungo il player2 al GameController già creato
    // String player2 = "Matteo";
    // addPlayerToGameController(gameController, player2);
    //
    // // creo il gameModel
    // createGameModel(gameController);
    //
    // }
    //
    // // NOTE for testing
    // public static void addPlayerToController(String player) {
    // // aggiungo gli username al controller (che verifica se esistono già
    // lanciando
    // // se necessario un'eccezione)
    // try {
    // controller.addPlayerUsername(player);
    // } catch (PlayerNicknameAlreadyExistsException e) {
    // e.printStackTrace();
    // }
    //
    // }
    //
    // public static GameController createGameController(List<String> usernamesList,
    // int numPlayers) {
    // if (usernamesList != null && !usernamesList.isEmpty()) {
    //
    // int gameControllerID = controller.createGameController(usernamesList.get(0),
    // numPlayers);
    // GameController gameController =
    // controller.getGameController(gameControllerID);
    // return gameController;
    // } else
    // return null;
    // }
    //
    // public static GameController createGameController(String player, int
    // numPlayers) {
    // int gameControllerID = controller.createGameController(player, numPlayers);
    // GameController gameController =
    // controller.getGameController(gameControllerID);
    // return gameController;
    // }
    //
    // public static void addPlayerToGameController(GameController gameController,
    // String player) {
    // try {
    // gameController.addPlayer(player);
    // } catch (MaxPlayerNumberReachedException e) {
    // e.printStackTrace();
    // } catch (PlayerNicknameAlreadyExistsException c) {
    // c.printStackTrace();
    // }
    // }
    //
    // public static void createGameModel(GameController gameController) {
    // try {
    // gameController.createGameModel();
    // } catch (PlayerNumberNotReachedException e) {
    // System.out.println("Player Number Not Reached!");
    // e.printStackTrace();
    // } catch (GameModelAlreadyCreatedException e) {
    // System.out.println("Game Model Alredy Created!");
    // e.printStackTrace();
    // }
    // }
}