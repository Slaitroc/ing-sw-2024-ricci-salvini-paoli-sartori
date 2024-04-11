package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.OurScanner;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;

import java.rmi.RemoteException;
import java.util.Scanner;

public class TUI extends UI {

    public TUI(VirtualClient client) {
        this.client = client;
    }

    @Override
    public void uiRunUI() {
        show_Options();
        String line = DefaultValues.TUI_START_LINE_SYMBOL;
        System.out.println(line);
        String input;
        input = OurScanner.scanner.next();
        switch (input) {
            case "n":
                break;
            default:
                break;
        }
    }

    @Override
    protected void uiOptions() {
        System.out.println("__Commands List__");
        System.out.println("nick : shows your username");
    }

    @Override
    protected void uiNicknames() {
        System.out.println("Username -> ");

    }

    @Override
    public IController choose_username(VirtualServer server_stub, VirtualClient client) throws RemoteException {
        String message = "Type your username:";
        String input;
        IController c = null;
        do {
            System.out.println(message);
            input = OurScanner.scanner.nextLine();

            try {
                c = server_stub.clientConnection(client, input);
            } catch (RemoteException e) {
                e.printStackTrace();
            }
            message = "Username already exists... \nTry a different username:";
        } while (c == null);
        client.setUsername(input);
        return c;
    }

}
