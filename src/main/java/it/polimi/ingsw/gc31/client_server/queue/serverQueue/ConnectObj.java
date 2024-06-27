package it.polimi.ingsw.gc31.client_server.queue.serverQueue;

import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.utility.DV;

/**
 * This class represent the action of connecting to the server
 */
public class ConnectObj extends ServerQueueObject {
    /**
     * Is the username of the client connecting to the server
     */
    private final String username;

    /**
     * @return the username
     */
    public String getUsername() {
        return username;
    }

    /**
     * Is the token value of the client
     */
    private final int token;

    /**
     * Is the tempToken of the client
     */
    private int tempToken;

    /**
     * This is the constructor of the class.
     *
     * @param username  is the username of the client connecting to the server.
     * @param tempToken is the value to be set as the tempToken.
     * @param token     is the value to be set as the token.
     */
    public ConnectObj(String username, int tempToken, int token) {
        this.username = username;
        this.token = token;
        this.tempToken = tempToken;
    }

    /**
     * This is the constructor of the class, without the value of the token which is
     * assigned to a defaultValue. Used for the first connection.
     *
     * @param username  is the username of the client connecting to the server.
     * @param tempToken is the value to be set as the tempToken.
     */
    public ConnectObj(String username, int tempToken) {
        this.username = username;
        this.tempToken = tempToken;
        this.token = DV.defaultToken;
    }

    /**
     * This method is executed if the object should be executed by the {@link GameController} but should be
     * executed by the {@link Controller}.
     *
     * @param gameController is the reference to the {@link GameController} that should execute the object.
     */
    @Override
    public void execute(GameController gameController) {
    }

    /**
     * This method is executed by the {@link Controller} when it is polled from the queue.
     * Invokes the {@link Controller#connect(VirtualClient, String, Integer, Integer)} method.
     *
     * @param controller is the reference to the {@link Controller} associated with the client.
     */
    @Override
    public void execute(Controller controller) {

        try {
            if (controller.connect(controller.getRightConnection(token), username, tempToken, token)) {
                ServerLog.tcpWrite("New user connected: " + username);
            } else {
                ServerLog.tcpWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method is executed by the {@link RmiServer} when it is polled from the queue.
     * Invokes the {@link RmiServer#connect(VirtualClient, String, Integer, Integer)} method.
     *
     * @param server is the reference to the {@link RmiServer} associated with the client.
     */
    @Override
    public void execute(RmiServer server) {
        try {
            if (server.connect(server.getRightConnection(token), username, tempToken, token)) {
                ServerLog.rmiWrite("New user connected: " + username);
            } else {
                ServerLog.rmiWrite("New connection refused");
            }
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method return the value of the token.
     *
     * @return the value of the token.
     */
    public int getToken(){
        return this.token;
    }

    /**
     * This method returns the value of the tempToken.
     *
     * @return the value of the tempToken.
     */
    public int getTempToken(){
        return this.tempToken;
    }

    /**
     * This method is a setter of the tempToken.
     *
     * @param tempToken is the value that needs to be set as the tempToken.
     */
    public void setTempToken(int tempToken){
        this.tempToken = tempToken;
    }
}
