package it.polimi.ingsw.gc31;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;

public class Server {

    static VirtualServer engine;

    // Il main attiva entrambi i server
    public static void main(String[] args) {
        try {
            new RmiServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
