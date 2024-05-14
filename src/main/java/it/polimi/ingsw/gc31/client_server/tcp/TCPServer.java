package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class TCPServer {
    final ServerSocket listenSocket;
    private final IController controller;

    public void TCPserverWrite(String text) {
        System.out.println(DefaultValues.ANSI_YELLOW + DefaultValues.TCP_SERVER_TAG + DefaultValues.ANSI_RESET + text);
    }

    // TODO Gestire meglio eccezioni
    public TCPServer(ServerSocket listenSocket, IController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
    }

    public void runServer() throws IOException {
        TCPserverWrite("Server created");

        while (true) {
            Socket clientSocket = this.listenSocket.accept();
            TCPserverWrite("New connection detected...");
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            SocketClientHandler handler = new SocketClientHandler(this.controller, this,
                    new BufferedReader(socketRx), new PrintWriter(socketTx), new ObjectInputStream(clientSocket.getInputStream()));

            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
        }

    }
}