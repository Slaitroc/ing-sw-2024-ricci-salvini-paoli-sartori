package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.gui.GameInstance;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;

import java.io.IOException;
import java.rmi.RemoteException;
import java.util.Arrays;

public class GameListController extends ViewController {

    @FXML
    public TableView<GameInstance> tableView;
    @FXML
    public TableColumn<GameInstance, String> gameIDColumn;
    @FXML
    public TableColumn<GameInstance, String> playersColumn;
    @FXML
    public TableColumn<GameInstance, Void> actionColumn;

    /**
     * Initializes the controller. Sets up the table columns and the action button.
     */
    @Override
    @FXML
    protected void initialize() {

        gameIDColumn.setCellValueFactory(cellData -> cellData.getValue().gameIDProperty());
        playersColumn.setCellValueFactory(cellData -> cellData.getValue().playersProperty());

        actionColumn.setCellFactory(new Callback<>() {
            @Override
            public TableCell<GameInstance, Void> call(final TableColumn<GameInstance, Void> param) {
                return new TableCell<>() {
                    private final Button btn = new Button("Join");

                    {
                        btn.getStyleClass().add("small-button");
                        btn.getStyleClass().add("rounded-button");
                        btn.setOnAction(event -> {
                            app.setCurrentGameID(Integer.parseInt(getTableView().getItems().get(getIndex()).getGameID()));
                            app.setNumberOfPlayers(Integer.parseInt(getTableView().getItems().get(getIndex()).getPlayers().split("/")[1]));
                            joinGame(app.getCurrentGameID());
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
            }
        });
    }

    /**
     * Joins a game with the given ID.
     * @param id The ID of the game to join.
     */
    private void joinGame(Integer id) {

        try {
            client.joinGame(id);
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }

    }

    /**
     * Sets up the game list view.
     */
    @Override
    public void setUp() {
        gamesList = app.getListGames();
        tableView.setItems(gamesList);
    }

    public void goMainMenu() {
        app.loadScene(SceneTag.MAINMENU);
    }

    public void refresh() {
        try {
            app.getClient().getGameList();
        } catch (NoGamesException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error 505");
            alert.setHeaderText("Server Error");
            alert.setContentText("Server has crashed. Please restart application");

            alert.showAndWait();
        }
    }

}

