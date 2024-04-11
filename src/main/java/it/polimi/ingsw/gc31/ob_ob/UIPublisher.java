package it.polimi.ingsw.gc31.ob_ob;

import java.util.ArrayList;
import java.util.List;

public class UIPublisher {
    private List<UIListener> listeners = new ArrayList<>();

    public void addListener(UIListener listener) {
        listeners.add(listener);
    }

    public void removeListener(UIListener listener) {
        listeners.remove(listener);
    }

    public void notifyListeners(String input) {
        for (UIListener lis : listeners) {
            lis.inputUpdate(input);
        }
    }
}
