package it.polimi.ingsw.gc31.client_server.rmi;

import it.polimi.ingsw.gc31.DefaultValues;
import it.polimi.ingsw.gc31.client_server.interfaces.IController;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualClient;
import it.polimi.ingsw.gc31.client_server.interfaces.VirtualServer;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.exceptions.PlayerNicknameAlreadyExistsException;

import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.server.UnicastRemoteObject;

public class RmiServer implements VirtualServer {
    private static IController virtualController = Controller.getController();

    // NOTE descrizione server client:
    // La classe Server serve per generalizzare il concetto di server a prescindere
    // dalla tipologia di protocollo che si sceglie di utilizzare (RMI o TCP).
    // L'obiettivo principale è avere un main comune a entrambi i server.

    // RMI:
    // Per essere passato tra server e client l'oggetto che viene passato deve
    // estendere Remote (interfaccia di RMI) e Serizalizable
    // Si possono registrare su una stessa porta tutti gli oggetti Remote che si
    // desiderano con registry.rebind("nome_oggetto_remoto", oggettoRemoto). Il nome
    // deve però essere univoco.
    // VirtualServer (e VirtualClient) estendono entrambi Remote quindi
    // transitivamente anche RMIserver e TCPServer (RMIclient e TCPclient) estendono
    // Remote
    // VirtualServer generalizza il tipo di server, le sue concretizzazioni sono
    // RMIserver e TCPserver. (idem per VirtualClient, RMIclient e TCPclient)
    // WARN ancora io non so nulla di TCP quindi vedremo se il layer aggiuntivo
    // reggerà anche l'implementazione di TCP...intanto mi sembrava opportuno
    // considerarlo come opzione.

    // Prerequisiti:
    // 1. Assegnare al server un nome
    // 2. Definire lo skeleton (l'oggetto remoto presente sul server di cui il
    // client
    // avrà il riferimento)
    // 3. Registrare il nome dello stub in quanto oggetto remoto

    // Note aggiuntive: se voglio passare al client un altro Remote tramite metodo
    // del Remote che ho bindato sul registry e che il client prende tramite lookup
    // in Remote che viene restituito deve estendere UnicastRemoteObject, altrimenti
    // succede un bordello
    private void serverWrite(String text) {
        System.out.println(DefaultValues.ANSI_GREEN + DefaultValues.RMI_SERVER_TAG + DefaultValues.ANSI_RESET + text);
    }

    public RmiServer() throws RemoteException {
        LocateRegistry.createRegistry(1234).rebind("VirtualServer",
                UnicastRemoteObject.exportObject(this, 0));
        serverWrite("");
    }

    @Override
    public IController clientConnection(VirtualClient client, String username) throws RemoteException {
        try {
            virtualController.connect(client, username);
        } catch (PlayerNicknameAlreadyExistsException e) {
            serverWrite("New connection refused - username already exists");
            return null;
        }
        serverWrite("New client connected: " + username);
        return virtualController;
    }

}
