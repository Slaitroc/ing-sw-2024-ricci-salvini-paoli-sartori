package it.polimi.ingsw.gc31.client_server.rmi;

import java.io.Serializable;
import java.rmi.RemoteException;
import java.util.Scanner;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.VirtualClient;
import it.polimi.ingsw.gc31.client_server.VirtualServer;

//
public class RMIClient implements VirtualClient, Serializable {
    private VirtualServer server;
    private String nick;

    public RMIClient(VirtualServer server) {
        this.server = server;
        this.nick = DefaultValues.DEFAULT_USERNAME;
    }

    public static void main(String[] args) {

    }

    @Override
    public RMIClient setUsername() throws RemoteException {
        boolean isValid = false;
        String input;
        Scanner scanner = new Scanner(System.in);
        System.out.println("Type your username...must be unique!");
        while (!isValid) {
            input = scanner.nextLine().toString().toLowerCase();
            if (server.clientConnection(this, input)) {
                this.nick = input;
                isValid = true;
            } else {
                System.out.println("Choose a different username:");
            }

        }
        scanner.close();
        return this;
    }

    public void run() throws RemoteException {
        setUsername();
    }

}
