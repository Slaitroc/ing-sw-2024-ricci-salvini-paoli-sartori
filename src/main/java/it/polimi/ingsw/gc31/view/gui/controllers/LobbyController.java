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
    public StackPane inGamePlayer2;
    @FXML
    public StackPane waitingPlayer2;
    @FXML
    public StackPane waitingPlayer3;
    @FXML
    public StackPane waitingPlayer4;

    @FXML
    MFXTextField mainPlayer;

    @FXML
    @Override
    protected void initialize() {
    }

    @Override
    public void setUp() {
        mainPlayer.setText(app.getUsername());

        inGamePlayer2.setVisible(false);
        inGamePlayer2.setManaged(false);

        inGamePlayer3.setVisible(false);
        inGamePlayer3.setManaged(false);

        inGamePlayer4.setVisible(false);
        inGamePlayer4.setManaged(false);

        switch (app.getNumberOfPlayers()) {
            case 2:
                lockPlayer3.setVisible(true);
                lockPlayer3.setManaged(true);

                lockPlayer4.setVisible(true);
                lockPlayer4.setManaged(true);

                waitingPlayer2.setVisible(true);
                waitingPlayer2.setManaged(true);

                waitingPlayer3.setVisible(false);
                waitingPlayer3.setManaged(false);

                waitingPlayer4.setVisible(false);
                waitingPlayer4.setManaged(false);

                break;
            case 3:
                lockPlayer3.setVisible(false);
                lockPlayer3.setManaged(false);

                lockPlayer4.setVisible(true);
                lockPlayer4.setManaged(true);

                waitingPlayer2.setVisible(true);
                waitingPlayer2.setManaged(true);

                waitingPlayer3.setVisible(true);
                waitingPlayer3.setManaged(true);

                waitingPlayer4.setVisible(false);
                waitingPlayer4.setManaged(false);

                break;
            case 4:
                lockPlayer3.setVisible(false);
                lockPlayer3.setManaged(false);

                lockPlayer4.setVisible(false);
                lockPlayer4.setManaged(false);

                waitingPlayer2.setVisible(true);
                waitingPlayer2.setManaged(true);

                waitingPlayer3.setVisible(true);
                waitingPlayer3.setManaged(true);

                waitingPlayer4.setVisible(true);
                waitingPlayer4.setManaged(true);

                break;
        }
    }
}
