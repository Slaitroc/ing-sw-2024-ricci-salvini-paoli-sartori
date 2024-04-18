package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.PrintWriter;
import java.rmi.RemoteException;

public class VirtualSocketServer implements VirtualServer {
    final PrintWriter output;

    public VirtualSocketServer(PrintWriter output){
        this.output = new PrintWriter(output);
    }

    @Override
    public IController clientConnection(VirtualClient client, String username) throws RemoteException {
        return null;
    }

    public void drawGold(){
        output.println("draw gold");
        output.flush();
    }
}