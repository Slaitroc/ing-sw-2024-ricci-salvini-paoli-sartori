package it.polimi.ingsw.gc31.client_server.tcp;

import java.io.*;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

/*
    La classe TCPClient in maniera simile all'RmiClient dovrebbe implementare i metodi di VirtualClient
    che vengono richiamati dalla TUI. Tuttavia, al contrario dell'RmiClient il TCPClient non dovrebbe
    avere il modo di richiamare i metodi del controller ma dovrebbe invece inoltrare il comando richiesto
    al server grazie al VirtualSocketServer
 */
public class TCPClient implements VirtualClient {


    final BufferedReader input;
    final VirtualSocketServer server;
    private String username;
    private Integer idGame;

    //TODO Manca il modo per assegnare correttamente il idGame al singolo player. Ora tenuto costantemente null
    protected TCPClient(BufferedReader input, PrintWriter output){
        this.input = input;
        this.server = new VirtualSocketServer(output);
        this.username = null;
        this.idGame = null;
    }

    private void run() throws RemoteException {
      /*
      new Thread(() -> {
            try {
                runVirtualServer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).start();

        runCli();
        */
    }

    public static void main(String[] args) throws IOException {
        //TODO Verificare valori opportuni di host/port per il corretto funzionamento finale
        String host = "127.0.0.1";
        int port = Integer.parseInt("1234");

        Socket serverSocket = new Socket(host, port);

        InputStreamReader socketRx = new InputStreamReader(serverSocket.getInputStream());
        OutputStreamWriter socketTx = new OutputStreamWriter(serverSocket.getOutputStream());

        new TCPClient(new BufferedReader(socketRx), new PrintWriter(socketTx)).run();
    }


    @Override
    public void setUsername(String n) throws RemoteException {

    }

    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {

    }

    @Override
    public void setGameID(int i) throws RemoteException {

    }

    @Override
    public int getGameID() throws RemoteException {
        return 0;
    }

    @Override
    public boolean createGame(int i) throws RemoteException {
        return false;
    }

    @Override
    public List<String> showGames() throws RemoteException, NoGamesException {
        return null;
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {

    }

    @Override
    public boolean ready() throws RemoteException {
        return false;
    }

    @Override
    public List<String> showHand() throws RemoteException {
        return null;
    }

    @Override
    public void drawGold() throws RemoteException {
        server.drawGold();
    }

    @Override
    public boolean isReady() throws RemoteException {
        return false;
    }

    @Override
    public void startGame() throws RemoteException {

    }

}
