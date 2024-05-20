package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.DefaultValues;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
    final ServerSocket listenSocket;
    private final List<SocketClientHandler> handlers = new ArrayList<>();

    public void TCPserverWrite(String text) {
        System.out.println(DefaultValues.ANSI_YELLOW + DefaultValues.TCP_SERVER_TAG + DefaultValues.ANSI_RESET + text);
    }

    // TODO Gestire meglio eccezioni
    public TCPServer(ServerSocket listenSocket) {
        this.listenSocket = listenSocket;
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
                    TCPserverWrite("New connection detected...");

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