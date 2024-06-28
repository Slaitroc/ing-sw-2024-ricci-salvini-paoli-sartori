package it.polimi.ingsw.gc31.client_server.queue.clientQueue;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * ShowPlayAreaObj is a class that extends ClientQueueObject
 * It is sent to the client to notify the play area.
 *
 * @see it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject
 */
public class ShowPlayAreaObj extends ClientQueueObject {

    /**
     * Username of the player who the play area belongs.
     */
    private final String username;

    /**
     * Play area of the player.
     */
    private final String playArea;

    /**
     * Achieved resources of the player.
     */
    private final String achievedResources;

    /**
     * Notify the play area.
     * @param username username of the player who the play area belongs.
     * @param playArea play area of the player.
     * @param achievedResources achieved resources of the player.
     */
    public ShowPlayAreaObj(String username, String playArea, String achievedResources) {
        this.username = username;
        this.playArea = playArea;
        this.achievedResources = achievedResources;
    }

    /**
     * Executes the method call of the given UI object.
     * Call the corresponding method on the UI to show that the game the client
     * wants to join is full.
     *
     * @param ui the UI object to execute the method call on
     */
    @Override
    public void execute(UI ui) {
        ui.show_playArea(
                username,
                gsonTranslater.fromJson(playArea, new TypeToken<LinkedHashMap<Point, PlayableCard>>(){}.getType()),
                gsonTranslater.fromJson(achievedResources, new TypeToken<Map<Resources, Integer>>(){}.getType())
        );
    }
}
