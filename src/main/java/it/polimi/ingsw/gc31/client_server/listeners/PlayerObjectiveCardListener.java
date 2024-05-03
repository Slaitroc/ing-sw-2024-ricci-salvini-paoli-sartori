package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;

import java.rmi.RemoteException;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;

/**
 * A listener class that handles updates related to a player's objective card.
 * This listener is designed to update a VirtualClient with the objective card
 * chosen by the player
 *
 * @author christian salvini
 */
public class PlayerObjectiveCardListener implements Listener<ObjectiveCard> {
    private VirtualClient client;

    /**
     * Constructs a PlayerObjectiveCardListener with the specified VirtualClient
     *
     * @param client The VirtualClient to update with objective card information.
     */
    public PlayerObjectiveCardListener(VirtualClient client) {
        this.client = client;
    }

    /**
     * Receives an update containing the objective card and triggers the display on
     * the associated VirtualClient
     *
     * @param data The objective card to display.
     * @throws RemoteException If there is a communication error.
     *
     */
    @Override
    public void update(ObjectiveCard data) throws RemoteException {
        client.getUI().show_objectiveCard(gsonTranslater.toJson(data, ObjectiveCard.class));
    }
}
