package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;


public class MainMenuController extends ViewController{

    @FXML
    public ImageView imageView1;
    @FXML
    public ImageView imageView2;
    @FXML
    public ImageView imageView3;
    @FXML
    public ImageView imageView4;

    /**
     * This method allows to initialize the fxml's attributes defined in the code
     * and used in the .fxml file. It must be
     * named initialize()...javafx needs it this way
     */
    @Override
    @FXML
    public void initialize() {
    }

    public void createGame() {
       app.loadScene(SceneTag.LOBBY);
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
    private void showPointer1(MouseEvent event) {
        imageView1.setVisible(true);
    }
    @FXML
    private void showPointer2(MouseEvent event) {
        imageView2.setVisible(true);
    }
    @FXML
    private void showPointer3(MouseEvent event) {
        imageView3.setVisible(true);
    }
    @FXML
    private void showPointer4(MouseEvent event) {
        imageView4.setVisible(true);
    }

    @FXML
    private void hidePointer1(MouseEvent event) {
        imageView1.setVisible(false);
    }
    @FXML
    private void hidePointer2(MouseEvent event) {
        imageView2.setVisible(false);
    }
    @FXML
    private void hidePointer3(MouseEvent event) {
        imageView3.setVisible(false);
    }
    @FXML
    private void hidePointer4(MouseEvent event) {
        imageView4.setVisible(false);
    }
}
