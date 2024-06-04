package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;

/**
 * This interface represents a generic listener that can be used to receive updates on a specific data.
 *
 * @author christian salvini
 */
public interface Listener {
    void update(GameModel model, String username) throws RemoteException;
}
