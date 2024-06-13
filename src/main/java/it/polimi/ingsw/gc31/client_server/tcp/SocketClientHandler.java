package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ConnectObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.utility.DV;

import java.io.*;

import java.rmi.RemoteException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

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
    private Integer idGame; // viene settata ma ancora non utilizzata
    // private String username;
    private boolean ready = false;

    private final ObjectInputStream input;
    private final ObjectOutputStream output;

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
        this.input = input;
        this.output = output;
        tcpClient_reader();
        Controller.getController().setNewConnection(this);

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
     * based on the recipient of the object.
     * The Object corresponding to the heartBeat are treated differently because it
     * needs to be evaluated instantly
     */
    private void tcpClient_reader() {
        new Thread(() -> {
            ServerQueueObject obj = null;

            try {
                while ((obj = (ServerQueueObject) input.readObject()) != null) {
                    if (obj.getRecipient().equals(DV.RECIPIENT_CONTROLLER)) {
                        try {
                            try {
                                ConnectObj connectObj = (ConnectObj) obj;
                                if (connectObj.getToken() == DV.defaultToken) {
                                    if (controller.connect(this, connectObj.getUsername())) {
                                        System.out.println("New user connected: " + connectObj.getUsername());
                                        // TCPserverWrite("New user connected: " + connectObj.getUsername());
                                    } else {
                                        System.out.println("New connection refused");
                                        // TCPserverWrite("New connection refused");
                                    }
                                    continue;
                                }
                            } catch (ClassCastException e) {

                            }
                            controller.sendCommand(obj);
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
                        controller.updateHeartBeat(this);
                    }
                }

                // se oggetto letto è null la connessione è caduta
                // TODO aggiungere wait per aspettare x minuti che il client si riconnetta,
                // se dopo x minuti il client non si è riconnesso chiudo la connessione.
                // Altrimenti
                // devo riconnettere il client alla partita a cui stava giocando
            } catch (IOException | ClassNotFoundException e) {
                // e.printStackTrace();
                System.out.println("A TCP client disconnected");
            }

            /*
             * while (true) {
             * try {
             * obj = (ServerQueueObject) input.readObject();
             * } catch (ClassNotFoundException | IOException e) {
             * e.printStackTrace();
             * }
             * if (obj != null) {
             * if (obj.getRecipient().equals(DV.RECIPIENT_CONTROLLER)) {
             * try {
             * controller.sendCommand(obj);
             * } catch (RemoteException e) {
             * e.printStackTrace();
             * }
             * } else if (obj.getRecipient().equals(DV.RECIPIENT_GAME_CONTROLLER)) {
             * try {
             * gameController.sendCommand(obj);
             * } catch (RemoteException e) {
             * e.printStackTrace();
             * }
             * }
             * }
             * }
             */
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
     * @param controller is the new reference to the controller that needs to be set
     *                   to the attribute
     */
    @Override
    public void setController(IController controller) {
        this.controller = controller;
    }

    /**
     * This method set the gameController attribute to the one taken as a parameter.
     *
     * @param gameController is the new reference to the gameController that needs
     *                       to be set to the attribute
     */
    @Override
    public void setGameController(IGameController gameController) {
        this.gameController = gameController;
    }
}