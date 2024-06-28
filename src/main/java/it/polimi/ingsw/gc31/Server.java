package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Enumeration;

import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;
import it.polimi.ingsw.gc31.utility.DV;

/**
 * Main class of the server side
 */
public class Server {

    /**
     * @return The IP address of the machine running the server
     */
    public static String findIP() {
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();

                // ignora le interfacce non attive e quelle di loopback
                if (networkInterface.isLoopback() || !networkInterface.isUp()) {
                    continue;
                }

                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    // se l'indirizzo è locale lo ritorna
                    if (inetAddress.isSiteLocalAddress()) {
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        System.setProperty("java.rmi.server.hostname", "indirizzo ip del client");
        // pulisce il terminale
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String ipAddress = findIP();

        if (DV.forceIP) {
            try {
                new TCPServer(DV.forcedIP);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                new RmiServer(DV.forcedIP);
            } catch (RemoteException e) {
                ServerLog.rmiWrite("Remote exception line 62 Server.java");
                e.printStackTrace();
            }
        } else if (ipAddress != null) {
            try {
                new TCPServer(ipAddress);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            } catch (UnknownHostException e) {

                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                new RmiServer(ipAddress);
            } catch (RemoteException e) {
                ServerLog.rmiWrite("Remote exception line 62 Server.java");
                e.printStackTrace();
            }
        }
    }
}