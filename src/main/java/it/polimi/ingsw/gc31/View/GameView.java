package it.polimi.ingsw.gc31.View;

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