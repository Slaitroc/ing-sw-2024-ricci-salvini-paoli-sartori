package it.polimi.ingsw.gc31.rmi;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.VirtualController;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiServer implements VirtualServer {
    // il server controlla che
    private List<String> usernameList;
    private Registry registry;

    public RmiServer() throws RemoteException {
        registry = LocateRegistry.createRegistry(1234);

        VirtualController virtualController = new Controller();
        VirtualController stubController = (VirtualController) UnicastRemoteObject.exportObject(virtualController, 0);
        registry.rebind("VirtualController", stubController);

        System.out.println("[SERVER] Adder bound");
    }
}
