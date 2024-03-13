package it.polimi.ingsw.gc31;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.Controller.Controller;
import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.Model.Exceptions.MaxPlayerNumberReachedException;
import it.polimi.ingsw.gc31.Model.Exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.View.GameView;

public class GameModelTest {
    private List<String> usernamesList;

    private GameModel gameModel;
    private Controller controller;
    private GameView view;

    public void creaDatiXInizializzazioneGameController() {
        //creo gli username per i futuri player
        usernamesList = new ArrayList<String>();
        usernamesList.add("Alessandro");
        Controller controller = new Controller();
        //NOTE questo controller sará unico e giá presente nel server
        //é il gestore di tutte le singole partite e degli username dei player
        
        
        try {
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
        try {

            //WARN qui sto creando il player prima di aver creato il GameModel... non mi piace
            //IDEA  nei controller voglio usare solo i nickname che possono anche tornare utili come identificativo esterno dei player del Model
            controller.getGameController(gameControllerID).addPlayer(usernamesList.get(1));
            controller.getGameController(gameControllerID).addPlayer(usernamesList.get(2));
            controller.getGameController(gameControllerID).addPlayer(usernamesList.get(3));     
        } catch (MaxPlayerNumberReachedException e) {
            e.printStackTrace();
        }

        System.out.println("FINE DEBUG");



        }

}
