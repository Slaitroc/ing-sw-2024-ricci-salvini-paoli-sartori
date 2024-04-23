package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;

import java.awt.*;
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface IGameController extends Remote {
    public void drawGold(String username) throws RemoteException;

    public void drawGoldCard1(String username) throws RemoteException;

    public void drawGoldCard2(String username) throws RemoteException;

    public void drawResource(String username) throws RemoteException;

    public void drawResourceCard1(String username) throws RemoteException;

    public void drawResourceCard2(String username) throws RemoteException;

    //ObjectiveCard chooseSecretObjective(String username, ObjectiveCard obj1);

    //void play(String username, Point point);

    //void selectCard(String username, int index);
}
