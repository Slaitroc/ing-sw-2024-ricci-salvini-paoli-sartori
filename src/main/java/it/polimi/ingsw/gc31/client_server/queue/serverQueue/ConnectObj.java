package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.utility.DV;

public class ConnectObj extends ServerQueueObject {

    private final String username;
    private final int token;

    public ConnectObj(String username, int token) {
        this.username = username;
        this.token = token;
    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(Controller controller) {

        try {
            if (controller.connect(controller.getCorrectConnection(token), username)) {
                TCPserverWrite("New user connected: " + username);
            } else {
                TCPserverWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(RmiServer server) {
        try {

            if (server.connect(server.getVirtualClient(), username)) {
                server.RMIserverWrite("New user connected: " + username);
            } else {
                server.RMIserverWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    private void TCPserverWrite(String text) {
        System.out.println(DV.ANSI_YELLOW + DV.TCP_SERVER_TAG + DV.ANSI_RESET + text);
    }

}
