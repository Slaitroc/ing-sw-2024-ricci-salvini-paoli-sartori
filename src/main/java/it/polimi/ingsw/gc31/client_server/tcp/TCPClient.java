package it.polimi.ingsw.gc31.client_server.tcp;

import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;

public class TCPClient implements VirtualClient {

    @Override
    public void setPlayerController(IPlayerController playerController) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'setPlayerController'");
    }

    @Override
    public void showHand(List<String> jsonHand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showHand'");
    }

    @Override
    public void showGameList(List<String> gameList) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'showGameList'");
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

}
