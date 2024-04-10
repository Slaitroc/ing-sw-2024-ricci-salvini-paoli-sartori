package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.VirtualController;
import it.polimi.ingsw.gc31.controller.VirtualMainGameController;
import it.polimi.ingsw.gc31.controller.VirtualPlayerController;

import javax.sound.sampled.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

// This class receives the inputs from the virtual socket server, executes the methods requested by the client
// sends the data, that need to be updated and showed to other clients, to the server
public class SocketClientHandler implements VirtualView{
    // Devo avere il "MainGameController", non il controller riferito alla singola partita
    private final VirtualController controller;
    private VirtualMainGameController mainGameController;
    private VirtualPlayerController playerController;
    private final SocketServer server;
    private final BufferedReader input;
    private final PrintWriter output;

    private final Integer idGame;
    private String username;

    //TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(VirtualController controller, SocketServer server, BufferedReader input, PrintWriter output, Integer progressiveIdGame){
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        this.idGame = progressiveIdGame;
    }

    //TODO Gestire meglio le eccezioni
    // This method executes the functions of the controller based on what input it receives. Also need to call the server
    // in order to send update to all other clients
    public void runVirtualView() throws IOException{
        String line;
        while((line = input.readLine()) != null){

            switch(line) {
                // TODO Chri usa il metodo connect del VirtualController tuttavia esso richiede
                //  una VirtualView mentre io possiedo un SocketClientHandler. Richiamo
                //  il metodo del server socket.
                case "connect" -> server.connect(this, input.readLine());
                case "mostra game" -> controller.getGameList(username);
                // TODO Per questi ultimi due casi ho bisogno di informazioni aggiuntive che devono
                //  essere richieste all'utente. Non so dove sto stampanda le richieste... Questa parte di "logica" la
                //  posso però demandare al VirtualSocketServer, quando riceve uno di questi due comandi richiederà
                //  le opportune informazioni e invierà al SocketClientHandler una singola stringa con tutto cò di cui c'è
                //  bisogno
                case "crea game" -> {
                    output.println("Inserisci il numero di giocatori della partita:");
                    int maxNumberPlayer = input.read();
                    mainGameController = controller.createGame(username, maxNumberPlayer);

                    output.println("Creata partita con id: " + idGame);

                    runCliInitGame();
                }
                case "join game" -> {
                    output.println("Inserisci l'ID del game a cui vuoi partecipare: ");
                    int idGame = input.read();

                    mainGameController = controller.joinGame(username, idGame);

                    runCliInitGame();
                }
                default -> output.println("[INVALID MESSAGE]");
            }
        }
    }

    public void runCliInitGame() throws RemoteException {
        output.println("> Game joined");

        // System.in oppure input ?
        Scanner scan = new Scanner(input);
        while (true) {
            String command = scan.nextLine();

            switch(command){
                case "info" -> {
                    if (mainGameController.isGameStarted()) {
                        output.println("Il gioco è iniziato");
                    } else {
                        output.println("Il gioco non è ancora iniziato");
                    }
                }
                case "mostra mano" -> playerController.getHand();
                case "draw gold" -> playerController.drawGold();
                default -> output.println("[INVALID MESSAGE]");
            }
        }
    }

    public void setPlayerController(VirtualPlayerController playerController){
        this.playerController = playerController;
    }

    // La logica dei metodi non dovrebbe essere implementata dal controller?
    @Override
    public void showHand(List<String> jsonHand) throws RemoteException {
        output.println("Le tue carte sono: ");
        jsonHand.stream().forEach(output::println);
    }

    @Override
    public void showGameList(List<String> gameList) throws RemoteException {
        output.println("Le partite disponibili sono");
        gameList.stream().forEach(output::println);
    }

    @Override
    public void reportError(String details) throws RemoteException {
        output.println("\n[ERROR] " + details + "\n> ");
    }
}
