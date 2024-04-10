package it.polimi.ingsw.gc31.rmi_tutorial.basic_setup;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {

    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(null, 1234);
        MyRemote stub = (MyRemote) registry.lookup("Skeleton");
        stub.printMessage("Ciao sono il client");

    }
}