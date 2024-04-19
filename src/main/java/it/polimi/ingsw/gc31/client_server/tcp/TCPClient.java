package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;
import java.util.Scanner;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.view.UI;

/*
    La classe TCPClient in maniera simile all'RmiClient dovrebbe implementare i metodi di VirtualClient
    che vengono richiamati dalla TUI. Tuttavia, al contrario dell'RmiClient il TCPClient non dovrebbe
    avere il modo di richiamare i metodi del controller ma dovrebbe invece inoltrare il comando richiesto
    al server grazie al VirtualSocketServer
 */
public class TCPClient implements ClientCommands {
    private final BufferedReader input;
    private final PrintWriter output;
    private String username;
    private Integer idGame;

    //TODO Manca il modo per assegnare correttamente il idGame al singolo player. Ora tenuto costantemente null
    public TCPClient() throws IOException {
        Socket serverSocket = new Socket("127.0.0.1", 1200);
        this.input = new BufferedReader(new InputStreamReader(serverSocket.getInputStream()));
        this.output = new PrintWriter(new OutputStreamWriter(serverSocket.getOutputStream()));
        run();
    }

    public void run(){
      new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();
    }

    public void runVirtualServer() throws RemoteException {
        Scanner scan = new Scanner(input);
        String line;
        while (true) {
            line = scan.nextLine();
            switch(line) {
                default -> System.out.println(line);
            }
        }
    }

    @Override
    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException {
        output.println("crea game");
        output.println(maxNumberPlayer);
        output.flush();
    }

    @Override
    public void drawGold() throws RemoteException {
        output.println("draw gold");
        output.flush();
    }

    @Override
    public void joinGame(int gameId) throws RemoteException {

    }

    @Override
    public void getGameList() throws RemoteException {

    }

    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {

    }

    @Override
    public void run(UI ui, String username) throws RemoteException, PlayerNicknameAlreadyExistsException {
    }
}
