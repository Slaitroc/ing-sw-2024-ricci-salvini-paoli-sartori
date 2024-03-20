package it.polimi.ingsw.gc31;

import java.util.List;
import java.util.ArrayList;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.GameView;

public class GameModelTest {
    private List<String> usernamesList;

    private GameModel gameModel;
    private Controller controller;
    private GameView view;
}
