package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.view.interfaces.ShowUpdate;

import java.io.PrintWriter;
import java.rmi.RemoteException;
import java.util.List;

public class VirtualSocketServer implements VirtualClient {
    private final PrintWriter output;
    @SuppressWarnings("unused")
    private final SocketClientHandler server;

    public VirtualSocketServer(PrintWriter output, SocketClientHandler server) {
        this.output = output;
        this.server = server;
    }

    @Override
    public void setGameID(int gameID) throws RemoteException {

    }

    @Override
    public void showListGame(List<String> listGame) throws RemoteException {
        listGame.forEach(x -> output.println(x));
        output.flush();
    }

    @Override
    public boolean isReady() throws RemoteException {
        throw new UnsupportedOperationException("Unimplemented method 'isReady'");
    }

    @Override
    public void startGame() throws RemoteException {
        throw new UnsupportedOperationException("Unimplemented method 'startGame'");
    }

    @Override
    public ShowUpdate getUI() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getUI'");
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_goldDeck'");
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_resourceDeck'");
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveDeck'");
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_starterCard'");
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveCard'");
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_playArea'");
    }

    @Override
    public void show_handPlayer(String username, List<String> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_handPlayer'");
    }

    @Override
    public void show_scorePlayer(String key, Integer value) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_scorePlayer'");
    }
}