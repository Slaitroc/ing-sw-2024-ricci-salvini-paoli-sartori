package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements VirtualServer {
    private IController controller;

    private void serverWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_RESET + text);
    }

    public RmiServer(IController controller) throws RemoteException {
        this.controller = controller;
        LocateRegistry.createRegistry(1100).rebind("VirtualServer", UnicastRemoteObject.exportObject(this, 0));
        serverWrite("Server created");
    }

    @Override
    public IController connect(VirtualClient client, String username)
            throws RemoteException {
        try {
            controller.connect(client, username);
            serverWrite("New client connected: " + username);
            return controller;
        } catch (PlayerNicknameAlreadyExistsException e) {
            return null;
        }
    }

    @Override
    public void RMIserverWrite(String text) throws RemoteException {
        serverWrite(text);
    }

}
