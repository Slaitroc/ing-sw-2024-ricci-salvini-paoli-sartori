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
}
