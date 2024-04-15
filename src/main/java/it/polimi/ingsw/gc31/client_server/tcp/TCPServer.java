package it.polimi.ingsw.gc31.client_server.tcp;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public class TCPServer implements VirtualServer {

    @Override
    public IController clientConnection(VirtualClient client, String username) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'clientConnection'");
    }

}
