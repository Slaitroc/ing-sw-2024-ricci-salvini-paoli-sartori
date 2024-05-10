# Peer-Review 1: UML

Lorenzo Ricci, Christian Salvini, Alessandro Sartori, Matteo Paoli

**Gruppo 31**

Valutazione del diagramma UML delle classi del gruppo 21.

## Lati positivi

Vediamo che sono stati seguiti alcuni dei consigli della precedente revisione: ora lo strategy pattern è più intuitivo e speriamo anche efficace; notiamo anche il package per la serializzazione con Json sebbene non sia chiaro se viene utilizzata una libreria esterna o meno.
Apprezzata la decisione di rendere sincrono tcp nelle fasi iniziali e asincrono rmi durante la partita. <br>
Interessante l'utilizzo di oggetti immutabili bean per non esporre il model. <br>
Ottima la creazione di canali di comunicazioni tcp diversi tra fasi iniziali e la partita. <br>
Apprezzata anche la suddivisione del class diagram in packages.

## Lati negativi

Non è chiaro l'utilizzo effettivo dei bean a questo punto dell'implementazione. Non essendo espliciti i riferimenti nel class diagram immaginiamo che verranno implementati ulteriormente con la view. <br>

Il class diagram si potrebbe rendere più intuitivo per semplificarne la lettura cercando di collassare gli spazi privi di contenuto. Inoltre sebbene la divisione in package sia decisamente migliorata ci sono comunque alcune classi che non sembrano coerenti con il package di appartenenza (ad esempio il Client nel package Controller). <br>
Per evitare la ridondanza è meglio non riscrivere i metodi che sono ovviamente presenti in quanto implementazioni di interfacce.

Invece di creare classi separate per gli oggetti RMIMasterController e RMIGame poteva essere più semplice utilizzare direttamente MasterController e GameController come oggetti rmi.

La classe CodexNaturalis non ci sembra particolarmente utile, pensiamo sia più corretto avere due eseguibili differenti per client e server.<br>

Le eccezioni personalizzate dovrebbero già essere state create a questo punto dello sviluppo e spostate nel package principale o nei package che generano quelle eccezioni.<br>

Solo dal class diagram risulta complicato fare un'analisi più approfondita per cui sarebbe stato utile ricevere una descrizione più nel dettaglio dell'effettiva implementazione.

## Confronto tra le architetture

#### Punti in comune

Come voi abbiamo implementato il pattern Observer-Observable tra model e vista per aggiornare quest'ultima.<br>
Prendendo ispirazione dalla vostra implementazione probabilmente creeremo connessioni separate tcp per le fasi di creazione della lobby e game.

#### Differenze

Per mantenere evidente l'analogia tra tcp e rmi il nostro package relativo al networking, oltre alla classe RMIClient, contiene TCPClient che si comporta anche da VirtualSocketServer.
In generale le concretizzazioni di client gestiscono le diverse tipologie di connessioni e viste. In generale abbiamo deciso di collassare layer di classi che seppur favoriscano l'incapsulamento ci sembra complichino senza benefici significativi la struttura del progetto.

Il nostro progetto prevede due eseguibili differenti per client e server.

## Conclusioni

Abbiamo cercato di evidenziare tutto quello che ci sembrava poco chiaro avendo come unico schema l'UML e eventuali differenze. A prescindere dallo stato dell'implementazione la struttura ci sembra corretta e, seppure un po' complicata in certi punti, efficace.
