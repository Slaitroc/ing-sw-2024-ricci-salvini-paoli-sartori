package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


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

    public void createGame() {
        app.setNumberOfPlayers(comboBox.getValue());
        if (app.getNumberOfPlayers() != null) {
            try {
                app.getClient().createGame(app.getNumberOfPlayers());
                /*app.setCurrentGameID(app.getClient().getGameID());
                System.out.println("GameID: " + app.getCurrentGameID());*/
                /*app.loadScene(SceneTag.LOBBY);*/ //DOES NOT WORK LIKE THIS

            } catch (IOException e) {
                show_ServerCrashWarning();
            }
        }
    }

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

    public void showJoinGameMenu() {
        joinGameMenu.setVisible(true);
        joinGameMenu.setManaged(true);

        generalMenu.setVisible(false);
        generalMenu.setManaged(false);

        createGameButton.setText("Back");

        imageView2.setVisible(false);

        step1=false;
        warningLabel.setVisible(false);
    }

    public void joinGame() {
        if (gameID.getText().matches("\\d+")){
            try {
                app.getClient().joinGame(Integer.parseInt(gameID.getText()));

            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error 505");
                alert.setHeaderText("Server Error");
                alert.setContentText("Server has crashed. Please restart application");

                alert.showAndWait();
            }
        }
        else {
            warningLabel.setText("Please type only numbers!");
            warningLabel.setVisible(true);
        }
    }

    public void showGames() {
        try {
            app.getClient().getGameList();
        } catch (NoGamesException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Alert alert = new Alert(AlertType.ERROR);
            alert.setTitle("Error 505");
            alert.setHeaderText("Server Error");
            alert.setContentText("Server has crashed. Please restart application");

            alert.showAndWait();
        }
        app.loadScene(SceneTag.GAMELIST);
    }

    public void showRules() {
        loadRuleBookScene();
    }

    @Override
    public void setMessage(String message){
        warningLabel.setText(message);
        warningLabel.setVisible(true);
    }

    @FXML
    private void loadRuleBookScene() {
        app.setRuleBookSize();
        app.loadScene(SceneTag.RULEBOOK);
    }

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
