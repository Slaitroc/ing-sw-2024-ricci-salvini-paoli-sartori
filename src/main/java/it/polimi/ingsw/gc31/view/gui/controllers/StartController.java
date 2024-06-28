package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.utility.DV;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.rmi.RemoteException;

/**
 * Checks for existing tokens and asks for reconnection or load login scene
 */
public class StartController extends ViewController {

    @FXML
    public VBox reconnectChoice;
    @FXML
    public VBox startVbox;
    @FXML
    public Label helloName;

    @FXML
    private void loadLoginScene() {
        if (app.getClient().getToken().getToken() != DV.defaultToken) {
            try {
                app.getClient().setUsernameCall(null);
            } catch (IOException e) {
                show_ServerCrashWarning(e.toString());
                e.getStackTrace();
            }
        } else {
            app.loadScene(SceneTag.USERNAME);
        }
    }

    @Override
    public void setMessage(String username) {
        helloName.setText("Hello " + username);
        startVbox.setVisible(false);
        startVbox.setManaged(false);
        reconnectChoice.setVisible(true);
        reconnectChoice.setManaged(true);
    }

    @Override
    @FXML
    protected void initialize() {

    }

    @FXML
    public void reconnect() {
        try{
            app.getClient().reconnect(true);
            System.out.println("Sent reconnect");
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

    public void doNotReconnect() {
        try{
            app.getClient().reconnect(false);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
        app.loadScene(SceneTag.USERNAME);
    }
}
