package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

public class GameListController extends ViewController{

    @FXML
    public TableView tableView;
    @FXML
    public TableColumn gameIDColumn;
    @FXML
    public TableColumn playersColumn;
    @FXML
    public TableColumn joinButtonsColumn;

    @Override
    @FXML
    protected void initialize() {
    }

    /*@Override
    public void setUp(){
        for(String game : app.getListGames()){
            gameIDColumn.setText("Game: " + game.charAt(0));
            playersColumn.setText(game.substring(1,game.length()-1));
        }
    }*/

    public void goMainMenu() {
        app.loadScene(SceneTag.MAINMENU);
    }
}
