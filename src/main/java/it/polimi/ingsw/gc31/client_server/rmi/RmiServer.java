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

    public RmiServer(String ipaddress) throws RemoteException {
        System.setProperty("java.rmi.server.hostname", ipaddress);
        RMIserverWrite("Server IP " + ipaddress);

        this.controller = Controller.getController();
        this.callsList = new LinkedBlockingQueue<>();
        int port = 1100;
        LocateRegistry.createRegistry(port).rebind("VirtualServer", UnicastRemoteObject.exportObject(this, port));
        RMIserverWrite("Server created");
        RMIserverWrite("Server in ascolto sulla porta " + port);

        executor();
    }

    @Override
    public boolean connect(VirtualClient client, String username)
            throws RemoteException {
        if (controller.connect(client, username)) {
            return true;
        } else {
            return false;
        }

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
