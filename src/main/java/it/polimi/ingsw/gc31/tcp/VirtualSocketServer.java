package it.polimi.ingsw.gc31.tcp;

import it.polimi.ingsw.gc31.rmi.VirtualView;

public class VirtualSocketServer {
    public void connect(VirtualView client, String username){}
    public void getGameList(String username){}
    public Integer createGame(String username, int maxNumberPlayers){ return 0; }
    public void joinGame(String username, Integer idGame){}
    public void getHand(String username, Integer idGame){}
    public void drawGold(String username, Integer idGame){}

}
