package it.polimi.ingsw.gc31.client_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import it.polimi.ingsw.gc31.client_server.rmi.RMIServer;

public class Server {

    static VirtualServer engine;

    public static void main(String[] args) {
        String name = "VirtualServer";
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 1) {
            System.out.println("Choose Server Type:");
            System.out.println("1 -> [RMI] ");
            choice = scanner.nextInt();
            if (choice == 1) {
                engine = new RMIServer();
            } else {
                // Handle invalid input
                System.out.println("Invalid choice");
            }
        }

        try {
            ServerRMI();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

    }

    private static void ServerRMI() throws RemoteException {
        String name = "VirtualServerRMI";
        VirtualServer skeleton = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, skeleton);
        System.out.println("[RMI-Server]");
    }

}
