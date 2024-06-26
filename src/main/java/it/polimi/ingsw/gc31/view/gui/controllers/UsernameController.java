package it.polimi.ingsw.gc31.view.gui.controllers;

import io.github.palexdev.materialfx.controls.MFXTextField;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.IOException;
import java.rmi.RemoteException;


public class UsernameController extends ViewController {

    @FXML
    private MFXTextField usernameField;
    @FXML
    private Label warningLabel;

    /**
     * Handles the user login process by setting the username and communicating with the server.
     *
     * <p>This method performs the following actions:</p>
     * <ul>
     *   <li>Sets the application's username using the text from {@code usernameField}.</li>
     *   <li>Checks if the username is empty and displays a warning message if it is.</li>
     *   <li>If the username is not empty, attempts to set the username on the server using the client.</li>
     *   <li>If an {@link RemoteException} occurs, displays a server crash warning.</li>
     * </ul>
     */
    @FXML
    private void login() {
        app.setUsername(usernameField.getText());

        if (app.getUsername().isEmpty()) {
            warningLabel.setVisible(true);
            warningLabel.setText("Username cannot be empty!");
        } else {
            try {
                client.setUsernameCall(usernameField.getText());
                client.setUsernameResponse(usernameField.getText());
            } catch (IOException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        }
    }

    @Override
    public void setMessage(String message) {
        warningLabel.setText(message);
        warningLabel.setVisible(true);
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
    private void loadStartScene() {
        app.loadScene(SceneTag.START);
    }

    /**
     * Allow the enter functionality linking the Enter key event to the login function
     *
     * @param event the keyboard event
     */
    @FXML
    private void handleEnterKeyPressed(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            login();
        }
    }

}
