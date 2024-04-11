package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.VirtualServer;

import java.rmi.RemoteException;

public class TCPServer implements VirtualServer {

    @Override
    public void clientConnection(VirtualClient client, String nick) throws RemoteException {

    }
}
