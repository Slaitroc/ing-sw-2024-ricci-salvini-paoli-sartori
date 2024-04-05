package it.polimi.ingsw.gc31.rmi;

import it.polimi.ingsw.gc31.controller.Controller;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RmiServer implements VirtualServer {
    private final Map<Integer, Controller> gameList;
    private final List<String> usernameList;
    private Map<String, VirtualView> tempClients;
    private Integer progressiveIdGame;

    public RmiServer() {
        gameList = new HashMap<>();
        usernameList = new ArrayList<>();
        tempClients = new HashMap<>();
        progressiveIdGame = 0;
    }

    public static void main(String[] args) throws RemoteException {
        String name = "VirtualServer";
        VirtualServer engine = new RmiServer();
        VirtualServer stub = (VirtualServer) UnicastRemoteObject.exportObject(engine, 0);
        Registry registry = LocateRegistry.createRegistry(1234);
        registry.rebind(name, stub);

        System.out.println("[SERVER] Adder bound");
    }

    @Override
    public void connect(VirtualView client, String username) throws RemoteException {
        System.out.println("[SERVER] new client connected");

        // TODO aggiungere eccezione per quando l'username è gia presente, il client non viene aggiunto
        usernameList.add(username);
        // appena collegato un client viene messo nella tempClients fino a che non viene associato ad un game
        tempClients.put(username, client);
    }

    @Override
    public void getGameList(String username) throws RemoteException {
        if (gameList.isEmpty()) {
            tempClients.get(username).reportError("Non sono presenti game creati. Digita 'crea game' per creare un nuovo game");
        } else {
            List<String> res = new ArrayList<>();
            for (Integer idGame : gameList.keySet()) {
                res.add(
                        idGame.toString() + " "
                                + gameList.get(idGame).getMaxNumberPlayers() + " / "
                                + gameList.get(idGame).getCurrentNumberPlayers()
                );
            }
            tempClients.get(username).showGameList(res);
        }
    }

    @Override
    public Integer createGame(String username, int maxNumberPlayers) throws RemoteException {
        gameList.put(progressiveIdGame, new Controller(username, tempClients.get(username), maxNumberPlayers));

        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);

        System.out.println("[SERVER] Creato game con id: " + progressiveIdGame);

        //viene ritornato l'id del game associato al client, poi viene incrementato
        return progressiveIdGame++;
    }

    @Override
    public void joinGame(String username, Integer idGame) throws RemoteException {
        // viene aggiunto il client al game corrispondente
        gameList.get(idGame).joinGame(username, tempClients.get(username));

        // viene rimosso il client da quelli temporanei
        tempClients.remove(username);
    }

    @Override
    public void getHand(String username, Integer idGame) throws RemoteException {
        gameList.get(idGame).getHand(username);
    }

    @Override
    public void drawGold(String username, Integer idGame) throws RemoteException {
        gameList.get(idGame).drawGold(username);
    }
}
