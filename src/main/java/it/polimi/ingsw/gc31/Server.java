package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.net.InetAddress;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;

public class Server {

    public static void main(String[] args) throws IOException {
        // pulisce il terminale
        System.out.print("\033[H\033[2J");
        System.out.flush();

        InetAddress localHost = InetAddress.getLocalHost();
        String ipAddress = localHost.getHostAddress();

        new TCPServer(ipAddress);
        new RmiServer(ipAddress);
    }

}
