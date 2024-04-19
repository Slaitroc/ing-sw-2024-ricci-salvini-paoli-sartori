package it.polimi.ingsw.gc31;

import java.io.IOException;
import java.net.ServerSocket;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.client_server.tcp.TCPServer;
import it.polimi.ingsw.gc31.controller.Controller;

public class Server {
    private static IController virtualController = Controller.getController();
    public static void main(String[] args) throws IOException {
        try {
            new RmiServer(virtualController);

            // TODO spostare prima riga dentro SocketServer
            ServerSocket listenSocket = new ServerSocket(Integer.parseInt("1200"));
            new TCPServer(listenSocket, virtualController).runServer();
        } catch (RemoteException e) {
            e.printStackTrace();
        }
    }

}
