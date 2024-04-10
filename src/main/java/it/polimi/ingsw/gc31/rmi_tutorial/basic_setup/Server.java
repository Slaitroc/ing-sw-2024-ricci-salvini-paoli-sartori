package it.polimi.ingsw.gc31.rmi_tutorial.basic_setup;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

public class Server implements MyRemote {

    public static void main(String[] args) throws RemoteException {
        Server obj = new Server();
        MyRemote skeleton = (MyRemote) UnicastRemoteObject.exportObject(obj, 1234);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind("Skeleton", skeleton);
        System.out.println("[RMI-Server]");
    }

    @Override
    public void printMessage(String message) throws RemoteException {
        System.out.println(message);
    }
}
