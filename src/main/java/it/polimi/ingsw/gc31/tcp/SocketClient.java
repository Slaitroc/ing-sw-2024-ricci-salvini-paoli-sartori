// package it.polimi.ingsw.gc31.tcp;

// import it.polimi.ingsw.gc31.OurScanner;
// import java.io.*;
// import java.net.Socket;
// import java.net.UnknownHostException;
// import java.rmi.RemoteException;
// public class SocketClient {
// final BufferedReader input;
// final VirtualSocketServer server;
// private String username;
// private Integer idGame;

// //TODO Manca il modo per assegnare correttamente il idGame al singolo player.
// Ora tenuto costantemente null
// protected SocketClient(BufferedReader input, PrintWriter output){
// this.input = input;
// this.server = new VirtualSocketServer(output);
// this.username = null;
// this.idGame = null;
// }

// private void run() throws RemoteException {
// new Thread(() -> {
// try {
// runVirtualServer();
// } catch (Exception e) {
// throw new RuntimeException(e);
// }
// }).start();

// //runCli();
// }

// // TODO Dovrebbe servire per leggere i messaggi in arrivo dal server e
// richiamare i rispettivi metodi
// // serve anche per stampare a video i messaggi che il server semplicemente
// invia al client
// private void runVirtualServer() throws IOException{
// String line;
// while(true) {
// line = OurScanner.scanner.nextLine();
// //while ((line = scan.nextLine()) != null) {
// switch (line) {
// //case "" -> System.out.println("Message");
// default -> System.out.println(line);
// }
// //}
// }
// }

// /*
// private void runCli(){
// String line;

// System.out.print("[ SERVER ] Inserisci username: ");
// Scanner scan = new Scanner(System.in);
// this.username = scan.nextLine();
// server.connect(username);

// // Qui devo leggere ciò che scrive l'utente e inoltro la richiesta di
// esecuzione al clientHandler (?)
// // Attraverso l'utilizzo del VirtualSocketServer. Per inoltrare però devo
// avere informazioni sul player
// // che non ho ora, come username
// while(true) {
// line = scan.nextLine();

// switch(line){
// case "mostra game" : {
// server.getGameList(username);
// break;
// }
// case "crea game" : {
// System.out.print("[ SERVER ] Inserisci il numero di giocatori della partita:
// ");
// int maxNumPlayers = Integer.parseInt(scan.nextLine());
// // Istruzione necessaria per non stampare a schermo due > di fila
// // scan.nextLine();
// server.createGame(username, maxNumPlayers);
// //System.out.println("Creata partita con id: " + idGame);

// // I'll never exit this while statement, maybe it's bad I don't know
// runCliInitGame();
// break;
// }
// case "join game" : {
// System.out.print("[ SERVER ] Inserisci l'id del game: ");
// idGame = scan.nextInt();
// server.joinGame(username, idGame);
// break;
// }
// default : System.out.println("[ SERVER ] [ INVALID MESSAGE ]");
// }
// }
// }

// public void runCliInitGame(){
// Scanner scan = new Scanner(System.in);

// while (true){
// String line = scan.nextLine();

// switch(line){
// case "info" : {
// server.info();
// break;
// }
// case "mostra mano" : {
// server.getHand(username, idGame);
// break;
// }
// case "draw gold" : {
// server.drawGold(username, idGame);
// break;
// }
// default : System.out.println("[ SERVER ] [ INVALID MESSAGE ]");
// }
// }
// }
// */

// public static void main(String[] args) throws IOException{
// //TODO Verificare valori opportuni di host/port per il corretto funzionamento
// finale
// String host = "127.0.0.1";
// int port = Integer.parseInt("1234");

// Socket serverSocket = new Socket(host, port);

// InputStreamReader socketRx = new
// InputStreamReader(serverSocket.getInputStream());
// OutputStreamWriter socketTx = new
// OutputStreamWriter(serverSocket.getOutputStream());

// new SocketClient(new BufferedReader(socketRx), new
// PrintWriter(socketTx)).run();
// }
// }
