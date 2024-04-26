package it.polimi.ingsw.gc31.client_server.listeners;

import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.List;

public class Observable<T> {
    private List<Listener<T>> listeners = new ArrayList<>();
    public void addListener(Listener<T> listener) {
        listeners.add(listener);
    }
    public void removeListener(Listener<T> listener) {
        listeners.remove(listener);
    }
    protected void notifyListeners(T data) {
        for (Listener<T> listener : listeners) {
            // TODO non sono sicuro sia questo il posto per gestire questa eccezione
            //  il problema è che se un update non va a buon fine il client non riceve un aggiornamento fino alla prossima modifica
            //  quindi nel catch si potrebbe richiedere al client un altro tipo di aggiornamento?

            try {
                listener.update(data);
            } catch (RemoteException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
