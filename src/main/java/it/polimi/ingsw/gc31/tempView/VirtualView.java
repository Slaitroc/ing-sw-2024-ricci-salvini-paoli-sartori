package it.polimi.ingsw.gc31.tempView;

import java.util.List;

public interface VirtualView{
    public void showHand(List<String> hand);

    public void sendListMessages(List<String> msg);

    public void sendMessage(String msg);
}
