package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class receives the inputs from the virtual socket server, executes the
 * methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the
 * server
 */
public class SocketClientHandler {
    final IController controller;
    private IGameController gameController;
    private String username;
    private VirtualClient client;
    private final TCPServer server;
    private final BufferedReader input;
    private final PrintWriter output;

    // TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(IController controller, TCPServer server, BufferedReader input, PrintWriter output)
            throws RemoteException {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
    }

    public void runVirtualView() throws IOException {
        String line;
        while ((line = input.readLine()) != null) {

            switch (line) {
                case "connect": {
                    try {
                        line = input.readLine();
                        controller.connect(client, line);
                        this.username = line;
                        output.println("username set");
                        output.flush();
                    } catch (PlayerNicknameAlreadyExistsException e) {
                        output.println("username already exists");
                        output.flush();
                    }
                    System.out.println("[SERVER-Tcp] New client connected. Username: " + username);
                    break;
                }
                case "crea game": {
                    int maxNumberPlayer = Integer.parseInt(input.readLine());
                    gameController = controller.createGame(username, maxNumberPlayer);

                    System.out.println("[SERVER-Tcp] New game created. IdGame: " + maxNumberPlayer);
                    break;
                }
                case "get game list": {
                    try {
                        controller.getGameList(username);
                    } catch (NoGamesException e) {
                        output.println("no game exception");
                        output.flush();
                    }
                    System.out.println("[SERVER-Tcp] Richiesta di gameList. Username: " + username);
                    break;
                }
                case "join game": {
                    int idGame = Integer.parseInt(input.readLine());

                    gameController = controller.joinGame(username, idGame);

                    System.out.println("[SERVER-Tcp] Il giocatore " + username + " ha joinato la partita " + idGame);
                    break;
                }
                case "draw gold": {
                    gameController.drawGold(username);

                    System.out.println("[SERVER-Tcp] Il giocatore " + username + " ha pescato una carta gold");
                    break;
                }
                // It's probably useless. If the user write a not valid command it is
                // immediately
                // notified
                // default : sendMsg("[ SERVER ] [INVALID MESSAGE]");
            }
        }
    }

    private void sendMsg(String line) {
        output.println(line);
        output.flush();
    }
}