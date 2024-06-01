package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
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

public class RmiServer implements VirtualServer {
    private IController controller;
    private VirtualClient client;

    public VirtualClient getVirtualClient() {
        return client;
    }

    private final Queue<ServerQueueObject> callsList;

    public RmiServer(String ipaddress) throws RemoteException {
        System.setProperty("java.rmi.server.hostname", ipaddress);
        ServerLog.rmiWrite("Server IP " + ipaddress);

        this.controller = Controller.getController();
        this.callsList = new LinkedBlockingQueue<>();
        LocateRegistry.createRegistry(DV.RMI_PORT).rebind("VirtualServer",
                UnicastRemoteObject.exportObject(this, DV.RMI_PORT));
        ServerLog.rmiWrite("Server created");
        ServerLog.rmiWrite("Server in ascolto sulla porta " + DV.RMI_PORT);

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

    @Override
    public void RMIserverWrite(String text) throws RemoteException {
        ServerLog.rmiWrite(text);
    }

    @Override
    public String getClientIP() throws RemoteException {
        String ip = null;
        try {
            ip = RemoteServer.getClientHost().toString();
        } catch (ServerNotActiveException e) {
            e.printStackTrace();
        }
        return ip;
    }

}
