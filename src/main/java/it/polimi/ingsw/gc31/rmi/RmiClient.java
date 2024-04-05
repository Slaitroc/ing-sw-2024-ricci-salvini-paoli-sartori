package it.polimi.ingsw.gc31.rmi;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualServer server;
    private Integer idGame;
    private String username;

    protected RmiClient(VirtualServer server) throws RemoteException {
        this.server = server;
        this.idGame = null;
    }

    private RmiClient setUsername(String username) {
        this.username = username;
        return this;
    }

    private void run() throws RemoteException {
        this.server.connect(this, username);
        runCli();
    }

    private void runCli() throws RemoteException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scan.nextLine().toString();

            if (command.equals("draw gold")) {
                server.drawGold(username, idGame);
            } else if (command.equals("show hand")) {
                server.getHand(username, idGame);
            } else if (command.equals("mostra game")) {
                // se il giocatore ha già creato una partita non può chiamare getGameList
                server.getGameList(username);
            } else if (command.equals("crea game")) {
                System.out.print("Inserisci il numero di giocatori della partita:");
                int maxNumberPlayer = scan.nextInt();
                idGame = server.createGame(username, maxNumberPlayer);
                System.out.println("Creata partita con id: " + idGame);
            } else if (command.equals("join game")) {
                System.out.print("Inserisci l'id del game:");
                idGame = scan.nextInt();
                server.joinGame(username, idGame);
            }
        }
    }

    @Override
    public void showHand(List<String> jsonHand) throws RemoteException {
        System.out.println("Le tue carte sono: ");
        jsonHand.stream().forEach(System.out::println);
    }

    @Override
    public void showGameList(List<String> gameList) throws RemoteException {
        System.out.println("Le partite disponibili sono");
        gameList.stream().forEach(System.out::println);
    }

    @Override
    public void reportError(String details) throws RemoteException {
        System.err.println("\n[ERROR] " + details + "\n> ");
    }

    public static void main(String[] args) throws RemoteException, NotBoundException {
        System.out.print("Inserisci il tuo nome utente: ");
        Scanner scan = new Scanner(System.in);
        String setUser = scan.nextLine().toString();

        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
        VirtualServer server = (VirtualServer) registry.lookup("VirtualServer");

        new RmiClient(server).setUsername(setUser).run();
    }
}