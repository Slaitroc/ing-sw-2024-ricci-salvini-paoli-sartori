
package it.polimi.ingsw.gc31.view.gui;

import java.io.Reader;
import java.rmi.RemoteException;

import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import javafx.application.Platform;
import it.polimi.ingsw.gc31.view.UI;
import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;
import com.google.gson.reflect.TypeToken;
import java.util.List;
import java.util.Map;

public class GUI extends UI {


    GUIApplication app;

    public GUI(ClientCommands client) {
        this.client = client;
    }

    /**
     * This method allows to call a Runnable inside the GUI thread
     * 
     * @param r
     * 
     * @Slaitroc
     */
    public void runInGuiApp(Runnable r) {
        Platform.runLater(r);
    }

    @Override
    protected void uiRunUI() {
        //Application.launch(GUIApplication.class);

        app = new GUIApplication();
        client.setUI(this);
        app.setClient(client);
        app.run();
    }

    @Override
    public void show_listGame(List<String> listGame) throws RemoteException {
        //gsonTranslater.fromJson((Reader) listGame, new TypeToken<List<String>>(){}.getType());??
        for (String string : listGame) {
            System.out.println(string);
        }
    }

    @Override
    public void show_gameCreated() {
        /*try {
            app.setCurrentGameID(client.getGameID());
            app.setNumberOfPlayers(3);
            //TODO app.setNumberOfPlayers(client.getNumOfPlayers);
            app.loadScene(SceneTag.LOBBY);
        } catch (RemoteException e) {
            throw new RuntimeException(e);
        }*/
    }

    @Override
    public void updateToPlayingState() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateToPlayingState'");
    }

    @Override
    public void updateHand(String username, List<String> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'updateHand'");
    }

    // SHOW UPDATE
    @Override
    public void show_scorePlayer(String key, Integer value) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_scorePlayer'");
    }

    @Override
    public void show_goldDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_goldDeck'");
    }

    @Override
    public void show_handPlayer(String username, List<String> hand) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_handPlayer'");
    }

    @Override
    public void show_objectiveCard(String objectiveCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveCard'");
    }

    @Override
    public void show_objectiveDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_objectiveDeck'");
    }

    @Override
    public void show_playArea(String username, String playArea, String achievedResources) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_playArea'");
    }

    @Override
    public void show_resourceDeck(String firstCardDeck, String card1, String card2) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_resourceDeck'");
    }

    @Override
    public void show_starterCard(String starterCard) throws RemoteException {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'show_starterCard'");
    }



}

//TODO client.getReady()
//TODO client.getGamePlayerList or directly client.getClients
//TODO client.getNumOfPlayers
//TODO client.get
