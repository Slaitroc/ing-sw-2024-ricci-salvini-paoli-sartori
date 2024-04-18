package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.exceptions.*;
import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;

/**
 * This class receives the inputs from the virtual socket server, executes the methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the server
 */
public class SocketClientHandler implements VirtualClient{
    final IController controller;
    private IMainGameController mainGameController;
    private Integer idGame;
    private String username;
    private IPlayerController playerController;

    private final VirtualServer server;
    private final BufferedReader input;
    private final PrintWriter output;


    //TODO Modificare l'inizializzazione di idGame
    public SocketClientHandler(IController controller, VirtualServer server, BufferedReader input, PrintWriter output, Integer progressiveIdGame) throws RemoteException {
        this.controller = controller;
        this.server = server;
        this.input = input;
        this.output = output;
        this.idGame = progressiveIdGame;
    }

    @Override
    public void setUsername(String n) throws RemoteException {

    }

    public void setPlayerController(IPlayerController playerController){
        this.playerController = playerController;
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

    }

    @Override
    public boolean isReady() throws RemoteException {
        return false;
    }

    @Override
    public void startGame() throws RemoteException {

    }

    public void runVirtualView(){

    }

}