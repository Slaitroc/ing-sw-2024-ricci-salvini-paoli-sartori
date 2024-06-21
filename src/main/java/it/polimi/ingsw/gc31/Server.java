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

public class Server {

    public static String findIP(){
        Enumeration<NetworkInterface> networkInterfaces;
        try {
            networkInterfaces = NetworkInterface.getNetworkInterfaces();
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
                        return inetAddress.getHostAddress();
                    }
                }
            }
        } catch (SocketException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
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
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Errore di rete durante la connessione al server.\n");
                errorMessage.append("Dettagli:\n");
                errorMessage.append("- Tipo di errore: UnknownHostException\n");
                errorMessage
                        .append("- Messaggio di errore: Impossibile risolvere l'indirizzo IP \"xxx.xxx.xxx.xxx\"\n");
                errorMessage.append("- Cause possibili:\n");
                errorMessage.append("  - L'indirizzo IP è stato digitato erroneamente.\n");
                errorMessage.append("  - Problemi di connessione di rete.\n");
                errorMessage.append("  - Il server DNS non è in grado di risolvere l'indirizzo IP.\n");
                errorMessage.append("  - L'indirizzo IP non è valido o non esiste.\n");
                errorMessage.append("  - Problemi di configurazione del sistema.\n");
                errorMessage.append("- Suggerimenti per la risoluzione:\n");
                errorMessage.append("  - Verifica la correttezza dell'indirizzo IP.\n");
                errorMessage.append("  - Controlla la connessione di rete.\n");
                errorMessage.append("  - Assicurati che il server DNS sia configurato correttamente.\n");
                errorMessage.append("  - Verifica che l'indirizzo IP sia valido e che esista.\n");
                errorMessage.append("  - Esamina la configurazione del sistema per eventuali errori.\n");
                String printableMessage = errorMessage.toString();
                ServerLog.tcpWrite(printableMessage);
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
                StringBuilder errorMessage = new StringBuilder();
                errorMessage.append("Errore di rete durante la connessione al server.\n");
                errorMessage.append("Dettagli:\n");
                errorMessage.append("- Tipo di errore: UnknownHostException\n");
                errorMessage
                        .append("- Messaggio di errore: Impossibile risolvere l'indirizzo IP \"xxx.xxx.xxx.xxx\"\n");
                errorMessage.append("- Cause possibili:\n");
                errorMessage.append("  - L'indirizzo IP è stato digitato erroneamente.\n");
                errorMessage.append("  - Problemi di connessione di rete.\n");
                errorMessage.append("  - Il server DNS non è in grado di risolvere l'indirizzo IP.\n");
                errorMessage.append("  - L'indirizzo IP non è valido o non esiste.\n");
                errorMessage.append("  - Problemi di configurazione del sistema.\n");
                errorMessage.append("- Suggerimenti per la risoluzione:\n");
                errorMessage.append("  - Verifica la correttezza dell'indirizzo IP.\n");
                errorMessage.append("  - Controlla la connessione di rete.\n");
                errorMessage.append("  - Assicurati che il server DNS sia configurato correttamente.\n");
                errorMessage.append("  - Verifica che l'indirizzo IP sia valido e che esista.\n");
                errorMessage.append("  - Esamina la configurazione del sistema per eventuali errori.\n");
                String printableMessage = errorMessage.toString();
                ServerLog.tcpWrite(printableMessage);
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