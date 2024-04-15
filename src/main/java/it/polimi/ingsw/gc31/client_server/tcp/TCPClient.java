package it.polimi.ingsw.gc31.client_server.tcp;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public class TCPClient implements VirtualClient {

    @Override
    public void setUsername(String n) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setUsername'");
    }

    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayerController'");
    }

    @Override
    public void sendMessage(String details) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'sendMessage'");
    }

    @Override
    public void setGameID(int i) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setGameID'");
    }

    @Override
    public int getGameID() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getGameID'");
    }

    @Override
    public boolean createGame(int i) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'createGame'");
    }

    @Override
    public List<String> showGames() throws RemoteException, NoGamesException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showGames'");
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'joinGame'");
    }

    @Override
    public boolean ready() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'ready'");
    }

    @Override
    public List<String> showHand() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showHand'");
    }

    @Override
    public void drawGold() throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'drawGold'");
    }

}
