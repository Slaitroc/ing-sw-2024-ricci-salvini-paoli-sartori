package it.polimi.ingsw.gc31.view.tui;

import java.io.IOException;
import java.rmi.NotBoundException;

import it.polimi.ingsw.gc31.Server;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;

public class TuiTest {

    public static void main(String[] args) throws InterruptedException {
        // IP
        String ipAddress = Server.findIP();

        // Client Launch
        try {

            if (args[0].equals("1")) {

                RmiClient rmiClient = new RmiClient(ipAddress);
                TUI tui = new TUI(rmiClient);
                tui.runUI();

            }
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
        }

    }

}
