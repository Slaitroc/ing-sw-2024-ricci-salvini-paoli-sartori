package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IMainGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.List;
import java.util.Scanner;

public class RmiClient extends UnicastRemoteObject implements VirtualClient {
    private IController controller;
    private IMainGameController mainGameController;
    private Integer idGame;
    private String username;
    private IPlayerController playerController;

    public RmiClient(VirtualServer server_stub) throws RemoteException {
        this.username = DefaultValues.DEFAULT_USERNAME;
        this.controller = setUsername(server_stub);
        this.idGame = null;
    }

    @Override
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
    }

    private IController setUsername(VirtualServer server_stub) throws RemoteException {
        Scanner scanner = new Scanner(System.in);
        String message = "Type your username:";
        String input;
        IController c;
        do {
            System.out.println(message);
            input = scanner.nextLine();
            c = server_stub.clientConnection(this, input);
            message = "Username already exists... \nTry a different username:";
        } while (c == null);
        this.username = input;
        return c;
    }

    public void run() throws RemoteException, NotBoundException, PlayerNicknameAlreadyExistsException {
        runCli();
    }

    private void runCli() throws RemoteException, NotBoundException {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("> ");
            String command = scan.nextLine().toString();

            if (command.equals("create game")) {
                System.out.print("Inserisci il numero di giocatori della partita:");
                int maxNumberPlayer = scan.nextInt();
                mainGameController = controller.createGame(username, maxNumberPlayer);

                System.out.println("Creata partita con id: " + idGame);

                runCliInitGame();

            } else if (command.equals("show games")) {
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
    public void setPlayerController(IPlayerController playerController) throws RemoteException {
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
    public void sendMessage(String details) throws RemoteException {
        System.err.println("\n[ERROR] " + details + "\n> ");
    }
}