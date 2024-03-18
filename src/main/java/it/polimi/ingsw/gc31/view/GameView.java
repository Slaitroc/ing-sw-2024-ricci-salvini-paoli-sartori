package it.polimi.ingsw.gc31.view;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class GameView {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}