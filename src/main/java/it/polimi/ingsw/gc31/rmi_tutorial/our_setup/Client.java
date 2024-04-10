package it.rmi30l.our_setup;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    private static Registry registry;

    public static void main(String[] args) throws RemoteException, NotBoundException {
        registry = LocateRegistry.getRegistry(null, 1234);
        setUpRMI();

    }

    private static void setUpRMI() throws RemoteException, NotBoundException {
        VirtualServer stub = (VirtualServer) registry.lookup("Skeleton");
        // stub.printMessage("Ciao sono il client");
        new RMIClient("Lorenzo", stub).sendMessage("Ciao sono il client");
        ;

    }
}