package it.polimi.ingsw.gc31.client_server.listeners;

import java.rmi.RemoteException;

/**
 * This interface represents a generic listener that can be used to receive updates on a specific data.
 *
 * @param <T> The type of data this listener receives as an update.
 * @author christian salvini
 */
public interface Listener<T> {
    /**
     * Method called when new data is available for update.
     *
     * @param data The updated data of type T.
     * @throws RemoteException If there are issued during remote communication.
     */
    void update(T data) throws RemoteException;
}
