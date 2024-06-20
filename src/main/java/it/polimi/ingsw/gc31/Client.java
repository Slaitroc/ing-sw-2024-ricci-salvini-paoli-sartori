package it.polimi.ingsw.gc31;

import static it.polimi.ingsw.gc31.utility.OurScanner.scanner;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.NotBoundException;
import it.polimi.ingsw.gc31.client_server.interfaces.ClientCommands;
import it.polimi.ingsw.gc31.client_server.rmi.RmiClient;
import it.polimi.ingsw.gc31.client_server.tcp.TCPClient;
import it.polimi.ingsw.gc31.utility.CliPrint;
import it.polimi.ingsw.gc31.utility.IPvalidator;
import it.polimi.ingsw.gc31.view.gui.GUI;
import it.polimi.ingsw.gc31.view.tui.TUI;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

public class Client {

    public static void main(String[] args) throws NotBoundException, RemoteException {
        // AnsiConsole.systemInstall();
        int chosenUI;
        ClientCommands client;
        // clean the terminal
        String ipaddress;
        boolean result = true;
        global: while (true) {
            System.out.print("\033[H\033[2J");
            System.out.flush();
            if (result == false) {
                CliPrint.coloredPrintRed("Wrong ip format");
            }

            result = false;
            CliPrint.coloredPrintPurple("Write Server IP (leave empty if localhost)");
            ipaddress = scanner.nextLine();
            result = IPvalidator.isValid(ipaddress);
            if (result == false)
                continue global;

            // if the player does not enter anything, then ipAddress has the value of
            // localhost
            if (ipaddress.isEmpty()) {
                ipaddress = "127.0.0.1";
                CliPrint.coloredPrintBlue(Ansi.ansi().cursorUp(1).a("localhost: " + ipaddress));
            }

            boolean validConnectionType;
            do {
                validConnectionType = true;
                CliPrint.coloredPrintPurple("Chose connection:");
                CliPrint.coloredPrintCyan("or type 'restart' to restart the procedure:");
                System.out.println("\t1 -> RMI\n\t2 -> TCP");

                String networkChoise = scanner.nextLine().toLowerCase();

                client = null;
                try {
                    if (Integer.parseInt(networkChoise) == 1) {
                        CliPrint.coloredPrintBlue(Ansi.ansi().cursorUp(1).a("Connection type: RMI"));
                        client = new RmiClient(ipaddress);
                    } else if (Integer.parseInt(networkChoise) == 2) {
                        CliPrint.coloredPrintBlue(Ansi.ansi().cursorUp(1).a("Connection type: TCP"));
                        client = new TCPClient(ipaddress);
                    } else {
                        CliPrint.coloredPrintRed("Invalid Input");
                        validConnectionType = false;
                    }
                } catch (NumberFormatException e) {
                    if (networkChoise.equals("restart")) {
                        continue global;
                    } else {
                        CliPrint.coloredPrintRed("Invalid Input");
                        validConnectionType = false;
                    }
                    // e.printStackTrace();
                } catch (RemoteException e) {
                    e.printStackTrace();
                } catch (NotBoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } while (!validConnectionType);

            boolean validTUI;
            do {
                CliPrint.coloredPrintPurple("Chose UI:");
                CliPrint.coloredPrintCyan("or type 'restart' to restart the procedure:");
                System.out.println("\t1 -> TUI\n\t2 -> GUI:");
                String uiChoise = scanner.nextLine();
                validTUI = true;

                try {
                    if (Integer.parseInt(uiChoise) == 1) {
                        chosenUI = 1;
                        break global;
                    } else if (Integer.parseInt(uiChoise) == 2) {
                        chosenUI = 2;
                        break global;
                    } else {
                        CliPrint.coloredPrintRed("Invalid Input");
                        validTUI = false;
                    }
                } catch (NumberFormatException e) {
                    if (uiChoise.equals("restart")) {
                        continue global;
                    } else {
                        CliPrint.coloredPrintRed("Invalid Input");
                        validTUI = false;
                    }
                    // e.printStackTrace();
                }
            } while (!validTUI);
        }

        if (chosenUI == 2)
            new GUI(client).runUI();
        if (chosenUI == 1)
            new TUI(client).runUI();

    }
}
