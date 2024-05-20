package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;

public class LobbyController extends ViewController {

    @FXML
    public StackPane lockPlayer4;
    @FXML
    public StackPane lockPlayer3;
    @FXML
    public StackPane inGamePlayer4;
    @FXML
    public StackPane inGamePlayer3;
    @FXML
    MFXTextField mainPlayer;

    @FXML
    @Override
    protected void initialize() {
    }

    @Override
    public void setUp(){
        mainPlayer.setText(app.getUsername());
        switch (app.getNumberOfPlayers()){
            case 2:
                lockPlayer3.setVisible(true);
                lockPlayer4.setVisible(true);
                inGamePlayer3.setVisible(false);
                inGamePlayer4.setVisible(false);
                break;
            case 3:
                lockPlayer3.setVisible(false);
                lockPlayer4.setVisible(true);
                inGamePlayer3.setVisible(true);
                inGamePlayer4.setVisible(false);
                break;
            case 4:
                lockPlayer3.setVisible(false);
                lockPlayer4.setVisible(false);
                inGamePlayer3.setVisible(true);
                inGamePlayer4.setVisible(true);
                break;
        }
    }
}
