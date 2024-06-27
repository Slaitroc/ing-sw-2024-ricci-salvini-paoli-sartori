package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.IGameController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.SaveToken;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ConnectObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.utility.DV;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.rmi.RemoteException;

/*
ricevo
deserialize
getto e check il recipient
a seconda chiamo gameController o controller sendObject
eseguito da loro
 */

/**
 * This class receives the inputs from the virtual socket server, executes the
 * methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the
 * server
 */
public class SocketClientHandler implements VirtualClient {
    /**
     * Is the gameController associated to this handler by the Controller. Manages
     * the games
     */
    private IGameController gameController;
    @SuppressWarnings("unused")
    private Integer idGame;
    private boolean ready = false;
    /**
     * Is the ObjectInputStream used to read all the objects the client sends to the
     * handler
     */
    private final ObjectInputStream input;
    /**
     * Is the ObjectOutputStream used to send all the objects to the client
     */
    private final ObjectOutputStream output;
    /**
     * A value that is used to manages the first connections of the clients to the
     * server. The client posses both the
     * tempToken and the token (in the Token object)
     */
    private int tempToken;

    /**
     * This method is the constructor of the client handler
     *
     * @param input  is the reference to the input stream of the socket
     *               connection
     * @param output is the reference to the output stream of the socket
     *               connection
     */
    public SocketClientHandler(ObjectInputStream input, ObjectOutputStream output) {
        this.input = input;
        this.output = output;
        tcpClient_reader();
        this.tempToken = Controller.getController().generateToken(this);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            try {
                input.close();
                output.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }));
    }

    /**
     * This method send to the TCPClient the ClientQueueObject that needs to be
     * executed
     */
    @Override
    public synchronized void sendCommand(ClientQueueObject obj) throws RemoteException {
        try {
            output.writeObject(obj);
            output.reset();
            output.flush();
        } catch (IOException e) {
            throw new RemoteException();
        }
    }

    /**
     * This method reads the object from the client and sends it to the
     * right controller
     * based on the recipient of the object.
     * The Object corresponding to the heartBeat are treated differently because it
     * needs to be evaluated instantly
     */
    private void tcpClient_reader() {
        new Thread(() -> {
            ServerQueueObject obj;

            try {
                while ((obj = (ServerQueueObject) input.readObject()) != null) {
                    if (obj.getRecipient().equals(DV.RECIPIENT_CONTROLLER)) {
                        try {
                            try {
                                ConnectObj connectObj = (ConnectObj) obj;
                                if (connectObj.getTempToken() == -1) {
                                    Controller.getController().connect(Controller.getController().getRightConnection(tempToken), connectObj.getUsername(), tempToken, connectObj.getToken());
                                    sendCommand(new SaveToken(tempToken, true));
                                } else {
                                    Controller.getController().connect(Controller.getController().getRightConnection(tempToken), connectObj.getUsername(), connectObj.getTempToken(), connectObj.getToken());
                                }
                                continue;
                            } catch (ClassCastException e) {

                            }
                            Controller.getController().sendCommand(obj);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else if (obj.getRecipient().equals(DV.RECIPIENT_GAME_CONTROLLER)) {
                        try {
                            gameController.sendCommand(obj);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else if (obj.getRecipient().equals(DV.RECIPIENT_HEARTBEAT)) {
                        Controller.getController().updateHeartBeat(this);
                    }
                }
                // If the object red is null the client disconnected
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
                System.out.println("A TCP client disconnected");
            }
        }).start();
    }

    /**
     * This method sets the gameID
     *
     * @param gameID is the value that needs to be set
     */
    @Override
    public void setGameID(int gameID) throws RemoteException {
        this.idGame = gameID;
    }

    /**
     * This method returns the value of the attribute ready.
     *
     * @return the value of the ready attribute
     */
    @Override
    public boolean isReady() throws RemoteException {
        return ready;
    }

    /**
     * This method set the gameController attribute to the one taken as a parameter.
     *
     * @param gameController is the new reference to the gameController that needs
     *                       to be set to the attribute
     */
    @Override
    public void setGameController(IGameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    /**
     * Method used at the start of the rmi connections but useless in the TCP
     * implementation.
     * Inherited from the VirtualClient interface.
     * In TCP implementation the VirtualClient (is server side so it...) can get the
     * controller via the singleton.
     *
     * @param controller is the controller the client needs to get
     */
    @Override
    public void setController(IController controller) throws RemoteException {

    }

    /**
     * Method used at the start of the rmi connections but useless in the TCP
     * implementation.
     * Inherited from the VirtualClient interface.
     *
     * @param token value to assign to the client specific token, used mainly for
     *              reconnection
     */
    @Override
    public void setRmiToken(int token) throws RemoteException {
    }

}