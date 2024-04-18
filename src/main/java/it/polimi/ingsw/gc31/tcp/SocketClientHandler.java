package it.polimi.ingsw.gc31.tcp;

//import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
//import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;

import java.io.BufferedReader;
//import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;
//import java.util.Scanner;

// This class receives the inputs from the virtual socket server, executes the methods requested by the client
// sends the data, that need to be updated and showed to other clients, to the server
public class SocketClientHandler implements VirtualClient {
    // Devo avere il "MainGameController", non il controller riferito alla singola
    // partita
    final IController controller;
    private IMainGameController mainGameController;
    private Integer idGame;
    private String username;
    private IPlayerController playerController;

    private final SocketServer server;
    private final BufferedReader input;
    private final PrintWriter output;

    // TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(IController controller, SocketServer server, BufferedReader input,
            PrintWriter output, Integer progressiveIdGame) throws RemoteException {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        this.idGame = progressiveIdGame;
    }

    // // TODO Gestire meglio le eccezioni
    // // This method executes the functions of the controller based on what input
    // it
    // // receives. Also need to call the server
    // // in order to send updates to all other clients
    // public void runVirtualClient() throws IOException,
    // PlayerNicknameAlreadyExistsException {
    // String line;
    // while ((line = input.readLine()) != null) {
    //
    // switch (line) {
    // case "connect": {
    // this.username = input.readLine();
    // controller.connect(this, username);
    //
    // sendMsg("[ SERVER ] Connected. Possible commands: [ crea game, mostra game,
    // join game ]");
    // break;
    // }
    // case "crea game": {
    // // Convert a String to an int
    // int maxNumberPlayer = Integer.parseInt(input.readLine());
    // mainGameController = controller.createGame(username, maxNumberPlayer);
    //
    // sendMsg("[ SERVER ] A game has been created");
    // // output.println("> Creata partita con id: " + idGame);
    // runCliInitGame();
    // break;
    // }
    // case "mostra game": {
    // // System.out.println("> Richiesta di mostrare i game ricevuta");
    // controller.getGameList(username);
    // break;
    // }
    // case "join game": {
    // output.println("[ SERVER ] Inserisci l'ID del game a cui vuoi partecipare:
    // ");
    // int idGame = Integer.parseInt(input.readLine());
    //
    // mainGameController = controller.joinGame(username, idGame);
    //
    // runCliInitGame();
    // break;
    // }
    // // It's probably useless. If the user write a not valid command it is
    // // immediately
    // // notified
    // // default : sendMsg("[ SERVER ] [INVALID MESSAGE]");
    // }
    // }
    // }
    //
    // public void runCliInitGame() throws RemoteException {
    // sendMsg("[ SERVER ] Game joined. Possible commands: [ info , mostra mano ,
    // draw gold ]");
    //
    // Scanner scan = new Scanner(input);
    // while (true) {
    // String command = scan.nextLine();
    //
    // switch (command) {
    // case "info": {
    // if (mainGameController.isGameStarted()) {
    // sendMsg("[ SERVER ] Il gioco è iniziato");
    // } else {
    // sendMsg("[ SERVER ] Il gioco non è ancora iniziato");
    // }
    // }
    // case "mostra mano": {
    // sendMsg("[ SERVER ] Richiesta ricevuta");
    // playerController.getHand();
    // }
    // case "draw gold": {
    // sendMsg("[ SERVER ] Richiesta ricevuta");
    // playerController.drawGold();
    // }
    // // Same as before, probably is useless
    // // default -> sendMsg("[ SERVER ] [INVALID MESSAGE]");
    // }
    // }
    // }

    @Override
    public void setUsername(String n) throws RemoteException {

    }

    public void setPlayerController(IPlayerController playerController) {
        this.playerController = playerController;
    }

    @Override
    public void setGameID(int i) throws RemoteException {

    }

    @Override
    public int getGameID() throws RemoteException {
        return 0;
    }

    @Override
    public boolean createGame(int i) throws RemoteException {
        return false;
    }

    @Override
    public List<String> showGames() throws RemoteException, NoGamesException {
        return null;
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {

    }

    @Override
    public boolean ready() throws RemoteException {
        return false;
    }

    @Override
    public List<String> showHand() throws RemoteException {
        return null;
    }

    @Override
    public void drawGold() throws RemoteException {

    }

    @Override
    public boolean isReady() throws RemoteException {
        return false;
    }

    @Override
    public void startGame() throws RemoteException {

    }
    //
    // // La logica dei metodi non dovrebbe essere implementata dal controller?
    // @Override
    // public void showHand(List<String> jsonHand) throws RemoteException {
    // output.println("[ SERVER ] Le tue carte sono: ");
    // jsonHand.stream().forEach(output::println);
    // output.flush();
    // }
    //
    // @Override
    // public void showGameList(List<String> gameList) throws RemoteException {
    // output.println("[ SERVER ] Le partite disponibili sono");
    // gameList.stream().forEach(output::println);
    // output.flush();
    // }
    //
    // @Override
    // public void reportError(String details) throws RemoteException {
    // output.println("[ SERVER ] [ERROR] " + details);
    // output.flush();
    // }

    private void sendMsg(String line) {
        output.println(line);
        output.flush();
    }

}
