package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TCPServer {
    final ServerSocket listenSocket;
    private final IController controller;
    private Map<String, SocketClientHandler> tempClients;

    // TODO Gestire meglio eccezioni
    public TCPServer(ServerSocket listenSocket, IController controller) {
        this.listenSocket = listenSocket;
        this.controller = controller;
        this.tempClients = new HashMap<>();
    }

    public void runServer() throws IOException {
        System.out.println("[TCP] Server created");
        Socket clientSocket = null;

        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            // Se non sono presenti partite non ho nulla nella mappa e tantomeno i
            // controller
            // perciò se la mappa risulta null creo un primo controller da passare al primo
            // client

            SocketClientHandler handler = new SocketClientHandler(
                    controller, this,
                    new BufferedReader(socketRx), new PrintWriter(socketTx));

            new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException e) {
                    throw new RuntimeException();
                }
            });
        }

    }

    /*
     * // Chiamata dal client come prima cosa appena creato. Così lo posso
     * aggiungere alla mappa e lista
     * public void connect(SocketClientHandler client, String username){
     * usernameList.add(username);
     * tempClients.put(username, client);
     *
     * System.out.println("> Nuovo Client ( " + username +
     * " ) collegato correttamente");
     * }
     */

}