package it.polimi.ingsw.gc31.view.tui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class newTUI {

    private Thread inputThread;
    private volatile boolean shouldInterrupt = false;

    public void runInputLoop() {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        try {
            while (!shouldInterrupt) {
                if (reader.ready()) {
                    StringBuilder inputBuilder = new StringBuilder();
                    while (reader.ready()) {
                        inputBuilder.append((char) reader.read());
                    }
                    String input = inputBuilder.toString().trim();
                    if (!input.isEmpty()) {
                        System.out.println("Input: " + input);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Eccezzione nel run --> Thread Interrotto");
        } finally {
            try {
                reader.close(); // Chiude il BufferedReader
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void runThreads() {
        inputThread = new Thread(this::runInputLoop);
        inputThread.start();

    }

    public void stopThreads() {
        if (inputThread != null && inputThread.isAlive()) {
            shouldInterrupt = true; // Imposta il flag per interrompere il thread di input
        }
        inputThread.interrupt(); // Interrompe il thread di input

    }

    public static void main(String[] args) {
        newTUI tui = new newTUI();
        tui.runThreads();

        // Dopo un po' di tempo, interrompi i thread
        try {
            Thread.sleep(5000); // Attendiamo 3 secondi prima di interrompere i thread
        } catch (InterruptedException e) {
            System.out.println("TUIthread interrotto.");
        }
        tui.stopThreads(); // Interrompi i thread
        System.out.println("FINEEEEE");
    }
}
