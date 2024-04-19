package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class receives the inputs from the virtual socket server, executes the methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the server
 */
public class SocketClientHandler{
    final IController controller;
    private IGameController gameController;
    private String username;
    private IPlayerController playerController;
    private VirtualClient client;

    private final TCPServer server;
    private final BufferedReader input;
    private final PrintWriter output;


    //TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(IController controller, TCPServer server, BufferedReader input, PrintWriter output) throws RemoteException {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
    }

    public void runVirtualView() throws IOException, PlayerNicknameAlreadyExistsException {
        String line;
        while((line = input.readLine()) != null){

            switch(line) {
                case "set username": {
                    this.username = input.readLine();
                    System.out.println("[SERVER-Tcp] Username settato per: "+username);
                    sendMsg("[ SERVER ] L'username è stato settato correttamente");
                }
                case "connect" : {
                    controller.connect(client, username);
                    System.out.println("[SERVER-Tcp] New client connected. Username: "+username);
                    break;
                }
                case "crea game" : {
                    int maxNumberPlayer = Integer.parseInt(input.readLine());
                    gameController = controller.createGame(username, maxNumberPlayer);

                    System.out.println("[SERVER-Tcp] New game create. IdGame: "+maxNumberPlayer);
                    runCliInitGame();
                    break;
                }
                case "mostra game" : {
                    try {
                        controller.getGameList(username);
                    } catch (NoGamesException e) {
                        throw new RuntimeException(e);
                    }
                    System.out.println("[SERVER-Tcp] Richiesta di gameList. Username: "+username);
                    break;
                }
                case "join game" : {
                    int idGame = Integer.parseInt(input.readLine());

                    gameController = controller.joinGame(username, idGame);

                    System.out.println("[SERVER-Tcp] Il giocatore "+username+" ha joinato la partita "+idGame);
                    runCliInitGame();
                    break;
                }
                case "draw gold": {
                    playerController.drawGold();

                    System.out.println("[SERVER-Tcp] Il giocatore "+username+" ha pescato una carta gold");
                    break;
                }
                // It's probably useless. If the user write a not valid command it is immediately
                // notified
                // default : sendMsg("[ SERVER ] [INVALID MESSAGE]");
            }
        }
    }


    public void runCliInitGame() throws RemoteException {
        /*
        sendMsg("[ SERVER ] Game joined. Possible commands: [ info , mostra mano , draw gold ]");

        Scanner scan = new Scanner(input);
        while (true) {
            String command = scan.nextLine();

            switch(command){
                case "info" : {
                    if (mainGameController.isGameStarted()) {
                        sendMsg("[ SERVER ] Il gioco è iniziato");
                    } else {
                        sendMsg("[ SERVER ] Il gioco non è ancora iniziato");
                    }
                }
                case "mostra mano" : {
                    sendMsg("[ SERVER ] Richiesta ricevuta");
                    playerController.getHand();
                }
                case "draw gold" : {
                    sendMsg("[ SERVER ] Richiesta ricevuta");
                    playerController.drawGold();
                }
                // Same as before, probably is useless
                //default -> sendMsg("[ SERVER ] [INVALID MESSAGE]");
            }
        }

         */
    }
    private void sendMsg(String line){
        output.println(line);
        output.flush();
    }
    public void setPlayerController(IPlayerController playerController) {
        this.playerController = playerController;
    }
}