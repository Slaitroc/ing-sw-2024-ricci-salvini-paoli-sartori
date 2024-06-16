package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.ServerSocket;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import it.polimi.ingsw.gc31.utility.DV;


public class TCPServer {
    final ServerSocket listenSocket;
    private final List<SocketClientHandler> handlers = new ArrayList<>();

    public void TCPserverWrite(String text) {
        System.out.println(DV.ANSI_YELLOW + DV.TCP_SERVER_TAG + DV.ANSI_RESET + text);
    }

    // TODO Gestire meglio eccezioni
    public TCPServer(String ipaddress) throws NumberFormatException, UnknownHostException, IOException {
        this.listenSocket = new ServerSocket(DV.TCP_PORT, 50, InetAddress.getByName("0.0.0.0"));
        TCPserverWrite("Server IP " + ipaddress);
        TCPserverWrite("Server in ascolto sulla porta " + DV.TCP_PORT);

        try {
            runServer();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void runServer() throws IOException {
        TCPserverWrite("Server created");
        new Thread(() -> {

            while (true) {
                try {
                    Socket clientSocket = this.listenSocket.accept();
                    TCPserverWrite(
                            "New connection detected from ip: " + clientSocket.getInetAddress().getHostAddress());

                    ObjectOutputStream socketTx = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream socketRx = new ObjectInputStream(clientSocket.getInputStream());

                    SocketClientHandler handler = new SocketClientHandler(socketRx, socketTx);
                    this.handlers.add(handler);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        }).start();
    }
}