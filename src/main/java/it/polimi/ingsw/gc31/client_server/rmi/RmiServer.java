package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.controller.Controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiServer implements VirtualServer {
    private IController controller;
    private VirtualClient client;

    public VirtualClient getVirtualClient() {
        return client;
    }

    private final Queue<ServerQueueObject> callsList;

    private void serverWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_RESET + text);
    }

    public RmiServer() throws RemoteException {
        this.controller = Controller.getController();
        this.callsList = new LinkedBlockingQueue<>();
        LocateRegistry.createRegistry(1100).rebind("VirtualServer", UnicastRemoteObject.exportObject(this, 0));
        serverWrite("Server created");
        executor();
    }

    @Override
    public void connect(VirtualClient client, String username)
            throws RemoteException {
        serverWrite("New client connected: " + username);
        controller.connect(client, username);

    }

    @Override
    public void setVirtualClient(VirtualClient client) {
        this.client = client;
    }

    @Override
    public void sendCommand(ServerQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    // QUEUE
    private void addQueueObj(ServerQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
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
    }

    @Override
    public void RMIserverWrite(String text) throws RemoteException {
        serverWrite(text);
    }

}
