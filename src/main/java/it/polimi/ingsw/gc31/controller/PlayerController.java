package it.polimi.ingsw.gc31.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IPlayerController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.exceptions.EmptyDeckException;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.player.Player;
import it.polimi.ingsw.gc31.utility.gsonUtility.PlayableCardAdapter;

import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.List;

public class PlayerController extends UnicastRemoteObject implements IPlayerController {
    private Gson gson;
    private final Player player;
    private final VirtualClient client;

    public PlayerController(Player player, VirtualClient client) throws RemoteException {
        this.player = player;
        this.client = client;

        gson = new GsonBuilder()
                .registerTypeAdapter(PlayableCard.class, new PlayableCardAdapter())
                .create();
    }

    private void playerControllerWrite(String text) {
        System.out.println(DefaultValues.ANSI_CYAN
                + DefaultValues.playerControllerTag(player.getName()) + DefaultValues.ANSI_RESET + text);
    }

    @Override
    public void drawGold() throws RemoteException {
        player.drawGold();

        List<PlayableCard> hand = player.getHand();
        List<String> res = new ArrayList<>();
        for (PlayableCard card : hand) {
            res.add(gson.toJson(card, PlayableCard.class));
        }
        try {
            client.showHand(res);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }
    }
}
