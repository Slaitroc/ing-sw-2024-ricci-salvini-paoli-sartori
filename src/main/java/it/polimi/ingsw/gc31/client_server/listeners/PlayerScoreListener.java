package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import javafx.util.Pair;

import java.rmi.RemoteException;

public class PlayerScoreListener implements Listener<Pair<String, Integer>>{
    private VirtualClient client;
    public PlayerScoreListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Pair<String, Integer> data) throws RemoteException {
        client.show_scorePlayer(data.getKey(), data.getValue());
    }
}
