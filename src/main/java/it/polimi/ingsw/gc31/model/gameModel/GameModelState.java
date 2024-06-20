package it.polimi.ingsw.gc31.model.gameModel;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.IllegalStateOperationException;
import it.polimi.ingsw.gc31.exceptions.ObjectiveCardNotChosenException;
import it.polimi.ingsw.gc31.exceptions.WrongIndexSelectedCard;
import it.polimi.ingsw.gc31.model.player.Player;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.List;

public interface GameModelState {
    Map<String, Player> initGame(GameModel model, LinkedHashMap<String, VirtualClient> clients) throws IllegalStateOperationException;
    void chooseSecretObjective(GameModel model, String username, Integer index) throws IllegalStateOperationException;
    void playStarter(GameModel model, String username) throws IllegalStateOperationException, ObjectiveCardNotChosenException;
    void play(GameModel model, String username, Point point) throws IllegalStateOperationException;
    void drawGold(GameModel model, String username, int index) throws IllegalStateOperationException;
    void drawResource(GameModel model, String username, int index) throws IllegalStateOperationException;
    void setSelectCard(GameModel model, String username, int index) throws IllegalStateOperationException, WrongIndexSelectedCard;
    void changeSide(GameModel model, String username) throws IllegalStateOperationException;
    void changeStarterSide(GameModel model, String username) throws IllegalStateOperationException;
    void detectEndGame(GameModel model) throws IllegalStateOperationException;
    void endGame(GameModel model) throws IllegalStateOperationException;
    void disconnectPlayer(GameModel model, String username) throws IllegalStateOperationException;
}
