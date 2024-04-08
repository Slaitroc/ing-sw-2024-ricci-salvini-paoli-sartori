package it.polimi.ingsw.gc31.client_server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.VirtualServer;
import it.polimi.ingsw.gc31.client_server.fake_controller.Controller;

public class RMIServer implements VirtualServer, Serializable {
    public Controller controller;

    public RMIServer() {
        controller = Controller.getController();
    }

    @Override
    public void clientConnection(VirtualClient client, String nick) throws RemoteException {
        if (Controller.addNickname(nick))
            System.out.println(DefaultValues.RMI_SERVER_TAG + "New client connected: " + nick);
        else
            System.out.println(DefaultValues.RMI_SERVER_TAG + "Connection Refused: username already exists");

    }

}
