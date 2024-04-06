package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.controller.Controller;

import javax.sound.sampled.Control;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Map;

// This class receives the inputs from the virtual socket server, executes the methods requested by the client
// sends the data, that need to be updated and showed to other clients, to the server
public class SocketClientHandler implements VirtualView{
    // Devo avere il "MainGameController", non il controller riferito alla singola partita
    final Controller controller;
    final SocketServer server;
    final BufferedReader input;
    final PrintWriter output;

    private Integer idGame;
    private String username;

    //TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(Controller controller, SocketServer server, BufferedReader input, PrintWriter output){
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        this.idGame = null;
    }

    //TODO Gestire meglio le eccezioni
    public void runVirtualView() throws IOException{

        //TODO verificare correttezza del luogo di stampa. System.out.println o output.println?
        // anche nei casi più avanti, "trova e sostituisci"
        System.out.println("> Inserisci username: ");
        this.username = System.in.toString();

        String line;
        while((line = input.readLine()) != null){
            System.out.println("> ");

            switch(line) {
                case "draw gold" -> controller.drawGold(username);
                case "show hand" -> controller.getHand(username);
                case "mostra game" -> controller.getGameList(username);
                case "crea game" -> {
                    System.out.print("Inserisci il numero di giocatori della partita:");
                    //TODO verificare correttezza della funzione
                    int maxNumberPlayer = input.read();
                    idGame = controller.createGame(username, maxNumberPlayer);
                    System.out.println("Creata partita con id: " + idGame);
                }
                case "join game" -> {
                    System.out.print("Inserisci l'id del game:");
                    idGame = input.read();
                    controller.joinGame(username, idGame);

                }
            }
        }
    }

    @Override
    public void showHand(List<String> jsonHand){

    }

    @Override
    public void showGameList(List<String> gameList){

    }

    @Override
    public void reportError(String details){

    }
}
