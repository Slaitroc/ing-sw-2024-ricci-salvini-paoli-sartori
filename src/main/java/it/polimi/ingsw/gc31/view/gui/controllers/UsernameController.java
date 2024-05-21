package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;

//import static it.polimi.ingsw.gc31.OurScanner.scanner;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;

public class UsernameController extends ViewController {

    @FXML
    private MFXTextField usernameField;
    @FXML
    private Label warningLabel;

    @FXML
    private void login() {
        app.setUsername(usernameField.getText());

        if (app.getUsername().isEmpty()) {
            System.out.println("Username cannot be empty!");
            warningLabel.setVisible(true);
            warningLabel.setText("Username cannot be empty!");
        } else {
            try {
                client.setUsernameCall(usernameField.getText());
                loadMainMenuScene();
            } catch (IOException e) {
                warningLabel.setVisible(true);
                warningLabel.setText("Server Error! Please restart the app.");
            }/* catch (PlayerNicknameAlreadyExistsException e) {
                warningLabel.setVisible(true);
                warningLabel.setText("Username already exists!");
            }*/
        }
    }

    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
    @Override
    @FXML
    protected void initialize() {
    }

    @FXML
    private void loadMainMenuScene() {
        app.loadScene(SceneTag.MAINMENU);
    }

    @FXML
    private void loadStartScene() {
        app.loadScene(SceneTag.START);
    }

    @FXML
    private void handleEnterKeyPressed(KeyEvent event){
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
