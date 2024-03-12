package it.polimi.ingsw.gc31;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.Controller.Controller;
import it.polimi.ingsw.gc31.Model.GameModel;
import it.polimi.ingsw.gc31.View.GameView;

public class GameModelTest {
    private List<String> usernamesList;

    private GameModel gameModel;
    private Controller controller;
    private GameView view;

    private Controller creaController(){
        return new Controller();
    }

    private void iniziaPartita() {
        usernamesList = new ArrayList<String>();
        usernamesList.add("Alessandro");
        usernamesList.add("Christian");
        usernamesList.add("Matteo");
        usernamesList.add("Lorenzo");

    }

}
