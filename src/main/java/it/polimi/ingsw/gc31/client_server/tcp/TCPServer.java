package it.polimi.ingsw.gc31.client_server.tcp;

import it.polimi.ingsw.gc31.utility.DV;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class TCPServer {
    final ServerSocket listenSocket;
    private final List<SocketClientHandler> handlers = new ArrayList<>();

    /**
     * This method writes on System.Out a specific type of string characterizing the
     * TCP communications
     *
     * @param text is the text to write on System.Out
     */
    public void TCPserverWrite(String text) {
        System.out.println(DV.ANSI_YELLOW + DV.TCP_SERVER_TAG + DV.ANSI_RESET + text);
    }

    /**
     * This method is the constructor of the class. The method creates the TCP
     * server and writes on System.Out the
     * ip of the server and the port used
     *
     * @param ipaddress is the ip of the server
     * @throws NumberFormatException if a string does not have the appropriate type to be converted in number
     * @throws IOException           if an error occurs during the ServerSocket creation
     */
    public TCPServer(String ipaddress) throws NumberFormatException, IOException {
        this.listenSocket = new ServerSocket(DV.TCP_PORT, 50, InetAddress.getByName("0.0.0.0"));
        TCPserverWrite("Server IP " + ipaddress);
        TCPserverWrite("Server in ascolto sulla porta " + DV.TCP_PORT);

        runServer();
    }

    /**
     * This method is invoked right after the creation of the TCP server.
     * It creates a thread that accept all the new TCP connections arriving.
     * For every connection detected the method creates a SocketClientHandler for
     * the specific client and add it to
     * the list of all the handlers.
     */
    public void runServer() {
        TCPserverWrite("Server created");
        new Thread(() -> {
            while (true) {
                try {
                    Socket clientSocket = this.listenSocket.accept();
                    TCPserverWrite(
                            "New connection detected from ip: " + clientSocket.getInetAddress().getHostAddress());

                    ObjectOutputStream socketTx = new ObjectOutputStream(clientSocket.getOutputStream());
                    ObjectInputStream socketRx = new ObjectInputStream(clientSocket.getInputStream());

                    SocketClientHandler handler = new SocketClientHandler(socketRx, socketTx);
                    this.handlers.add(handler);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}