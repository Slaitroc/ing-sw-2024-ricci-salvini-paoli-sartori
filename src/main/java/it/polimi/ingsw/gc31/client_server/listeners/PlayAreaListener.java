package it.polimi.ingsw.gc31.client_server.listeners;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.util.Pair;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;
public class PlayAreaListener implements Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> {
    private VirtualClient client;
    public PlayAreaListener(VirtualClient client) {
        this.client = client;
    }
    @Override
    public void update(Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>> data) throws RemoteException {
        client.show_playArea(
                data.getKey(),
                gsonTranslater.toJson(data.getValue().getKey(), new TypeToken<Map<Point, PlayableCard>>(){}.getType()),
                gsonTranslater.toJson(data.getValue().getValue(), new TypeToken<Map<Resources, Integer>>(){}.getType())
        );
    }
}
