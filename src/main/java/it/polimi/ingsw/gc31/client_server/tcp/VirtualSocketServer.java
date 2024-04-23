package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.*;

import java.io.PrintWriter;
import java.net.Socket;
import java.rmi.RemoteException;
import java.util.List;

public class VirtualSocketServer implements VirtualClient {
    private final PrintWriter output;
    private final SocketClientHandler server;

    public VirtualSocketServer(PrintWriter output, SocketClientHandler server) {
        this.output = output;
        this.server = server;
    }

    @Override
    public void setGameID(int gameID) throws RemoteException {

    }

    @Override
    public void showCards(List<String> hand) throws RemoteException {
        hand.forEach(x -> output.println(x));
        output.flush();
    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        listGame.forEach(x -> output.println(x));
        output.flush();
    }

    @Override
    public void showMessage(String msg) throws RemoteException {
        output.println(msg);
        output.flush();
    }
}