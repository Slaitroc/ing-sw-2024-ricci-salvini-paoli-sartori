package it.polimi.ingsw.gc31.rmi;

import it.polimi.ingsw.gc31.controller.*;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;

import java.util.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualView {
    final VirtualController controller;
    private VirtualMainGameController mainGameController;
    private Integer idGame;
    private String username;
    private VirtualPlayerController playerController;

    protected RmiClient(VirtualController controller) throws RemoteException {
        this.controller = controller;
        this.idGame = null;
    }

    private RmiClient setUsername(String username) {
        this.username = username;
        return this;
    }

    private void run() throws RemoteException, NotBoundException, PlayerNicknameAlreadyExistsException {
        controller.connect(this, username);
        runCli();
    }

    private void runCli() throws RemoteException, NotBoundException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scan.nextLine().toString();

            if (command.equals("crea game")) {
                System.out.print("Inserisci il numero di giocatori della partita:");
                int maxNumberPlayer = scan.nextInt();
                mainGameController = controller.createGame(username, maxNumberPlayer);

                System.out.println("Creata partita con id: " + idGame);

                runCliInitGame();

            } else if (command.equals("mostra game")) {
                controller.getGameList(username);
            } else if (command.equals("join game")) {
                System.out.print("Inserisci l'ID del game a cui vuoi partecipare: ");
                int idGame = scan.nextInt();

                mainGameController = controller.joinGame(username, idGame);

                runCliInitGame();
            }
        }
    }

    public void runCliInitGame() throws RemoteException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scan.nextLine().toString();

            if (command.equals("info")) {
                if (mainGameController.isGameStarted()) {
                    System.out.println("Il gioco è iniziato");
                } else {
                    System.out.println("Il gioco non è ancora iniziato");
                }
            } else if (command.equals("mostra mano")) {
                playerController.getHand();
            } else if (command.equals("draw gold")) {
                playerController.drawGold();
            }
        }
    }

    @Override
    public void setPlayerController(VirtualPlayerController playerController) throws RemoteException {
        this.playerController = playerController;
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
        Registry registry = LocateRegistry.getRegistry("127.0.0.1", 1234);
        VirtualController controller = (VirtualController) registry.lookup("VirtualController");
        System.out.print("Inserisci il tuo nome utente: ");
        Scanner scan = new Scanner(System.in);
        String setUser = scan.nextLine().toString();

        try {
            new RmiClient(controller).setUsername(setUser).run();
        } catch (PlayerNicknameAlreadyExistsException e) {
            throw new RuntimeException(e);
        }
    }
}