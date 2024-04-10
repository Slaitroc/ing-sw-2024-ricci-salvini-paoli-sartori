package it.polimi.ingsw.gc31.client_server;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.Scanner;

import it.polimi.ingsw.gc31.client_server.rmi.RMIServer;

//NOTE descrizione server client:
//La classe Server serve per generalizzare il concetto di server a prescindere dalla tipologia di protocollo che si sceglie di utilizzare (RMI o TCP). L'obiettivo principale è avere un main comune a entrambi i server.

//RMI:
//Per essere passato tra server e client l'oggetto che viene passato deve estendere Remote (interfaccia di RMI) e Serizalizable 
//Si possono registrare su una stessa porta tutti gli oggetti Remote che si desiderano con registry.rebind("nome_oggetto_remoto", oggettoRemoto). Il nome deve però essere univoco. 
//VirtualServer (e VirtualClient) estendono entrambi Remote quindi transitivamente anche RMIserver e TCPServer (RMIclient e TCPclient) estendono Remote
//VirtualServer generalizza il tipo di server, le sue concretizzazioni sono RMIserver e TCPserver. (idem per VirtualClient, RMIclient e TCPclient)
//WARN ancora io non so nulla di TCP quindi vedremo se il layer aggiuntivo reggerà anche l'implementazione di TCP...intanto mi sembrava opportuno considerarlo come opzione.
//Vedi il commento Serie di Invocazioni prima delle procedure. 

public class Server {

    static VirtualServer engine;

    // Il main permette di scegliere quale procedura privata invocare tra
    // serverRMI() e serverTCP()
    // ...per ora solo serverRMI()
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int choice = 0;
        while (choice != 1) {
            System.out.println("Choose Server Type:");
            System.out.println("1 -> [RMI] ");
            choice = scanner.nextInt();
            if (choice == 1) {
                engine = new RMIServer();
            } else {
                System.out.println("Invalid choice");
            }
        }

        try {
            serverRMI();
        } catch (RemoteException e) {
            e.printStackTrace();
        }

        scanner.close();
    }

    // Prerequisiti:
    // 1. Assegnare al server un nome
    // 2. Definire lo skeleton (l'oggetto remoto presente sul server di cui il
    // client
    // avrà il riferimento)
    // 3. Registrare il nome dello stub in quanto oggetto remoto

    private static void serverRMI() throws RemoteException {
        // 1
        String name = "VirtualServerRMI";
        // 2
        // WARN definirlo in questo modo sembra essere deprecato. Invece di usare
        // staticamente UnicastRemoteObject viene consigliato di farlo dinamicamente con
        // new UnicastRemoteObject()....non so bene che fare a riguardo. Il prof ha
        // fatto così...
        VirtualServer skeleton = (VirtualServer) UnicastRemoteObject.exportObject(engine, 1234);
        // NOTE fai anche una prova con la porta sbagliata dopo aver definito lo stub
        // nel client
        // 3.0
        Registry registry = LocateRegistry.createRegistry(1234);
        // 3.1
        registry.rebind(name, skeleton);

        System.out.println("[RMI-Server]");

    }

    // Serie di Invocazioni:
    private static void serverTCP() {
    }

    // Esempio di una serie di invocazioni:
    // L'oggetto registrato è l'RMIserver con nome "VirtualServerRMI"
    //

}
