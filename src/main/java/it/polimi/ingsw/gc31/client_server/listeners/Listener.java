package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

/**
 * This interface represents a generic listener that can be used to receive updates on a specific data.
 *
 * @author christian salvini
 */
public abstract class Listener {
    final Map<String, VirtualClient> clients;

    Listener(Map<String, VirtualClient> clients) {
        this.clients = clients;
    }


    abstract void update(GameModel model, String username) throws RemoteException;
}
