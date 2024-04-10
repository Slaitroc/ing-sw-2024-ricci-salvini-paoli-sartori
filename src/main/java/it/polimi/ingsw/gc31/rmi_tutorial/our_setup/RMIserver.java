package it.polimi.ingsw.gc31.rmi_tutorial.our_setup;

import java.rmi.RemoteException;

public class RMIserver implements VirtualServer {

    @Override
    public void printMessage(String message) throws RemoteException {
        System.out.println(message);
    }

    // @Override
    // public void printMessage(RMIClient client, String message) throws
    // RemoteException {
    // System.out.println("[Client: " + client.getName() + "]" + message);
    // }

}
