package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Enumeration;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;
import it.polimi.ingsw.gc31.utility.DV;
import it.polimi.ingsw.gc31.utility.IPvalidator;

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
        // pulisce il terminale
        System.out.print("\033[H\033[2J");
        System.out.flush();
        String ipAddress = findIP();

        if (args.length == 0) {
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
                    e.printStackTrace();
                }
            }

        } else if (args.length == 1) {
            if (IPvalidator.isValid(args[0])) {
                try {
                    new TCPServer(args[0]);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                try {
                    new RmiServer(args[0]);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            } else {
                System.out.println("Wrong ip format Please restart the server with a valid IP address. Exiting...");
            }
        }
    }
}