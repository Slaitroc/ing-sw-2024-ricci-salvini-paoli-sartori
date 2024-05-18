package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.Client;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;
import it.polimi.ingsw.gc31.view.gui.GUI;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;

import java.io.IOException;

import static it.polimi.ingsw.gc31.OurScanner.scanner;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;

public class UsernameController extends ViewController {

    private GUI gui;

    @FXML
    private MFXButton getUsername;
    @FXML
    private MFXTextField username;

    @FXML
    private void getUsername() {
        String nickname = username.getText();

        /*boolean usernameIsValid = false;
        while (!usernameIsValid) {
            try {
                app.getClient().setUsername(nickname);
                usernameIsValid = true;
            } catch (IOException e) {
                e.printStackTrace();
            } catch (PlayerNicknameAlreadyExistsException e) {
                getUsername.setText("Username already taken. Try again");
            }
        }*/

        getUsername.setText(nickname);
    }

    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
    @Override
    @FXML
    public void initialize() {
        /* example */
        // Image image = new
        // Image(getClass().getResource("/it/polimi/ingsw/gc31/Images/Misc/Misc1.jpg").toExternalForm());
        // image1.setImage(image);

    }

    @FXML
    private void loadStartScene() {
        app.loadScene(SceneTag.START);
    }


/*    @FXML
    private void loadLobbyScene() {
        app.loadScene(SceneTag.LOBBY);
    }

    @FXML
    private void loadLoginScene() {
        app.loadScene(SceneTag.LOBBY);
    }*/
}
