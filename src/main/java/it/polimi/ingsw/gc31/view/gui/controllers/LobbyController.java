package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.view.gui.GUIApplication;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.rmi.RemoteException;

public class LobbyController extends ViewController {

    @FXML
    public ImageView iconPlayer1;
    @FXML
    public ImageView iconPlayer2;
    @FXML
    public ImageView iconPlayer3;
    @FXML
    public ImageView iconPlayer4;

    @FXML
    public TextField ready1;
    @FXML
    public TextField ready2;
    @FXML
    public TextField ready3;
    @FXML
    public TextField ready4;
    @FXML
    public Label gameIDLabel;


    @FXML
    MFXTextField namePlayer1;
    @FXML
    public MFXTextField namePlayer2;
    @FXML
    public MFXTextField namePlayer3;
    @FXML
    public MFXTextField namePlayer4;

    @FXML
    public StackPane inGamePlayer2;
    @FXML
    public StackPane inGamePlayer3;
    @FXML
    public StackPane inGamePlayer4;

    @FXML
    public StackPane lockPlayer3;
    @FXML
    public StackPane lockPlayer4;

    @FXML
    public StackPane waitingPlayer2;
    @FXML
    public StackPane waitingPlayer3;
    @FXML
    public StackPane waitingPlayer4;

    @FXML
    public MFXTextField textField;
    @FXML
    public TextArea chatField;

    private int imPlayerNumber;


    @FXML
    @Override
    protected void initialize() {
    }

    @Override
    public void setUp() {
        gameIDLabel.setText("Game ID: " + app.getCurrentGameID());

        inGamePlayer2.setVisible(false);
        inGamePlayer2.setManaged(false);

        inGamePlayer3.setVisible(false);
        inGamePlayer3.setManaged(false);

        inGamePlayer4.setVisible(false);
        inGamePlayer4.setManaged(false);

        lockPlayer3.setVisible(false);
        lockPlayer3.setManaged(false);

        lockPlayer4.setVisible(false);
        lockPlayer4.setManaged(false);

        waitingPlayer2.setVisible(false);
        waitingPlayer2.setManaged(false);

        waitingPlayer3.setVisible(false);
        waitingPlayer3.setManaged(false);

        waitingPlayer4.setVisible(false);
        waitingPlayer4.setManaged(false);

        switch (app.getNumberOfPlayers()) {
            case 2:
                if (GUIApplication.getPlayerList().isEmpty()) {
                    imPlayerNumber = 1;
                    iconPlayer1.setVisible(true);
                    namePlayer1.setText(app.getUsername());

                    waitingPlayer2.setVisible(true);
                    waitingPlayer2.setManaged(true);

                } else {
                    imPlayerNumber = 2;
                    iconPlayer2.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(app.getUsername());
                }

                lockPlayer3.setVisible(true);
                lockPlayer3.setManaged(true);

                lockPlayer4.setVisible(true);
                lockPlayer4.setManaged(true);


                break;
            case 3:

                if (GUIApplication.getPlayerList().isEmpty()) {
                    imPlayerNumber = 1;
                    iconPlayer1.setVisible(true);
                    namePlayer1.setText(app.getUsername());

                    waitingPlayer2.setVisible(true);
                    waitingPlayer2.setManaged(true);

                    waitingPlayer3.setVisible(true);
                    waitingPlayer3.setManaged(true);
                } else if (GUIApplication.getPlayerList().size() == 1) {
                    imPlayerNumber = 2;
                    iconPlayer2.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(app.getUsername());

                    waitingPlayer3.setVisible(true);
                    waitingPlayer3.setManaged(true);
                } else {
                    imPlayerNumber = 3;
                    iconPlayer3.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(GUIApplication.getPlayerList().get(1));

                    inGamePlayer3.setVisible(true);
                    inGamePlayer3.setManaged(true);
                    namePlayer3.setText(app.getUsername());

                }
                lockPlayer4.setVisible(true);
                lockPlayer4.setManaged(true);
                break;

            case 4:
                if (GUIApplication.getPlayerList().isEmpty()) {
                    imPlayerNumber = 1;
                    iconPlayer1.setVisible(true);
                    namePlayer1.setText(app.getUsername());

                    waitingPlayer2.setVisible(true);
                    waitingPlayer2.setManaged(true);

                    waitingPlayer3.setVisible(true);
                    waitingPlayer3.setManaged(true);

                    waitingPlayer4.setVisible(true);
                    waitingPlayer4.setManaged(true);

                } else if (GUIApplication.getPlayerList().size() == 1) {
                    imPlayerNumber = 2;
                    iconPlayer2.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(app.getUsername());

                    waitingPlayer3.setVisible(true);
                    waitingPlayer3.setManaged(true);

                    waitingPlayer4.setVisible(true);
                    waitingPlayer4.setManaged(true);

                } else if (GUIApplication.getPlayerList().size() == 2) {
                    imPlayerNumber = 3;
                    iconPlayer3.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(GUIApplication.getPlayerList().get(1));

                    inGamePlayer3.setVisible(true);
                    inGamePlayer3.setManaged(true);
                    namePlayer3.setText(app.getUsername());

                    waitingPlayer4.setVisible(true);
                    waitingPlayer4.setManaged(true);
                } else {
                    imPlayerNumber = 4;
                    iconPlayer4.setVisible(true);
                    namePlayer1.setText(GUIApplication.getPlayerList().getFirst());

                    inGamePlayer2.setVisible(true);
                    inGamePlayer2.setManaged(true);
                    namePlayer2.setText(GUIApplication.getPlayerList().get(1));

                    inGamePlayer3.setVisible(true);
                    inGamePlayer3.setManaged(true);
                    namePlayer3.setText(GUIApplication.getPlayerList().get(2));

                    inGamePlayer4.setVisible(true);
                    inGamePlayer4.setManaged(true);
                    namePlayer4.setText(app.getUsername());
                }
                break;
        }
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
            sendText(textField.getText());
            textField.clear();
        }
    }

    public void sendText(String message) {
        try {
            client.sendChatMessage(client.getUsername(), message);
        } catch (RemoteException e) {
            show_ServerCrashWarning();
        }
    }

    @Override
    public void updateChat(String username, String message) {
        chatField.setText("\n" + username + ": " + message + chatField.getText());
    }

    public void setReady() {
        switch (imPlayerNumber) {
            case 1:
                changeReady(ready1);
                break;
            case 2:
                changeReady(ready2);
                break;
            case 3:
                changeReady(ready3);
                break;
            case 4:
                changeReady(ready4);
                break;
        }
    }

    private void changeReady(TextField ready) {
        if (ready.getText().equals("Not Ready")) {
            try {
                client.setReady(true);
                ready.setText("Ready");
            } catch (RemoteException e) {
                show_ServerCrashWarning();
            }
        } else {
            try {
                client.setReady(false);
                ready.setText("Not Ready");
            } catch (RemoteException e) {
                show_ServerCrashWarning();
            }
        }
    }
}
