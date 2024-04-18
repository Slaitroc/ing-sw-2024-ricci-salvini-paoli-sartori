package it.polimi.ingsw.gc31.client_server.interfaces;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

import it.polimi.ingsw.gc31.exceptions.NoGamesException;

public interface VirtualClient extends Remote {
    // TODO questo devo cambiarlo, per coerenza con quello che ha fatto chri e farlo
    // chiamare dal controller. O ancora meglio implementare un pattern ObOb
    /**
     * Allows the UI to set the clients name after validating it through
     * the remote controller.
     * 
     * @param name
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public void setUsername(String name) throws RemoteException;

    /**
     * Allows the Controller to set the client's remote PlayerController once
     * initialized locally on the server
     * 
     * @param playerController
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public void setPlayerController(IPlayerController playerController) throws RemoteException;

    public void setGameID(int i) throws RemoteException;

    public int getGameID() throws RemoteException;

    /**
     * Crates a new MainGameController which corresponds to a new game lobby
     * 
     * @param maxPlayersNumber : the player the game needs to be started
     * @return <code> true </code> if the MainGameController was successfully
     *         created
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public boolean createGame(int maxPlayersNumber) throws RemoteException;

    /**
     * Obtains the list through the remote controller
     * 
     * @return the game list (this allows the UI to display it)
     * @throws RemoteException
     * @throws NoGamesException
     * 
     * @Slaitroc
     */
    public List<String> showGames() throws RemoteException, NoGamesException;

    /**
     * Calls the corresponding Controller method and sets the clients
     * MainGameController
     * 
     * @param idGame
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public void joinGame(int idGame) throws RemoteException;

    /**
     * Calls <code>MainGameController.checkReady()</code> to check the other clients
     * state and if their all ready init the model.
     * Through <code>MainGameController.startGame()</code> its also responsible to
     * close the running TUI and start the game TUI for all the clients (in case
     * they're all ready)
     * 
     * @return
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public boolean ready() throws RemoteException;

    /**
     * @return player's hand for the UI to display it
     * @throws RemoteException
     */
    public List<String> showHand() throws RemoteException;

    public void drawGold() throws RemoteException;

    /**
     * Allows the MainGameController to check if they a clients is ready
     * 
     * @return <code>true</code> if the client is ready. <code>false</code>
     *         otherwise.
     * @throws RemoteException
     * 
     * @Slaitroc
     */
    public boolean isReady() throws RemoteException;

    /**
     * This method should update the UI to update scene to be ready for the game
     * 
     * @throws RemoteException
     */
    public void startGame() throws RemoteException;

}
