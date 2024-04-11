package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.rmi.RMIClient;

import java.rmi.RemoteException;

public class TCPClient implements VirtualClient {

    @Override
    public RMIClient setUsername(String nick) throws RemoteException {
        return null;
    }
}
