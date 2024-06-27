package it.polimi.ingsw.gc31.view.gui.controllers;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.view.gui.GameInstance;
import it.polimi.ingsw.gc31.view.gui.SceneTag;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.util.Callback;

import java.rmi.RemoteException;
import java.util.LinkedHashMap;

public class GameListController extends ViewController {

    @FXML
    public TableView<GameInstance> tableView;
    @FXML
    public TableColumn<GameInstance, Integer> gameIDColumn;
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

        gameIDColumn.setCellValueFactory(cellData -> cellData.getValue().gameIDProperty().asObject());
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
                            app.setCurrentGameID(getTableView().getItems().get(getIndex()).getGameID());
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
     * Calls client method to join the specified game
     * Show server crash in case of RemoteException
     *
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
     * Sets up the game list TableView.
     */
    @Override
    public void setUp() {
        gamesList = app.getListGames();
        tableView.setItems(gamesList);
    }

    /**
     * Set the player when entering a match so that the LobbyScene can load already showing who is in the game
     *
     * @param players List of players that will be found in the match that we are about to enter
     */
    @Override
    public void handleInGamePlayers(LinkedHashMap<String, Boolean> players) {
        app.setPlayerList(players);
    }

    /**
     * Load MainMenu scene
     */
    public void goMainMenu() {
        app.loadScene(SceneTag.MAINMENU);
    }

    /**
     * Calls the getGameList() client function that reload the scene with the updated List of games
     * (I suspect the Scene itself not been reloaded but the method setUp is called so ether way it works)
     * IOException caught showing common error window
     */
    public void refresh() {
        try {
            app.getClient().getGameList();
        } catch (NoGamesException ignored) {
        } catch (RemoteException e) {
            show_ServerCrashWarning(e.toString());
            e.getStackTrace();
        }
    }

}

