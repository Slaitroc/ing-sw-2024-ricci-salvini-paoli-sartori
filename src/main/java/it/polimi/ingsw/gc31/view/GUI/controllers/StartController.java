package it.polimi.ingsw.gc31.view.GUI.controllers;

import it.polimi.ingsw.gc31.view.GUI.SceneTag;
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
