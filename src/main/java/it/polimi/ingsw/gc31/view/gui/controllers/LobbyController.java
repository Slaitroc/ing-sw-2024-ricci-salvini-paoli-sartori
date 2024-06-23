package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;
import java.util.Map;

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
    public TextFlow chatField;
    @FXML
    public ScrollPane scrollPane;

    // Player number in the game
    private int imPlayerNumber;


    @FXML
    @Override
    protected void initialize() {
    }

    /**
     * Disable all StackPanes and enable back only those which will be actually used
     * through the updateLobby method
     */
    @Override
    public void setUp() {

        gameIDLabel.setText("Game ID: " + app.getCurrentGameID());

        iconPlayer1.setVisible(false);
        iconPlayer2.setVisible(false);
        iconPlayer3.setVisible(false);
        iconPlayer4.setVisible(false);

        disableStackPane(inGamePlayer2);
        disableStackPane(inGamePlayer3);
        disableStackPane(inGamePlayer4);

        disableStackPane(waitingPlayer2);
        disableStackPane(waitingPlayer3);
        disableStackPane(waitingPlayer4);

        disableStackPane(lockPlayer3);
        disableStackPane(lockPlayer4);

        // Enable the waiting player and lock player panes based on the number of players
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
        textField.requestFocus();
    }

    /**
     * Enter key on chat to send message
     *
     * @param event The key event.
     */
    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER && !textField.getText().isEmpty()) {
            sendText(textField.getText());
            textField.clear();
        }
    }

    /**
     * Sends a chat message.
     *
     * @param message The message to send.
     */
    private void sendText(String message) {
        try {
            client.sendChatMessage(client.getUsername(), message);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Enables a StackPane by setting it to visible and managed.
     *
     * @param pane The StackPane to enable.
     */
    private void enableStackPane(StackPane pane) {
        pane.setVisible(true);
        pane.setManaged(true);
    }

    /**
     * Disables a StackPane by setting it to invisible and not managed.
     *
     * @param pane The StackPane to disable.
     */
    private void disableStackPane(StackPane pane) {
        pane.setVisible(false);
        pane.setManaged(false);
    }


    @Override
    public void updateChat(String username, String message) {
        Text usernameText = new Text(username + ": ");
        if (username.equals(app.getPlayerList().keySet().stream().toList().getFirst())) {
            usernameText.setFill(Color.GREEN);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(1))) {
            usernameText.setFill(Color.VIOLET);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(2))) {
            usernameText.setFill(Color.RED);
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(3))) {
            usernameText.setFill(Color.BLUE);
        }
        Text messageText = new Text(message + "\n");
        messageText.setFill(Color.BLACK);

        chatField.getChildren().add(usernameText);
        chatField.getChildren().add(messageText);
        scrollPane.layout();
        scrollPane.setVvalue(1.0);
    }

    /**
     * Method called on button trigger
     * Select the right player on witch call the changeReady() function
     */
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

    /**
     * Changes the ready status of a player. Here the message to the server is sent
     *
     * @param ready The TextField representing the ready status.
     */
    private void changeReady(TextField ready) {
        try {
            //System.out.println("Hey, I'm " + app.getUsername() + ", Player Number " + imPlayerNumber + ". I am setting my status from " + ready.getText());
            client.setReady(ready.getText().equals("Not Ready"));
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    /**
     * Updates the ready status of players in the lobby.
     * This method sets the ready status text ("Ready" or "Not Ready") for each player in the lobby
     * based on their current status. It checks which player has changed their status and updates
     * the corresponding UI label accordingly.
     * The method performs the following actions:
     * 1. Retrieves the player's position in the list from the application.
     * 2. Compares the given username with the usernames in the player list.
     * 3. Sets the ready status text for the corresponding player based on the provided status.
     *
     * @param username the username of the player whose status has changed.
     * @param status   the current ready status of the player (true if ready, false otherwise).
     */
    @Override
    public void showReady(String username, boolean status) {
        //System.out.println("Hey, I'm " + app.getUsername() + ", Player Number " + imPlayerNumber + ". I am observing that player " + username + " is setting his status to " + status);
        if (username.equals(app.getPlayerList().keySet().stream().toList().getFirst())) {
            //System.out.println("I have observed that player number 1 changed his status");
            if (status) {
                ready1.setText("Ready");
            } else {
                ready1.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(1))) {
            //System.out.println("I have observed that player number 2 changed his status");
            if (status) {
                ready2.setText("Ready");
            } else {
                ready2.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(2))) {
            //System.out.println("I have observed that player number 3 changed his status");
            if (status) {
                ready3.setText("Ready");
            } else {
                ready3.setText("Not Ready");
            }
        } else if (username.equals(app.getPlayerList().keySet().stream().toList().get(3))) {
            //System.out.println("I have observed that player number 4 changed his status");
            if (status) {
                ready4.setText("Ready");
            } else {
                ready4.setText("Not Ready");
            }
        }
        checkAllReady();
    }

    private void checkAllReady() {
        if(app.getPlayerList().size() == app.getNumberOfPlayers()){
            if (ready1.getText().equals("Ready") && ready2.getText().equals("Ready") && ready3.getText().equals("Ready") && ready4.getText().equals("Ready")) {
                app.loadScene(SceneTag.GAME);
            }
        }
    }

    @Override
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
        app.setPlayerList(players);
        updateLobby();
    }


    /**
     * Updates the lobby with the current players and their statuses.
     * This method iterates through the player list obtained from the application,
     * and for each player, it updates the lobby UI elements such as player names
     * and visibility of specific icons based on the player’s position and status.
     * The method performs the following actions:
     * 1. Sets the player's name in the corresponding label.
     * 2. Updates the visibility of icons to indicate which player corresponds to the current user.
     * 3. Enables inGamePlayers Panes when players are in lobby, waitingPlayers Panes for free spaces in the lobby
     *    (LockedPlayers are handled only in setup phases as # of player cannot change)
     * 4. Calls the showReady method to update the player's ready status.
     * The player list is a map with player names as keys and their ready status as values.
     */
    public void updateLobby() {

        //Reset lobby state to empty
        disableStackPane(inGamePlayer2);
        enableStackPane(waitingPlayer2);
        iconPlayer1.setVisible(false);
        iconPlayer2.setVisible(false);
        iconPlayer3.setVisible(false);
        iconPlayer4.setVisible(false);
        if(app.getNumberOfPlayers()>=3){
            disableStackPane(inGamePlayer3);
            enableStackPane(waitingPlayer3);
            if(app.getNumberOfPlayers()==4){
                disableStackPane(inGamePlayer4);
                enableStackPane(waitingPlayer4);
            }
        }

        //Repopulate lobby
        int count = 1;
        for (Map.Entry<String, Boolean> player : app.getPlayerList().entrySet()) {
            switch (count) {
                case 1:
                    namePlayer1.setText(player.getKey());
                    if (player.getKey().equals(app.getUsername())) {
                        imPlayerNumber = 1;
                        iconPlayer1.setVisible(true);
                    }
                    break;
                case 2:
                    namePlayer2.setText(player.getKey());
                    disableStackPane(waitingPlayer2);
                    enableStackPane(inGamePlayer2);
                    if (player.getKey().equals(app.getUsername())) {
                        imPlayerNumber = 2;
                        iconPlayer2.setVisible(true);
                    }
                    break;
                case 3:
                    namePlayer3.setText(player.getKey());
                    disableStackPane(waitingPlayer3);
                    enableStackPane(inGamePlayer3);
                    if (player.getKey().equals(app.getUsername())) {
                        imPlayerNumber = 3;
                        iconPlayer3.setVisible(true);
                    }
                    break;
                case 4:
                    namePlayer4.setText(player.getKey());
                    disableStackPane(waitingPlayer4);
                    enableStackPane(inGamePlayer4);
                    if (player.getKey().equals(app.getUsername())) {
                        imPlayerNumber = 4;
                        iconPlayer4.setVisible(true);
                    }
                    break;
            }
            showReady(player.getKey(), player.getValue());
            count++;
        }
    }

    public void quit() {
        try {
            client.quitGame();
            app.loadScene(SceneTag.MAINMENU);
            app.setDefaultWindowSize();
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }
}
