package it.polimi.ingsw.gc31.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.rmi.VirtualView;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PlayerController extends UnicastRemoteObject implements VirtualPlayerController {
    Gson gson;
    final Player player;
    final VirtualView client;

    public PlayerController(Player player, VirtualView client) throws RemoteException{
        this.player = player;
        this.client = client;

        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();
    }


    @Override
    public void getHand() throws RemoteException {
        List<PlayableCard> hand = player.getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card : hand) {
            res.add(gson.toJson(card, PlayableCard.class));
        }

        client.showHand(res);
    }

    @Override
    public void drawGold() throws RemoteException {
        player.drawGold();
    }
}
