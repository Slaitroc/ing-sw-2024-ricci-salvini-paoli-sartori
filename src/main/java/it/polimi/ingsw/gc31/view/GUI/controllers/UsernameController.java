package it.polimi.ingsw.gc31.view.GUI.controllers;

import io.github.palexdev.materialfx.controls.MFXButton;
import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.view.GUI.SceneTag;
import javafx.fxml.FXML;
// import javafx.scene.image.Image;
// import javafx.scene.image.ImageView;

public class UsernameController extends ViewController {

    @FXML
    private MFXButton getUsername;
    @FXML
    private MFXTextField username;

    @FXML
    private void getUsername() {
        String nickname = username.getText();
        getUsername.setText(nickname);
    }

    // da capire se posso mettere la tag fxml nella classe padre o se devo metterlo
    // nelle figlie insieme all'override (per ora lascio al caso)
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
}
