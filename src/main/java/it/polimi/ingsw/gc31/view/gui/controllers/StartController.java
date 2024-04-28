package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;

public class StartController extends ViewController {

    @FXML
    private void loadLoginScene() {
        app.loadScene(SceneTag.USERNAME);
    }

    @Override
    @FXML
    protected void initialize() {

    }
}
