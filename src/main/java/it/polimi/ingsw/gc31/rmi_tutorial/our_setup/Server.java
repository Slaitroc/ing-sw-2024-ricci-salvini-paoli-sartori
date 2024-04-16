package it.polimi.ingsw.gc31.rmi_tutorial.our_setup;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server {

    private static VirtualServer skeleton;

    public static void main(String[] args) throws RemoteException {
        setUpRMI();
    }

    private static void setUpRMI() throws RemoteException {
        skeleton = new RMIserver();
        skeleton = (VirtualServer) UnicastRemoteObject.exportObject(skeleton, 1234);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("Skeleton", skeleton);
        System.out.println("[RMI-Server]");
    }

}
