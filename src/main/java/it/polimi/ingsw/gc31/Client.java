package it.polimi.ingsw.gc31;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.NotBoundException;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

public class Client {
    private static VirtualServer virtualServer_stub;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        virtualServer_stub = (VirtualServer) LocateRegistry.getRegistry("127.0.0.1", 1234).lookup("VirtualServer");
        try {
            new RmiClient(virtualServer_stub).run();
        } catch (PlayerNicknameAlreadyExistsException e) {
            throw new RuntimeException(e);
        }

    }

}
