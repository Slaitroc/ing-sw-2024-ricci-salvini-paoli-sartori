package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.controller.Controller;

import java.io.IOException;

import java.rmi.RemoteException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/*
ricevo
deserializzo
getto e check il recipient
a seconda chiamo gamecontroller o controller sendobject

eseguito da loro
 */
/**
 * This class receives the inputs from the virtual socket server, executes the
 * methods requested by the client
 * sends the data, that need to be updated and showed to other clients, to the
 * server
 */
public class SocketClientHandler implements VirtualClient {
    private IController controller;
    private IGameController gameController;
    private String username;

    private Integer idGame;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;

    private boolean ready = false;

    /**
     * This method is the constructor of the client handler
     *
     * @param input  is the reference to the input stream of the socket
     *               connection
     * @param output is the reference to the output stream of the socket
     *               connection
     */
    public SocketClientHandler(ObjectInputStream input, ObjectOutputStream output) {
        this.controller = Controller.getController();
        Controller.getController().setNewConnection(this);
        this.input = input;
        this.output = output;
        tcpClient_reader();

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
    public void sendCommand(ClientQueueObject obj) throws RemoteException {
        try {
            output.writeObject(obj);
            output.reset();
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method reads the object from the client and sends it to the
     * right controller
     * based on the recipient of the object
     */
    private void tcpClient_reader() {
        new Thread(() -> {
            ServerQueueObject obj = null;
            while (true) {
                try {
                    obj = (ServerQueueObject) input.readObject();
                } catch (ClassNotFoundException | IOException e) {
                    e.printStackTrace();
                }
                if (obj != null) {
                    if (obj.getRecipient().equals(DefaultValues.RECIPIENT_CONTROLLER)) {
                        try {
                            controller.sendCommand(obj);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    } else if (obj.getRecipient().equals(DefaultValues.RECIPIENT_GAME_CONTROLLER)) {
                        try {
                            gameController.sendCommand(obj);
                        } catch (RemoteException e) {
                            e.printStackTrace();
                        }
                    }

                }
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
    public boolean isReady() {
        return ready;
    }

    /**
     * This method set the controller attribute to the one taken as a parameter.
     *
     * @param controller is the new reference to the controller that needs to be set to the attribute
     */
    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * This method set the gameController attribute to the one taken as a parameter.
     *
     * @param gameController is the new reference to the gameController that needs to be set to the attribute
     */
    @Override
    public void setGameController(IGameController gameController) {
        this.gameController = gameController;
    }

}