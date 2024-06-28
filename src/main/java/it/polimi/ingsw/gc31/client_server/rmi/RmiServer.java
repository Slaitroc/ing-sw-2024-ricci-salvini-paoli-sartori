package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.log.ServerLog;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.utility.DV;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.rmi.server.UnicastRemoteObject;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * The RmiServer class represents a remote server that interacts with a virtual client
 * using RMI (Remote Method Invocation) protocol.
 * It implements the {@link VirtualServer} interface to allow the rmi client to call methods on the server via the RMI communication protocol.
 */
public class RmiServer implements VirtualServer {

    /**
     * Queue where the {@link ServerQueueObject} sent by the client are added.
     */
    private final Queue<ServerQueueObject> callsList;

    /**
     * Creates a new RmiServer instance which allows multiple {@link RmiClient} to connect and interact with the controller.
     * Forces hostname to be set to the ipaddress.
     * Registers the {@link RmiServer} instance in the {@link LocateRegistry} as a {@link UnicastRemoteObject}
     * on the port indicated by {@link DV#RMI_PORT}.
     * Starts the {@link #executor()}.
     *
     * @param ipaddress the IP address of the server
     * @throws RemoteException if a remote exception occurs
     */
    public RmiServer(String ipaddress) throws RemoteException {
        System.setProperty("java.rmi.server.hostname", ipaddress);
        ServerLog.rmiWrite("Server IP " + ipaddress);

        this.callsList = new LinkedBlockingQueue<>();
        LocateRegistry.createRegistry(DV.RMI_PORT).rebind("VirtualServer",
                UnicastRemoteObject.exportObject(this, DV.RMI_PORT));
        ServerLog.rmiWrite("Server created");
        ServerLog.rmiWrite("the server is listening on the port " + DV.RMI_PORT);

        executor();
    }

    /**
     * Method of the {@link VirtualServer} that writes the given text to the RMI server log.
     *
     * @param text the text to be written to the server log
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public void RMIserverWrite(String text) throws RemoteException {
        ServerLog.rmiWrite(text);
    }

    /**
     * Method of the {@link VirtualServer} that is called by the client to send an object of type {@link ServerQueueObject}.
     *
     * @param obj the ClientQueueObject representing the command to be executed
     * @throws RemoteException if there is an error in the remote communication
     */
    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    /**
     * Method of the {@link VirtualServer} that retrieves the IP address of the client connected.
     * If not found return {@code null}.
     *
     * @return the IP address of the client
     * @throws RemoteException if a remote exception occurs
     */
    @Override
    public String getClientIP() throws RemoteException {
        String ip = null;
        try {
            ip = RemoteServer.getClientHost();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        return ip;
    }

    /**
     * Method of the {@link VirtualServer} that calls the {@link Controller} to ask it to generate a token.
     * It is used for the first connection of a {@link RmiClient}.
     *
     * @param newConnection the virtual client for which the token is generated.
     * @return the generated token value.
     */
    @Override
    public int generateToken(VirtualClient newConnection) {
        return Controller.getController().generateToken(newConnection);
    }

    /**
     * Method of the {@link VirtualServer} that calls the method {@link Controller#connect(VirtualClient, String, Integer, Integer)}.
     * It is used by a {@link RmiClient} to connect with the controller.
     *
     * @param client     the virtual client who wants controller connect to.
     * @param username   the username chose by the client
     * @param tempoToken the temporary token.
     * @param token      the token for the client
     * @return true if the connection is successful, false otherwise
     * @throws RemoteException if there is an error in the remote communication
     */
    @Override
    public boolean connect(VirtualClient client, String username, Integer tempoToken, Integer token)
            throws RemoteException {
        if (Controller.getController().connect(client, username, tempoToken, token)) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * Retrieves the VirtualClient object representing the right connection based on the provided token.
     *
     * @param token the token value used to identify the connection
     * @return the VirtualClient object representing the right connection
     */
    public VirtualClient getRightConnection(int token) {
        return Controller.getController().getRightConnection(token);
    }

    // QUEUE
    /**
     * Add a ClientQueueObject to the callsList and notify any waiting threads.
     *
     * @param obj the ClientQueueObject to be added to the callsList
     */
    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    /**
     * Thread that takes an object from the callList and executes it.
     */
    private void executor() {
        new Thread(() -> {
            while (true) {
                ServerQueueObject action;
                synchronized (callsList) {
                    while (callsList.isEmpty()) {
                        try {
                            callsList.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    action = callsList.poll();
                }
                if (action != null) {
                    action.execute(this);

                }
            }
        }).start();
    }

    // TODO
//    private VirtualClient client;
//    @Override
//    public void setVirtualClient(VirtualClient client) {
//        this.client = client;
//    }

//    public VirtualClient getVirtualClient() {
//        return client;
//    }

}
