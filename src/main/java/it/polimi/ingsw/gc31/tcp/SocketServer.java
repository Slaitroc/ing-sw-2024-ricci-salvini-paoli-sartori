package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SocketServer {
    // TODO Verifica correttezza più avanti
    final ServerSocket listenSocket;

    private final Map<Integer, Controller> gameList;
    private final List<String> usernameList;
    // TODO Verifica correttezza più avanti, rispetto ad rmi utilizzo
    // SocketClientHandler
    private Map<String, SocketClientHandler> tempClients;
    private Integer progressiveIdGame;

    // TODO Gestire meglio eccezioni
    public SocketServer(ServerSocket listenSocket) {
        this.listenSocket = listenSocket;
        this.gameList = new HashMap<>();
        this.usernameList = new ArrayList<>();
        this.tempClients = new HashMap<>();
        this.progressiveIdGame = 0;
    }

    // TODO Gestire meglio le eccezioni
    private void runServer() throws IOException {
        System.out.println("Server created");
        Socket clientSocket = null;

        while ((clientSocket = this.listenSocket.accept()) != null) {
            InputStreamReader socketRx = new InputStreamReader(clientSocket.getInputStream());
            OutputStreamWriter socketTx = new OutputStreamWriter(clientSocket.getOutputStream());

            // Se non sono presenti partite non ho nulla nella mappa e tantomeno i
            // controller
            // perciò se la mappa risulta null creo un primo controller da passare al primo
            // client

            SocketClientHandler handler = new SocketClientHandler(/* gameList.get(progressiveIdGame) */
                    Controller.getController(), this,
                    new BufferedReader(socketRx), new PrintWriter(socketTx), progressiveIdGame);

            /*new Thread(() -> {
                try {
                    handler.runVirtualView();
                } catch (IOException | PlayerNicknameAlreadyExistsException e) {
                    throw new RuntimeException(e);
                }
            }).start();*/
        }

    }

    // public void broadcastUpdate(){}

    // TODO Gestire meglio eccezioni
    public static void main(String[] args) throws IOException {
        // TODO Eliminare se rimane inutilizzato
        String host = "127.0.0.1";
        // TODO Utilizzare parametri formali
        int port = Integer.parseInt("1234");

        ServerSocket listenSocket = new ServerSocket(port);

        new SocketServer(listenSocket).runServer();
    }
}