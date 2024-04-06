package it.polimi.ingsw.gc31.client_server.fake_controller;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import it.polimi.ingsw.gc31.client_server.fake_controller.intefaces.IController;

public class Controller implements IController {

    private static final Controller singleton = new Controller();
    private static final List<MainGameController> mgcList = new ArrayList<>();
    private static final Set<String> nicknames = new HashSet<>();

    private Controller() {
    }

    public static boolean addNickname(String nick) {
        if (nicknames.add(nick))
            return true;
        else
            return false;
    }

    public static void createGame(String nick, int playerNumber) {
        mgcList.add(new MainGameController(nick, playerNumber));
    }

    public static synchronized Controller getController() {
        return singleton;
    }

    public static synchronized MainGameController getMGController(int id) {
        return mgcList.get(id);
    }
}
