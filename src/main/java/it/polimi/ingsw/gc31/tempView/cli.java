package it.polimi.ingsw.gc31.tempView;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.RemoteException;
import java.util.List;

import static it.polimi.ingsw.gc31.OurScanner.scanner;

public class cli extends ui{
    public cli(ClientCommands client) throws RemoteException{
        this.client = client;
    }


    public void runCli() {
        System.out.println("Menu principale");
        while (true) {
            String command = scanner.nextLine();
            switch (command) {
                case "set username":
                    System.out.println("Inserisci l'username: ");
                    String tempUsername = scanner.nextLine();
                    try {
                        client.run(this, tempUsername);
                        client.setUsername(tempUsername);
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (PlayerNicknameAlreadyExistsException e) {
                        System.out.println("[CLI] Username non disponibile!");
                    }
                    break;
                case "crea game":
                    System.out.print("Inserisci il numero di giocatori: ");
                    int maxNumberPlayer = scanner.nextInt();
                    try {
                        client.createGame(maxNumberPlayer);
                        runInitCli();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                case "join game":
                    try {
                        client.getGameList();
                        System.out.print("Inserisci l'id del game a cui vuoi joinare: ");
                        int gameId = scanner.nextInt();
                        client.joinGame(gameId);
                        runInitCli();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    } catch (NoGamesException e) {
                        throw new RuntimeException(e);
                    }
                    break;
                default:
                    System.out.println("Comando non riconosciuto");
                    break;
            }
        }
    }

    private void runInitCli() {
        System.out.println("Menu del gioco");
        while (true) {
            String command = scanner.nextLine();

            switch (command) {
                case "draw gold":
                    try {
                        client.drawGold();
                    } catch (RemoteException e) {
                        throw new RuntimeException(e);
                    }
            }
        }
    }

    @Override
    public void showHand(List<String> hand) {
        hand.forEach(x -> System.out.println("[CLI] "+x));
    }

    @Override
    public void sendListMessages(List<String> msg) {
        msg.forEach(x -> System.out.println("[CLI] "+x));
    }

    @Override
    public void sendMessage(String msg) {
        System.out.println("[CLI] "+msg);
    }
}
