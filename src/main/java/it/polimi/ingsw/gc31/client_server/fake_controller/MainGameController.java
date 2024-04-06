package it.polimi.ingsw.gc31.client_server.fake_controller;

import java.rmi.Remote;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.fake_controller.intefaces.IMainGameController;
import it.polimi.ingsw.gc31.model.GameModel;

public class MainGameController implements IMainGameController {

    // general controllers map
    private final Map<String, MGCelement> gcMap;
    private int playerNumber;
    private GameModel model;

    protected MainGameController(String nick, int playerNumber) {
        this.playerNumber = playerNumber;
        gcMap = new HashMap<>();
        gcMap.put(DefaultValues.GAME_CONTROLLER_MAP_KEY, new GameController());
        gcMap.put(nick, new PlayerController());
    }

    public synchronized void addPlayer(String nick) {
        gcMap.put(nick, new PlayerController());
        createModel();
    }

    // FIX il nome è da cambiare
    private boolean createModel() {
        if (gcMap.size() == playerNumber + 1) {
            this.model = new GameModel();// FIX da aggiornare il costruttore
            return true;
        }

        return false;
    }

}
