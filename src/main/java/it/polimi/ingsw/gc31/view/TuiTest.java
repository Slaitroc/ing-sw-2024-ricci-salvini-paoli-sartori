package it.polimi.ingsw.gc31.view;

import java.io.IOException;
import java.rmi.NotBoundException;


import it.polimi.ingsw.gc31.Server;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;
import it.polimi.ingsw.gc31.client_server.tcp.TCPClient;
import it.polimi.ingsw.gc31.view.tui.TUI;

public class TuiTest {

    public static void main(String[] args) throws InterruptedException {
        //IP
        String ipAddress = Server.findIP();

        //Client Launch
        try {

        if (args[0].equals("1")){

            RmiClient rmiClient = new RmiClient(ipAddress);
            TUI tui = new TUI(rmiClient);
            tui.runUI();
            rmiClient.setUsernameCall("RMIclient1");
            Thread.sleep(1000);
            rmiClient.createGame(2);
            Thread.sleep(1000);
            rmiClient.setReady(true);
        }
        else{ 
            TCPClient tcpClient = new TCPClient(ipAddress);
            TUI tui = new TUI(tcpClient);
            tui.runUI();
            tcpClient.setUsernameCall("TCPclient1");
            Thread.sleep(1000);
            tcpClient.joinGame(0);
            Thread.sleep(1000);
            tcpClient.setReady(true);
        }
        } catch (IOException | NotBoundException e) {
            e.printStackTrace();
    }
    
    
        
    }
    
}
