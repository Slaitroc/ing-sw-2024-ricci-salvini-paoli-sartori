package it.polimi.ingsw.gc31.client_server.listeners;

import java.rmi.RemoteException;

public interface Listener<T> {
    void update(T data) throws RemoteException;
}
