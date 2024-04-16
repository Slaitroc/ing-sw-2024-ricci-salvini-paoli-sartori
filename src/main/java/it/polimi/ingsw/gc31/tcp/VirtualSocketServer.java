package it.polimi.ingsw.gc31.tcp;

import java.io.PrintWriter;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

public class VirtualSocketServer implements VirtualServer{
    final PrintWriter output;

    public VirtualSocketServer(PrintWriter output){
        this.output = new PrintWriter(output);
    }

    @Override
    public IController clientConnection(VirtualClient client, String username) throws RemoteException {
        return null;
    }
}
