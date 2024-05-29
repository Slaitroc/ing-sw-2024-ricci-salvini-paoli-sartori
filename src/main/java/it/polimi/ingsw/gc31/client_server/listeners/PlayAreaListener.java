package it.polimi.ingsw.gc31.client_server.listeners;

import com.google.gson.reflect.TypeToken;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ShowPlayAreaObj;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import javafx.util.Pair;

import java.awt.*;
import java.rmi.RemoteException;
import java.util.Map;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * The PlayAreaListener class implements the Listener interface to observe
 * changes in the playArea.
 * It receives data in the form of a pair containing:
 * <ul>
 * <li>A String containing the username of the player's playArea</li>
 * <li>A pair of maps:
 * <ul>
 * <li>The first map (Map<Point, PlayableCard>) is the PlacedCards.</li>
 * <li>The second map (Map<Resources, Integer>) is the achievedResources.</li>
 * </ul>
 * </li>
 * </ul>
 * This listener is designed to update a VirtualClient with the play area
 * updated.
 *
 * @author christian salvini
 */
public class PlayAreaListener
                implements Listener<Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>>> {
        private VirtualClient client;

        /**
         * Constructs a PlayAreaListener with the specified VirtualClient.
         *
         * @param client The VirtualClient to update with play area information.
         */
        public PlayAreaListener(VirtualClient client) {
                this.client = client;
        }

        /**
         * Receives an update containing play area data and triggers the display on the
         * associated VirtualClient.
         *
         * @param data A pair containing the username, the PlaceCards and the
         *             achievedResources
         * @throws RemoteException If there is a communication error,
         */
        @Override
        public void update(Pair<String, Pair<Map<Point, PlayableCard>, Map<Resources, Integer>>> data)
                        throws RemoteException {
                client.sendCommand(new ShowPlayAreaObj(
                        data.getKey(),
                        gsonTranslater.toJson(data.getValue().getKey(),
                                new TypeToken<Map<Point, PlayableCard>>() {
                                }.getType()),
                        gsonTranslater.toJson(data.getValue().getValue(),
                                new TypeToken<Map<Resources, Integer>>() {
                                }.getType()))
                );
        }
}
