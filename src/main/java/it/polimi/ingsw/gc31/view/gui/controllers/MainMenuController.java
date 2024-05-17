package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;

public class MainMenuController extends ViewController{


    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
    @Override
    @FXML
    public void initialize() {
    }

    public void createGame() {
        // TODO
    }

    public void joinGame() {
        // TODO
    }

    public void showGames() {
        // TODO
    }

    public void showRules() {
        loadRuleBookScene();
    }

    @FXML
    private void loadRuleBookScene() {
        app.setRuleBookSize();
        app.loadScene(SceneTag.RULEBOOK);
    }
}
