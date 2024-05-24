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
import java.util.ArrayList;
import java.util.List;

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
    public MFXTextField namePlayer1;
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

        disableStackPane(inGamePlayer2);
        disableStackPane(inGamePlayer3);
        disableStackPane(inGamePlayer4);

        disableStackPane(waitingPlayer2);
        disableStackPane(waitingPlayer3);
        disableStackPane(waitingPlayer4);

        disableStackPane(lockPlayer3);
        disableStackPane(lockPlayer4);


        switch (app.getNumberOfPlayers()) {
            case 2:
                enableStackPane(waitingPlayer2);

                enableStackPane(lockPlayer3);
                enableStackPane(lockPlayer4);
                break;
            case 3:
                enableStackPane(waitingPlayer2);
                enableStackPane(waitingPlayer3);
                enableStackPane(lockPlayer4);
                break;
            case 4:
                enableStackPane(waitingPlayer2);
                enableStackPane(waitingPlayer3);
                enableStackPane(waitingPlayer4);
                break;
        }

        updateLobby();
        System.out.println("I'm player number " + imPlayerNumber);
        System.out.println("This is lobby number " + app.getCurrentGameID());
        System.out.println("In this lobby there are supposed to be " + app.getNumberOfPlayers() + " players");
        System.out.println("In this lobby there are " + app.getPlayerList().size() + " players");
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

    private void enableStackPane(StackPane pane) {
        pane.setVisible(true);
        pane.setManaged(true);
    }

    private void disableStackPane(StackPane pane) {
        pane.setVisible(false);
        pane.setManaged(false);
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
        try {
            //System.out.println("Hey, I'm " + app.getUsername() + ", Player Number " + imPlayerNumber + ". I am setting my status from " + ready.getText());
            client.setReady(ready.getText().equals("Not Ready"));
        } catch (RemoteException e) {
            show_ServerCrashWarning();
        }
    }

    @Override
    public void showReady(String username, boolean status) {
        //System.out.println("Hey, I'm " + app.getUsername() + ", Player Number " + imPlayerNumber + ". I am observing that player " + username + " is setting his status to " + status);
        if (username.equals(app.getPlayerList().getFirst())) {
            //System.out.println("I have observed that player number 1 changed his status");
            if (status) {
                ready1.setText("Ready");
            } else {
                ready1.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().get(1))) {
            //System.out.println("I have observed that player number 2 changed his status");
            if (status) {
                ready2.setText("Ready");
            } else {
                ready2.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().get(2))) {
            //System.out.println("I have observed that player number 3 changed his status");
            if (status) {
                ready3.setText("Ready");
            } else {
                ready3.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().get(3))) {
            //System.out.println("I have observed that player number 4 changed his status");
            if (status) {
                ready4.setText("Ready");
            } else {
                ready4.setText("Not Ready");
            }
        }
    }

    @Override
    public void updateLobby() {
        int count = 1;
        for (String name : app.getPlayerList()) {
            switch (count) {
                case 1:
                    namePlayer1.setText(name);
                    if (name.equals(app.getUsername())) {
                        imPlayerNumber = 1;
                        iconPlayer1.setVisible(true);
                    }
                    break;
                case 2:
                    namePlayer2.setText(name);
                    disableStackPane(waitingPlayer2);
                    enableStackPane(inGamePlayer2);
                    if (name.equals(app.getUsername())) {
                        imPlayerNumber = 2;
                        iconPlayer2.setVisible(true);
                    }
                    break;
                case 3:
                    namePlayer3.setText(name);
                    disableStackPane(waitingPlayer3);
                    enableStackPane(inGamePlayer3);
                    if (name.equals(app.getUsername())) {
                        imPlayerNumber = 3;
                        iconPlayer3.setVisible(true);
                    }
                    break;
                case 4:
                    namePlayer4.setText(name);
                    disableStackPane(waitingPlayer4);
                    enableStackPane(inGamePlayer4);
                    if (name.equals(app.getUsername())) {
                        imPlayerNumber = 4;
                        iconPlayer4.setVisible(true);
                    }
                    break;
            }
            count++;
        }
    }

    @Override
    public void unreadyMe(){
        try {
            client.setReady(false);
        } catch (RemoteException e) {
            show_ServerCrashWarning();
        }
        switch (imPlayerNumber) {
            case 1:
                ready1.setText("Not Ready");
                break;
            case 2:
                ready2.setText("Not Ready");
                break;
            case 3:
                ready3.setText("Not Ready");
                break;
            case 4:
                ready4.setText("Not Ready");
                break;
        }
    }

    private void setPlayer(int i) {
        iconPlayer1.setVisible(false);
        iconPlayer2.setVisible(false);
        iconPlayer3.setVisible(false);
        iconPlayer4.setVisible(false);

        switch (i) {
            case 1:
                imPlayerNumber = 1;
                iconPlayer1.setVisible(true);
            case 2:
                imPlayerNumber = 2;
                iconPlayer2.setVisible(true);
            case 3:
                imPlayerNumber = 3;
                iconPlayer3.setVisible(true);
            case 4:
                imPlayerNumber = 4;
                iconPlayer4.setVisible(true);
        }
    }
}
