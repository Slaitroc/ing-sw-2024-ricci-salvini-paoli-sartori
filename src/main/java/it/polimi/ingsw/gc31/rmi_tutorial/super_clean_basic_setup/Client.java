package it.polimi.ingsw.gc31.rmi_tutorial.super_clean_basic_setup;

import java.rmi.NotBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class Client {
    public static void main(String[] args) throws RemoteException, NotBoundException {
        Registry registry = LocateRegistry.getRegistry(null);
        Remote stub = registry.lookup("Skeleton");
        // stub.{metodi che non ci sono}---> per questo serve l'interfaccia che estende
        // da Remote.
        // Se li definissi direttamente in server non andrebbe comunque bene perchè non
        // li rileverebbe.

    }
}
