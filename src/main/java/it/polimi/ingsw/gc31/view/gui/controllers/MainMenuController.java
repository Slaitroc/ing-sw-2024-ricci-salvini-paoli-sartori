package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXComboBox;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.collections.FXCollections;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.io.IOException;


public class MainMenuController extends ViewController {


    boolean step1 = true;
    private Stage stage;
    private Parent root;
    private Scene scene;


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
    public StackPane loginMenu;
    @FXML
    public StackPane generalMenu;
    @FXML
    public MFXComboBox<Integer> comboBox;
    @FXML
    public MFXButton createGameButton;

    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
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
                /*scene = new Scene(root);
                stage.setScene(scene);
                stage.show();*/
                app.loadScene(SceneTag.LOBBY);
            } catch (IOException e) {
                Alert alert = new Alert(AlertType.ERROR);
                alert.setTitle("Error 505");
                alert.setHeaderText("Server Error");
                alert.setContentText("Server has crashed. Restart application");

                alert.showAndWait();
            }
        }
    }

    public void showLoginMenu() {
        if (step1) {
            loginMenu.setVisible(true);
            loginMenu.setManaged(true);

            generalMenu.setVisible(false);
            generalMenu.setManaged(false);

            createGameButton.setText("Back");

            imageView1.setVisible(false);
            imageView6.setVisible(true);

            step1 = false;
        } else {
            loginMenu.setVisible(false);
            loginMenu.setManaged(false);

            generalMenu.setVisible(true);
            generalMenu.setManaged(true);

            createGameButton.setText("Create Game");

            imageView6.setVisible(false);

            imageView1.setVisible(true);

            step1 = true;
        }
    }

    public void joinGame() {
        // TODO
    }

    public void showGames() {
        // TODO
    }

    public void showRules() {
        loadRuleBookScene();
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


}
