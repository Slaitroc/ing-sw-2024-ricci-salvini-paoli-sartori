package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.view.gui.GUIApplication;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.rmi.RemoteException;
import java.util.List;

public class GameListController extends ViewController {

    @FXML
    public TableView<GameInstance> tableView;
    @FXML
    public TableColumn<GameInstance, String> gameIDColumn;
    @FXML
    public TableColumn<GameInstance, String> playersColumn;
    @FXML
    public TableColumn<GameInstance, Void> actionColumn;

    @Override
    @FXML
    protected void initialize() {
        gameIDColumn.setCellValueFactory(cellData -> cellData.getValue().gameIDProperty());
        playersColumn.setCellValueFactory(cellData -> cellData.getValue().playersProperty());

        actionColumn.setCellFactory(new Callback<TableColumn<GameInstance, Void>, TableCell<GameInstance, Void>>() {
            @Override
            public TableCell<GameInstance, Void> call(final TableColumn<GameInstance, Void> param) {
                final TableCell<GameInstance, Void> cell = new TableCell<GameInstance, Void>() {
                    private final Button btn = new Button("Join");

                    {
                        btn.getStyleClass().add("small-button");
                        btn.setOnAction(event -> {
                            app.setNumberOfPlayers(Integer.parseInt(getTableView().getItems().get(getIndex()).getPlayers().charAt(2) + ""));
                            for(int i = 0; i<Integer.parseInt(getTableView().getItems().get(getIndex()).getPlayers().charAt(0) + ""); i++){
                                GUIApplication.getPlayerList().add("Player" + i);
                            }
                            app.setCurrentGameID(Integer.parseInt(getTableView().getItems().get(getIndex()).getGameID()));
                            GameInstance GameInstance = getTableView().getItems().get(getIndex());
                            joinGame(GameInstance);
                        });
                    }

                    @Override
                    public void updateItem(Void item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                        } else {
                            setGraphic(btn);
                        }
                    }
                };
                return cell;
            }
        });
    }

    private void joinGame(GameInstance gameInstance){

        try {
            client.joinGame(Integer.parseInt(gameInstance.getGameID()));
        } catch (RemoteException e) {
            show_ServerCrashWarning();
        }

    }

    @Override
    public void setUp() {
        gamesList = app.getListGames();
        tableView.setItems(gamesList);
    }

    public void goMainMenu() {
        app.loadScene(SceneTag.MAINMENU);
    }
}
