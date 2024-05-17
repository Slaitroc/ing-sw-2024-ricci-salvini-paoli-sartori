package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;

import java.io.IOException;

//import static it.polimi.ingsw.gc31.OurScanner.scanner;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;

public class UsernameController extends ViewController {

    @FXML
    private MFXButton getUsername;
    @FXML
    private MFXTextField username;
    @FXML
    private Label warningLabel;

    @FXML
    private void login() {
        String nickname = username.getText();

        boolean usernameIsValid = false;

        if (nickname.isEmpty()) {
            System.out.println("Username cannot be empty!");
            warningLabel.setVisible(true);
            warningLabel.setText("Username cannot be empty!");
        } else {
            try {
                client.setUsername(nickname);
                System.out.println("Username set to: " + nickname);
                System.out.println("Switching to MainMenu scene...");
                loadMainMenuScene();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PlayerNicknameAlreadyExistsException e) {
                warningLabel.setVisible(true);
                warningLabel.setText("Username already exists!");
            }
        }
    }

    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
    @Override
    @FXML
    public void initialize() {

    }

    @FXML
    private void loadMainMenuScene() {
        app.loadScene(SceneTag.MAINMENU);
    }

    @FXML
    private void loadStartScene() {
        app.loadScene(SceneTag.START);
    }

}
