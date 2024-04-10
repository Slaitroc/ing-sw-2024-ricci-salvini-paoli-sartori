package it.polimi.ingsw.gc31.tcp;

import java.io.*;
import java.net.Socket;
import java.net.UnknownHostException;
import java.rmi.RemoteException;
import java.util.Scanner;

public class SocketClient {
    final BufferedReader input;
    final VirtualSocketServer server;
    private String username;
    private Integer idGame;

    //TODO Manca il modo per assegnare correttamente il idGame al singolo player. Ora tenuto costantemente null
    protected SocketClient(BufferedReader input, PrintWriter output){
        this.input = input;
        this.server = new VirtualSocketServer(output);
        this.username = null;
        this.idGame = null;
    }

    private void run() throws RemoteException {
        new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
        runCli();
    }

    // TODO Dovrebbe servire per leggere i messaggi in arrivo dal server e richiamare i rispettivi metodi
    private void runVirtualServer() throws IOException{
        String line;

        while((line = input.readLine()) != null){
            // Qui dovrei leggere i messaggi in arrivo dal server e eseguire di conseguenza i metodi corrispondenti
            // ad esempio metodi che segnalano errori oppure segnali di update del model
            switch(line){
                case "" -> System.out.println("Message");
            }
        }
    }

    private void runCli() throws RemoteException{
        Scanner scan = new Scanner(System.in);
        String line;

        System.out.print("> Inserisci username: ");
        this.username = scan.nextLine();
        server.connect(username);


        // Qui devo leggere ciò che scrive l'utente e inoltro la richiesta di esecuzione al clientHandler (?)
        // Attraverso l'utilizzo del VirtualSocketServer. Per inoltrare però devo avere informazioni sul player
        // che non ho ora, come username. Aggiungo ora
        while(true) {
            System.out.print("> ");
            line = scan.nextLine();

            switch(line){
                case "draw gold" -> server.drawGold(username, idGame);
                case "show hand" -> server.getHand(username, idGame);
                case "mostra game" -> server.getGameList(username);
                case "crea game" -> {
                    System.out.println("> Inserisci il numero di giocatori della partita: ");
                    int manNumPlayers = scan.nextInt();
                    server.createGame(username, manNumPlayers);
                    System.out.println("Creata partita con id: " + idGame);
                }
                case "join game" -> {
                    System.out.print("> Inserisci l'id del game: ");
                    idGame = scan.nextInt();
                    server.joinGame(username, idGame);
                }
            }
        }
    }

    public static void main(String[] args) throws IOException, UnknownHostException {
        //TODO Verificare valori opportuni di host/port per il corretto funzioanamento finale
        String host = "127.0.0.1";
        int port = Integer.parseInt("1234");

        Socket serverSocket = new Socket(host, port);

        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        new SocketClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).run();
    }
}
