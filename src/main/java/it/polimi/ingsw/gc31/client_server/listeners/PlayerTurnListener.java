package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayerTurnObj;
import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.rmi.RemoteException;
import java.util.List;

public class PlayerTurnListener implements Listener{
    private final List<VirtualClient> clients;

    public PlayerTurnListener(List<VirtualClient> clients) {
        this.clients = clients;
    }

    @Override
    public void update(GameModel model, String username) throws RemoteException {
        for (VirtualClient client : clients) {
            client.sendCommand(new ShowPlayerTurnObj(username, model.getPlayers().get(username).infoState()));
        }
    }
}
