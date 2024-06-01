package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;

public class Server {

    public static void main(String[] args) throws IOException {
        // pulisce il terminale
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String ipAddress = null;

        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface networkInterface = networkInterfaces.nextElement();

            // Ignorare le interfacce di loopback e non attive
            if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                continue;
            }

            Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
            while (inetAddresses.hasMoreElements()) {
                InetAddress inetAddress = inetAddresses.nextElement();
                // Verificare se l'indirizzo è un indirizzo locale
                if (inetAddress.isSiteLocalAddress()) {
                    // Stampa dell'indirizzo IP locale
                    ipAddress = inetAddress.getHostAddress();
                }
            }
        }

        if (ipAddress != null) {
            new TCPServer(ipAddress);
            new RmiServer(ipAddress);
        }
    }
}