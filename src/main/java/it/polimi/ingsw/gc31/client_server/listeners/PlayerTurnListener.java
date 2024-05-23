package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayerTurnObj;
import javafx.util.Pair;

import java.rmi.RemoteException;

public class PlayerTurnListener implements Listener<Pair<String, String>>{
    private final VirtualClient client;
    public PlayerTurnListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Pair<String, String> data) throws RemoteException {
        client.sendCommand(new ShowPlayerTurnObj(data.getKey(), data.getValue()));
    }
}
