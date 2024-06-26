package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.StackPane;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.LinkedHashMap;


public class MainMenuController extends ViewController {

    boolean step1 = true;

    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;
    @FXML
    public ImageView imageView5;
    @FXML
    public ImageView imageView6;
    @FXML
    public ImageView imageView7;
    @FXML
    public ImageView imageView8;
    @FXML
    public ImageView imageView9;

    @FXML
    public StackPane createGameMenu;
    @FXML
    public StackPane generalMenu;
    @FXML
    public StackPane joinGameMenu;
    @FXML
    public MFXComboBox<Integer> comboBox;
    @FXML
    public MFXButton createGameButton;
    @FXML
    public MFXTextField gameID;
    @FXML
    public Label warningLabel;

    @Override
    @FXML
    protected void initialize() {
        comboBox.setItems(FXCollections.observableArrayList(2, 3, 4));
    }

    /**
     * Creates a new game with the selected number of players.
     *
     * <p>This method retrieves the number of players from a combo box, sets it in the application,
     * and attempts to create a game on the server with the specified number of players. If the game
     * is successfully created, the game ID is set in the application. If there is a remote exception
     * during the creation process, a server crash warning is displayed.</p>
     *
     * <p><strong>Note:</strong> The line to load the lobby scene is commented out because it
     * does not work in its current state.</p>
     */
    public void createGame() {
        app.setNumberOfPlayers(comboBox.getValue());
        if (app.getNumberOfPlayers() != null) {
            try {
                app.getClient().createGame(app.getNumberOfPlayers());
                /*app.setCurrentGameID(app.getClient().getGameID())
                System.out.println("GameID: " + app.getCurrentGameID());*/
                /*app.loadScene(SceneTag.LOBBY);*/ //DOES NOT WORK LIKE THIS

            } catch (IOException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        }
    }


    /**
     * Toggles the visibility of the create game menu and updates the UI elements accordingly.
     *
     * <p>This method performs the following actions based on the current state of {@code step1}:</p>
     * <ul>
     *   <li>If {@code step1} is {@code true}:</li>
     *     <li>Displays the create game menu by setting its visibility and managed properties to {@code true}.</li>
     *     <li>Hides the general menu by setting its visibility and managed properties to {@code false}.</li>
     *     <li>Changes the text of the create game button to "Back".</li>
     *     <li>Updates the visibility of image views to reflect the current menu state.</li>
     *     <li>Sets {@code step1} to {@code false}.</li>
     *
     *   <li>If {@code step1} is {@code false}:</li>
     *     <li>Hides the create game menu and join game menu by setting their visibility and managed properties to {@code false}.</li>
     *     <li>Displays the general menu by setting its visibility and managed properties to {@code true}.</li>
     *     <li>Changes the text of the create game button to "Create Game".</li>
     *     <li>Updates the visibility of image views to reflect the current menu state.</li>
     *     <li>Sets {@code step1} to {@code true}.</li>
     *   </ul>
     * <p>Additionally, this method hides the warning label.</p>
     */
    public void showCreateGameMenu() {
        if (step1) {
            createGameMenu.setVisible(true);
            createGameMenu.setManaged(true);

            generalMenu.setVisible(false);
            generalMenu.setManaged(false);

            createGameButton.setText("Back");

            imageView1.setVisible(false);
            imageView6.setVisible(true);

            step1 = false;
        } else {
            createGameMenu.setVisible(false);
            createGameMenu.setManaged(false);
            joinGameMenu.setVisible(false);
            joinGameMenu.setManaged(false);

            generalMenu.setVisible(true);
            generalMenu.setManaged(true);

            createGameButton.setText("Create Game");

            imageView6.setVisible(false);

            imageView1.setVisible(true);

            step1 = true;
        }
        warningLabel.setVisible(false);
    }


    /**
     * Displays the join game menu and updates the UI elements accordingly.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Displays the join game menu by setting its visibility and managed properties to {@code true}.</li>
     *   <li>Hides the general menu by setting its visibility and managed properties to {@code false}.</li>
     *   <li>Changes the text of the create game button to "Back".</li>
     *   <li>Hides the second image view to reflect the current menu state.</li>
     *   <li>Sets {@code step1} to {@code false}.</li>
     * </ul>
     * <p>Additionally, this method hides the warning label.</p>
     */
    public void showJoinGameMenu() {
        joinGameMenu.setVisible(true);
        joinGameMenu.setManaged(true);

        generalMenu.setVisible(false);
        generalMenu.setManaged(false);

        createGameButton.setText("Back");

        imageView2.setVisible(false);

        step1 = false;
        warningLabel.setVisible(false);
    }

    /**
     * Attempts to join a game using the game ID provided by the user.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Validates the game ID to ensure it contains only digits.</li>
     *   <li>If the game ID is valid, attempts to join the game by calling {@code joinGame} on the client.</li>
     *   <li>If a {@link RemoteException} occurs, displays an error alert indicating a server crash.</li>
     *   <li>If the game ID is invalid, displays a warning message instructing the user to input only numbers.</li>
     * </ul>
     */
    public void joinGame() {
        if (gameID.getText().matches("\\d+")) {
            try {
                app.getClient().joinGame(Integer.parseInt(gameID.getText()));

            } catch (RemoteException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        } else {
            warningLabel.setText("Please type only numbers!");
            warningLabel.setVisible(true);
        }
    }

    /**
     * Requests the list of available games from the server.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Attempts to get the game list by calling {@code getGameList} on the client.</li>
     *   <li>If no games are available, throws a {@link RuntimeException} wrapping the {@link NoGamesException}.</li>
     *   <li>If a {@link RemoteException} occurs, displays an error alert indicating a server crash.</li>
     * </ul>
     */
    public void showGames() {
        try {
            app.getClient().getGameList();
        } catch (NoGamesException ignored) {
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error 505");
            alert.setHeaderText("Server Error");
            alert.setContentText("Server has crashed. Please restart application");

            alert.showAndWait();
        }
    }

    /**
     * Loads the rulebook scene.
     *
     * <p>This method sets the application window size to the rulebook window size and then loads the rulebook scene.</p>
     */
    public void showRules() {
        app.setRuleBookWindowSize();
        app.loadScene(SceneTag.RULEBOOK);
    }

    /**
     * Sets a warning message and makes the warning label visible.
     * Called by the server to show a warning
     *
     * @param message the message to be displayed in the warning label
     */
    @Override
    public void setMessage(String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);
    }

    @Override
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
        app.setPlayerList(players);
    }

    //showPointers used to set visible labels when passing on corresponding button______________________________________
    @FXML
    private void showPointer1() {
        if (step1) imageView1.setVisible(true);
        if (!step1) imageView6.setVisible(true);
    }

    @FXML
    private void showPointer2() {
        imageView2.setVisible(true);
    }

    @FXML
    private void showPointer3() {
        imageView3.setVisible(true);
    }

    @FXML
    private void showPointer4() {
        imageView4.setVisible(true);
    }

    @FXML
    public void showPointer5() {
        imageView5.setVisible(true);
    }

    @FXML
    public void showPointer7() {
        imageView7.setVisible(true);
    }

    @FXML
    public void showPointer8() {
        imageView8.setVisible(true);
    }

    @FXML
    public void showPointer9() {
        imageView9.setVisible(true);
    }

    //showPointers used to set invisible labels when exiting the corresponding button___________________________________
    @FXML
    private void hidePointer1() {
        if (step1) imageView1.setVisible(false);
        if (!step1) imageView6.setVisible(false);
    }

    @FXML
    private void hidePointer2() {
        imageView2.setVisible(false);
    }

    @FXML
    private void hidePointer3() {
        imageView3.setVisible(false);
    }

    @FXML
    private void hidePointer4() {
        imageView4.setVisible(false);
    }

    @FXML
    public void hidePointer5() {
        imageView5.setVisible(false);
    }

    @FXML
    public void hidePointer7() {
        imageView7.setVisible(false);
    }

    @FXML
    public void hidePointer8() {
        imageView8.setVisible(false);
    }

    @FXML
    public void hidePointer9() {
        imageView9.setVisible(false);
    }


    @FXML
    public void handleEnterKeyPressed(KeyEvent keyEvent) {
        if (keyEvent.getCode().getName().equals("Enter")) {
            joinGame();
        }
    }
}
