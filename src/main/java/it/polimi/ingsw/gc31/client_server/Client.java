package it.polimi.ingsw.gc31.client_server;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Scanner;

import it.polimi.ingsw.gc31.client_server.rmi.RMIClient;

public class Client {

    public VirtualClient virtualClient;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.println("Type your username...must be unique!");
        Scanner scanner = new Scanner(System.in);
        String username = scanner.nextLine().toString();
        clientRMI(username);
    }

    public static void clientRMI(String username) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServerRMI");
        new RMIClient(server).setUsername(username).run();
    }

}
