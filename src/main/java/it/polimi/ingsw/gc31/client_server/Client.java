package it.polimi.ingsw.gc31.client_server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.rmi.RMIClient;

public class Client {

    public VirtualClient virtualClient;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        // TODO qui andrebbe fatto scegliere anche al client il tipo di connessione, per
        // ora solo RMI quindi non implemento
        clientRMI(DefaultValues.DEFAULT_USERNAME);
    }

    public static void clientRMI(String username) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
        VirtualServer stub = (VirtualServer) registry.lookup("VirtualServerRMI");
        stub.printMessageOnServer("Ciao sono il messaggio di test");
        // new RMIClient(server).run();
    }

}
