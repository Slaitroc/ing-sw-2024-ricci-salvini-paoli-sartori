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

    // Se lo username esiste nel Controller allora l'utente non può proseguire.
    @Override
    public boolean clientConnection(VirtualClient client, String nick) throws RemoteException {
        if (Controller.addNickname(nick)) {
            System.out.println(DefaultValues.RMI_SERVER_TAG + "New client connected: " + nick);
            return true;
        } else
            System.out.println(DefaultValues.RMI_SERVER_TAG + "Connection Refused: username already exists");
        return false;
    }

    @Override
    public void printMessageOnServer(String message) {
        System.out.println(message);
    }

}
