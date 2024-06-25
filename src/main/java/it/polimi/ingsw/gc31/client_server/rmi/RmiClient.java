package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.client_server.interfaces.*;
import it.polimi.ingsw.gc31.client_server.queue.clientQueue.ClientQueueObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.*;
import it.polimi.ingsw.gc31.exceptions.NoGamesException;
import it.polimi.ingsw.gc31.utility.DV;
import it.polimi.ingsw.gc31.view.UI;

import java.awt.*;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.LinkedBlockingQueue;

public class RmiClient extends UnicastRemoteObject implements VirtualClient, ClientCommands {
    private IController controller;
    private final VirtualServer server;
    private IGameController gameController;
    private Integer idGame;
    private String username;
    private UI ui;
    private final LinkedBlockingQueue<ClientQueueObject> callsList;
    private int token;
    private boolean firstConnectionDone = false;

    /**
     * Creates a client with a default name and calls inner procedures to:
     * <p>
     * - choose the UI type;
     * <p>
     * - sets its name and assigning it the remote controller once the name is
     * verified by the server controller.
     */
    public RmiClient(String ipaddress) throws RemoteException, NotBoundException {
        this.server = (VirtualServer) LocateRegistry.getRegistry(ipaddress, DV.RMI_PORT)
                .lookup("VirtualServer");
        this.server.RMIserverWrite("New connection detected from ip: " + server.getClientIP());
        this.server.generateToken(this);
        this.username = DV.DEFAULT_USERNAME;
        this.controller = null;
        this.callsList = new LinkedBlockingQueue<>();
        timer = new Timer(true);
        new Thread(this::executor).start();

    }

    @Override
    public void sendCommand(ClientQueueObject obj) throws RemoteException {
        addQueueObj(obj);
    }

    // QUEUE
    private void addQueueObj(ClientQueueObject obj) {
        synchronized (callsList) {
            callsList.add(obj);
            callsList.notify();
        }
    }

    private void executor() {
        new Thread(() -> {
            while (true) {
                ClientQueueObject action;
                synchronized (callsList) {
                    while (callsList.isEmpty()) {
                        try {
                            callsList.wait();
                        } catch (InterruptedException e) {
                            throw new RuntimeException(e);
                        }
                    }
                    action = callsList.poll();
                }
                if (action != null) {
                    action.execute(ui);

                }
            }
        }).start();
    }

    // CLIENT COMMANDS
    public void setUI(UI ui) {
        this.ui = ui;
    }

    @Override
    public void setUsernameCall(String username) throws RemoteException {
        if (controller == null) {
            server.sendCommand(new ConnectObj(username, token));
        }

    }

    @Override
    public void setUsernameResponse(String username) {
        this.username = username;

    }

    @Override
    public void createGame(int maxNumberPlayer) throws RemoteException {
        controller.sendCommand(new CreateGameObj(username, maxNumberPlayer));
    }

    @Override
    public void joinGame(int idGame) throws RemoteException {
        controller.sendCommand(new JoinGameObj(username, idGame));
    }

    @Override
    public void getGameList() throws RemoteException, NoGamesException {
        controller.sendCommand(new GetGameListObj(username));
    }

    @Override
    public void setReady(boolean ready) throws RemoteException {
        gameController.sendCommand(new ReadyStatusObj(ready, username));

        // this.ready = ready;
        // if (ready) {
        // try {
        // gameController.checkReady();
        // } catch (RemoteException | IllegalStateOperationException e) {
        // e.printStackTrace();
        // }
        // }
    }

    @Override
    public void drawGold(int index) throws RemoteException {
        gameController.sendCommand(new DrawGoldObj(username, index));
    }

    @Override
    public void drawResource(int index) throws RemoteException {
        gameController.sendCommand(new DrawResObj(username, index));
        // gameController.drawResource(username);
    }

    @Override
    public void chooseSecretObjective1() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 0));
    }

    @Override
    public void chooseSecretObjective2() throws RemoteException {
        gameController.sendCommand(new ChooseSecretObjectiveObj(username, 1));
    }

    @Override
    public void playStarter() throws RemoteException {
        gameController.sendCommand(new PlayStarterObj(username));
    }

    @Override
    public void play(Point point) throws RemoteException {
        gameController.sendCommand(new PlayObj(username, point.x, point.y));
    }

    @Override
    public void selectCard(int index) throws RemoteException {
        gameController.sendCommand(new SelectCardObj(username, index));
    }

    @Override
    public void changeSide() throws RemoteException {
        gameController.sendCommand(new FlipCardObj(username));
    }

    @Override
    public void changeStarterSide() throws RemoteException {
        gameController.sendCommand(new FlipStarterCardObj(username));
    }

    @Override
    public int getGameID() throws RemoteException {
        return idGame;
    }

    @Override
    public String getUsername() {
        return username;
    }

    // VIRTUAL CLIENT
    private boolean ready = false;

    @Override
    public boolean isReady() {
        return ready;
    }

    @Override
    public void setGameID(int i) throws RemoteException {
        this.idGame = i;
    }

    @Override
    public void sendChatMessage(String username, String message) throws RemoteException {
        gameController.sendCommand(new ChatMessage(username, message));
    }

    @Override
    public void sendChatMessage(String fromUsername, String toUsername, String message) throws RemoteException {
        gameController.sendCommand(new ChatMessage(fromUsername, toUsername, message));
    }

    @Override
    public void setController(IController controller) throws RemoteException {
        this.controller = controller;
        startHeartBeat();
    }

    @Override
    public void setGameController(IGameController gameController) throws RemoteException {
        this.gameController = gameController;
    }

    @Override
    public void quitGame() throws RemoteException {
        gameController.sendCommand(new QuitGameObj(username));
    }

    // Risorse per heartbeat
    // FIXME spostare in cima attributi e metodi per heartbeat
    private final Timer timer;

    /**
     * This method starts the process that "sends" the heart beat periodically to
     * the server
     * A heart beat is sent immediately on the first execution and every 2 seconds
     * after it
     */
    public void startHeartBeat() {
        long sendTime = (DV.testHB) ? DV.sendTimeTest : DV.sendTime;
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    sendHeartBeat();
                } catch (RemoteException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, sendTime);
    }

    /**
     * This method sends to the controller the heart beat associated with the
     * VirtualClient that is sending it
     * 
     * @throws RemoteException if an error occurs during the rmi communication
     */
    private void sendHeartBeat() throws RemoteException {
        controller.updateHeartBeat(this);
        // System.out.println("HeartBeat inviato");
    }

    // Metodi per token

    /**
     * This method sets the token of the client to the value received as a parameter
     *
     * @param token is the value needed to be set as the client's token
     */
    @Override
    public void setRmiToken(int token) throws RemoteException {
        this.token = token;
    }

    @Override
    public void setToken(int token) {
        String userHome = System.getProperty("user.home");
        String desktopPath = DV.getDesktopPath(userHome);
        String folderName = "CodexNaturalis";
        String fileName = "Token.txt";
        // Crea il percorso completo della cartella e del file
        Path folderPath = Paths.get(desktopPath, folderName);
        Path filePath = Paths.get(desktopPath, folderName, fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createDirectories(folderPath);
            } catch (IOException e) {
                ui.showGenericClientResonse("Errore nel salvataggio del token!");
                e.printStackTrace();
            }
        } else {
            // try {
            // long lines = Files.lines(filePath).count();
            // ui.showGenericClientResponse("Numero di righe del file: " + lines);
            // if (lines > 10) {
            // java.util.List<String> lastNineLines = getLastNineLines(filePath);
            // Path newFilePath = Paths.get(desktopPath, folderName, "LastNineLines.txt");
            // try (BufferedWriter writer = Files.newBufferedWriter(newFilePath,
            // StandardOpenOption.CREATE,
            // StandardOpenOption.TRUNCATE_EXISTING)) {
            // for (String line : lastNineLines) {
            // writer.write(line);
            // writer.newLine();
            // }
            // }
            // ui.showGenericClientResponse(
            // "Le ultime 9 righe sono state copiate nel file: " + newFilePath.toString());
            // }
            // // Files.delete(filePath);
            // // ui.showGenericClientResponse("File esistente eliminato.");
            // } catch (IOException e) {
            // e.printStackTrace();
            // }
        }

        try (BufferedWriter writer = Files.newBufferedWriter(filePath, StandardOpenOption.CREATE,
                StandardOpenOption.APPEND)) {
            writer.write("" + token);
        } catch (IOException e) {
            e.printStackTrace();
        }
        ui.showGenericClientResonse("Token salvato correttamente nel percorso: ");
        ui.showGenericClientResonse(filePath.toString());

    }

    @Override
    public void reconnect(boolean reconnect) throws RemoteException {
        controller.sendCommand(new ReconnectObj(reconnect, username, token));
    }

    /**
     * Method invoked by the ui when the user specify if it wants to play another match or not
     *
     * @param wantsToRematch is the boolean value associated to the response (true: wants to rematch, false otherwise)
     * @throws RemoteException if an error occurred during the rmi connection
     */
    @Override
    public void anotherMatchResponse(Boolean wantsToRematch) throws RemoteException {
        gameController.sendCommand(new AnotherMatchResponseObj(username, wantsToRematch));
    }
}