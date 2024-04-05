package it.polimi.ingsw.gc31;

import it.polimi.ingsw.gc31.rmi.RmiServer;
import it.polimi.ingsw.gc31.rmi.VirtualServer;
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
        String name = "VirtualServer";
        VirtualServer engine = new RmiServer();
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);

        System.out.println("[SERVER] Adder bound");

    }
}