package it.polimi.ingsw.gc31.client_server.listeners;

import it.polimi.ingsw.gc31.model.gameModel.GameModel;

import java.util.HashMap;
import java.util.Map;

/**
 * This class handles the listeners for the game.
 * Listeners are used to retrieve updates from the game model and send them to clients.
 * The listeners are organized based on different listener types.
 * Use the methods provided to enable/disable listeners, add/remove listeners, and notify listeners.
 */
public class GameListenerHandler{
    /**
     * Map that stores the different types of listeners used in the GameListenerHandler class.
     * The map is organized based on the ListenerType enum as the key and the corresponding Listener object as the value.
     */
    private final Map<ListenerType, Listener> listeners = new HashMap<>();

    /**
     * The username of the player whose changes the gameHandler notifies
     */
    private final String username;

    /**
     * Lock object that is used to synchronize with the clientList map to avoid concurrency problems
     */
    private final Object lock;

    /**
     * This class represents a boolean variable that indicates whether a listener is enabled or disabled.
     * A listener is enabled when it is able to send updates, and disabled when it is not.
     * The default value is true, which means the listener is initially enabled.
     */
    private Boolean enabled = true;

    /**
     * This class handles the listeners for the game.
     * Listeners are used to retrieve updates from the game model and send them to clients.
     * The listeners are organized based on different listener types.
     * Use the methods provided to enable/disable listeners, add/remove listeners, and notify listeners.
     */
    public GameListenerHandler(String username, Object lock) {
        this.username = username;
        this.lock = lock;
    }

    /**
     * Sets the enabled status to true.
     */
    public void setEnabled() {
        enabled = true;
    }

    /**
     * Disables the listener so that it does not receive updates.
     */
    public void setDisabled() {
        enabled = false;
    }

    /**
     * Notifies all the listeners with the given GameModel object only if enabled is set true.
     *
     * @param model The GameModel object containing the updates to be sent to the listeners.
     */
    public void notifyAllListeners(GameModel model) {
        if (enabled) {
        synchronized (lock) {
            listeners.values().forEach(listener -> listener.update(model, username));
        }
        }
    }

    /**
     * Adds a listener of the specified type to the listeners map.
     *
     * @param type the type of the listener to add
     * @param listener the listener to add
     */
    public void addListener(ListenerType type, Listener listener){
        listeners.put(type, listener);
    }

    /**
     * Removes a listener of the specified type from the listeners map.
     *
     * @param type the type of the listener to remove
     */
    public void removeListener(ListenerType type){
        listeners.remove(type);
    }

    /**
     * Notifies the listener of the specified type with the given GameModel object only if enabled is set true.
     *
     * @param type  The type of listener to notify.
     * @param model The GameModel object containing the updates to be sent to the listener.
     */
    public void notifyListener(ListenerType type, GameModel model){
        if (enabled) {
            synchronized (lock) {
                listeners.get(type).update(model, username);
            }
        }
    }
}
