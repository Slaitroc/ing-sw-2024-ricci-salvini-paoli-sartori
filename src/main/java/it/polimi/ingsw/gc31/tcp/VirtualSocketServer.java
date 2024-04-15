package it.polimi.ingsw.gc31.tcp;

import java.io.PrintWriter;

public class VirtualSocketServer implements VirtualServer{

    final PrintWriter output;

    public VirtualSocketServer(PrintWriter output){
        this.output = new PrintWriter(output);
    }

    @Override
    public void connect(String username){
        output.println("connect");
        output.println(username);
        output.flush();
    }

    // Tutti questi metodi semplicemente scrivono su "output" la stessa cosa che hanno letto in input.
    // se viene chiamato getGameList questo metodo scrvierà su output esattamente lo stesso messaggio ricevuto
    //
    // Oltre a dover riscrivere esattamente il messaggio ricevuto dall'utente devo anche inoltrare informazioni
    // al server che riguardano l'utente in questione. Quindi dovrò aggiungere delle print con le informazioni
    // necessarie
    @Override
    public void getGameList(String username){
        //output.println(username);

        output.println("mostra game");
        output.flush();
    }

    @Override
    public void createGame(String username, int maxNumberPlayers){
        //output.println(username);

        output.println("crea game");
        output.println(maxNumberPlayers);
        output.flush();
    }

    @Override
    public void joinGame(String username, Integer idGame){
        //output.println(username);

        output.println("join game");
        output.println(idGame);
        output.flush();
    }

    @Override
    public void getHand(String username, Integer idGame){
        //output.println(username);

        output.println("mostra mano");

        //output.println(idGame);

        output.flush();
    }

    @Override
    public void drawGold(String username, Integer idGame){
        //output.println(username);


        output.println("draw gold");

        //output.println(idGame);

        output.flush();
    }

    @Override
    public void info() {
        output.println("info");
        output.flush();
    }
}
