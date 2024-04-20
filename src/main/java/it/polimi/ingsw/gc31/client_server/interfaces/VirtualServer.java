package it.polimi.ingsw.gc31.client_server.interfaces;

import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.Remote;
import java.rmi.RemoteException;

public interface VirtualServer extends Remote {
    public void RMIserverWrite(String text) throws RemoteException;

    public IController connect(VirtualClient client, String username)
            throws RemoteException, PlayerNicknameAlreadyExistsException;
}
