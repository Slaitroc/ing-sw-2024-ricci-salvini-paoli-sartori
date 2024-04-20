package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;
import it.polimi.ingsw.gc31.client_server.tcp.TCPClient;
import it.polimi.ingsw.gc31.utility.CliPrint;
import it.polimi.ingsw.gc31.view.GUI.GUI;
import it.polimi.ingsw.gc31.view.TUI;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

public class Client {
    private static VirtualServer server;

    public static void main(String[] args) throws NotBoundException, RemoteException {
        // pulisce il terminale
        System.out.print("\033[H\033[2J");
        System.out.flush();

        CliPrint.coloredPrintPurple("Chose connection:");
        System.out.println("\t1->RMI\n\t2->TCP");
        int networkChoise = Integer.parseInt(scanner.nextLine());

        ClientCommands client = null;
        try {
            if (networkChoise == 1) {
                client = new RmiClient();
            } else if (networkChoise == 2) {
                client = new TCPClient();
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (NotBoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        CliPrint.coloredPrintPurple("Chose UI:");
        System.out.println("\t1 -> TUI\n\t2 -> GUI:");
        int uiChoise = Integer.parseInt(scanner.nextLine());

        if (uiChoise == 1) {
            new TUI(client).runUI();
        } else if (uiChoise == 2) {
            new GUI(client).runUI();
        }
    }

}
