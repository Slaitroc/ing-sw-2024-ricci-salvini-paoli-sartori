package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;
import it.polimi.ingsw.gc31.client_server.tcp.TCPClient;
import it.polimi.ingsw.gc31.view.GUI.GUI;
import it.polimi.ingsw.gc31.view.TUI;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

public class Client {
    private static VirtualServer server;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        System.out.print("Vuoi usare RMI (0) o TCP (1): ");
        int networkChoise = scanner.nextInt();

        ClientCommands client = null;
        try {
            if (networkChoise == 0) {
                client = new RmiClient();
            } else if (networkChoise == 1) {
                client = new TCPClient();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        System.out.println("Vuoi usare CLI (0) o GUI (1): ");
        int uiChoise = scanner.nextInt();

        if (uiChoise == 0) {
            new TUI(client).runUI();
        } else if (uiChoise == 1) {
            new GUI(client).runUI();
        }
    }

}
