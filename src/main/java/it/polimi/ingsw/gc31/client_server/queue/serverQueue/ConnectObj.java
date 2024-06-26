package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.utility.DV;

//TODO javaDoc
public class ConnectObj extends ServerQueueObject {

    private final String username;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    private final int token;

    /**
     * Token getter
     *
     * @return the Token number
     */
    public int getToken() {
        return token;
    }

    /**
     * @param username Username of player that wants to reconnect
     * @param token int number of the token generated at firs connection
     */
    public ConnectObj(String username, int token) {
        this.username = username;
        this.token = token;
    }

    /**
     * @param username Username of player that wants to connect
     */
    public ConnectObj(String username) {
        this.username = username;
        this.token = DV.defaultToken;
    }

    @Override
    public void execute(GameController gameController) {
    }

    @Override
    public void execute(Controller controller) {

        try {
            if (controller.connect(controller.getRightConnection(token), username, token)) {
                ServerLog.tcpWrite("New user connected: " + username);
            } else {
                ServerLog.tcpWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(RmiServer server) {
        try {
            if (server.connect(server.getRightConnection(token), username, token)) {
                ServerLog.rmiWrite("New user connected: " + username);
            } else {
                ServerLog.rmiWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }
}
