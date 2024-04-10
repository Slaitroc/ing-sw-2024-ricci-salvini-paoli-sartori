package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.rmi.VirtualView;

import java.io.PrintWriter;

public class VirtualSocketServer {

    final PrintWriter output;

    public VirtualSocketServer(PrintWriter output){
        this.output = new PrintWriter(output);
    }

    public void connect(String username){
        output.print("connect ");
        output.print(username);
        output.flush();
    }

    // Tutti questi metodi semplicemente scrivono su "output" la stessa cosa che hanno letto in input.
    // se viene chiamato getGameList questo metodo scrvierà su output esattamente lo stesso messaggio ricevuto
    //
    // Oltre a dover riscrivere esattamente il messaggio ricevuto dall'utente devo anche inoltrare informazioni
    // al server che riguardano l'utente in questione. Quindi dovrò aggiungere delle print con le informazioni
    // necessarie
    public void getGameList(String username){
        //output.println(username);

        output.println("mostra game");
        output.flush();
    }
    public void createGame(String username, int maxNumberPlayers){
        //output.println(username);

        output.println("crea game ");
        output.println(maxNumberPlayers);
        output.flush();
    }
    public void joinGame(String username, Integer idGame){
        //output.println(username);

        output.println("join game ");
        output.println(idGame);
        output.flush();
    }
    public void getHand(String username, Integer idGame){
        //output.println(username);

        output.println("show hand");

        //output.println(idGame);

        output.flush();
    }
    public void drawGold(String username, Integer idGame){
        //output.println(username);


        output.println("draw gold ");

        //output.println(idGame);

        output.flush();
    }

}
