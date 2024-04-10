package it.polimi.ingsw.gc31.rmi_tutorial.our_setup;

import java.rmi.RemoteException;
import java.util.Scanner;

public class RMIClient {

    private String name;
    private VirtualServer server;

    public RMIClient(String name, VirtualServer server) {
        this.name = name;
        this.server = server;
    }

    public void sendMessage(String message) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String input = null;
        while (input != "q") {
            String text = "[Client: " + name + "] " + message;
            server.printMessage(text);
            input = scanner.nextLine();
        }
        scanner.close();
    }

}
