package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.client_server.rmi.RmiServer;
import it.polimi.ingsw.gc31.controller.Controller;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;

/*import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;*/

public class Server /* extends Application */ {
    /*
     * @Override
     * public void start(Stage stage) throws IOException {
     * FXMLLoader fxmlLoader = new
     * FXMLLoader(HelloApplication.class.getResource("hello-view.fxml"));
     * Scene scene = new Scene(fxmlLoader.load(), 320, 240);
     * stage.setTitle("Hello!");
     * stage.setScene(scene);
     * stage.show();
     * }
     */
    public static void main(String[] args) throws RemoteException {
        new RmiServer();
    }
}