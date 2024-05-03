package it.polimi.ingsw.gc31.client_server.listeners;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

/**
 * This class represents an observable object that can be monitored for changes.
 * It maintains a list of listeners interested in its state and notifies them
 * upon any change.
 *
 * @param <T> The type of data this observable object deals with.
 * @author christian salvini
 */
public class Observable<T> {
    /**
     * The list of listeners registered with this Observable object.
     */
    private List<Listener<T>> listeners = new ArrayList<>();

    /**
     * Adds a listener to this Observable object.
     *
     * @param listener The listener to be added.
     */
    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }

    /**
     * Remove a listener from this Observable object.
     *
     * @param listener The listener to be removed.
     */
    public void removeListener(Listener<T> listener) {
        listeners.remove(listener);
    }

    /**
     * Notifies all registered listeners with the provided data.
     *
     * @param data The data to be passed to the listeners.
     */
    protected void notifyListeners(T data) {
        for (Listener<T> listener : listeners) {
            // TODO non sono sicuro sia questo il posto per gestire questa eccezione il
            // problema è che se un update non va a buon fine il client non riceve un
            // aggiornamento fino alla prossima modifica
            // quindi nel catch si potrebbe richiedere al client un altro tipo di
            // aggiornamento? potrebbe essere un modo per capire che un client non è più
            // collegato?
            try {
                listener.update(data);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
