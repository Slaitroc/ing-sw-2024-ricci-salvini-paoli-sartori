package it.polimi.ingsw.gc31.client_server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.VirtualServer;

public class RMIClient implements VirtualClient, Serializable {
    private VirtualServer server;
    private String nick;

    public RMIClient(VirtualServer server) {
        this.server = server;
    }

    public static void main(String[] args) {

    }

    @Override
    public RMIClient setUsername(String nick) {
        this.nick = nick;
        return this;
    }

    public void run() throws RemoteException {
        this.server.clientConnection(this, nick);
    }

}
