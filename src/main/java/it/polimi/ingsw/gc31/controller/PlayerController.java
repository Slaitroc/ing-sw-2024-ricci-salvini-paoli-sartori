/*
package it.polimi.ingsw.gc31.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.DrawGoldObj;
import it.polimi.ingsw.gc31.client_server.queue.DrawGoldOneObj;
import it.polimi.ingsw.gc31.client_server.queue.DrawGoldTwoObj;
import it.polimi.ingsw.gc31.client_server.queue.DrawResOneObj;
import it.polimi.ingsw.gc31.client_server.queue.DrawResTwoObj;
import it.polimi.ingsw.gc31.client_server.queue.QueueObject;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;
import java.util.concurrent.LinkedBlockingQueue;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PlayerController extends UnicastRemoteObject implements IPlayerController {
    private Gson gson;
    private final Player player;
    private final VirtualClient client;
    private LinkedBlockingQueue<QueueObject> callsList;

    public PlayerController(Player player, VirtualClient client) throws RemoteException {
        this.player = player;
        this.client = client;
        this.callsList = new LinkedBlockingQueue<>();

        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();
        new Thread(this::executor).start();

    }

    private void addQueueObj(QueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
        }
    }

    private void executor() {
        QueueObject action;
        while (true) {
            synchronized (callsList) {
                action = callsList.poll();
            }
            if (action != null)
                action.execute();
        }
        // TODO ciclo da terminare alla fine del gioco altrimenti diventa demoooone
        // uuuuhhhhh
    }

    private void playerControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_CYAN
                + DefaultValues.playerControllerTag(player.getUsername()) + DefaultValues.ANSI_RESET + text);
    }

    @Override
    public void drawGold() throws RemoteException {
        addQueueObj(new DrawGoldObj(player));

        List<PlayableCard> hand = player.getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card : hand) {
            res.add(gson.toJson(card, PlayableCard.class));
        }
    }

    @Override
    public void drawGoldCard1() throws RemoteException {
        addQueueObj(new DrawGoldOneObj(player));
    }

    @Override
    public void drawGoldCard2() throws RemoteException {
        addQueueObj(new DrawGoldTwoObj(player));
    }

    @Override
    public void drawResource() throws RemoteException {
        addQueueObj(new DrawResourceObj(player));
    }

    @Override
    public void drawResourceCard1() throws RemoteException {
        addQueueObj(new DrawResOneObj(player));
    }

    @Override
    public void drawResourceCard2() throws RemoteException {
        addQueueObj(new DrawResTwoObj(player));
    }

}
*/
