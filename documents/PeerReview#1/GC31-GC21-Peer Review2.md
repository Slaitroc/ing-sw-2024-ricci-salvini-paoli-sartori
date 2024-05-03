## model

Il model è stato aggiornato seguendo alcuni dei consigli della precedente revisione:

- i colori delle carte sono stati divisi da quelli delle pedine in due enum chiamate rispettivamente <code>CardColor</code> e <code>PawnColor</code>;
- è ancora presente l'enum <code>Resources</code> ed è stata aggiunta <code> CardType</code> per il tipo delle carte;
- la classe <code>Card</code> è diventata un'interfaccia e <code>PlayableCard</code> e <code>ObjectiveCard</code> non sono più sottoclassi ma implementano l'interfaccia;
- è stata aggiunta l'enum <code>GameState</code> per distinguere i vari stati della partita. Per semplicità non ci sembrava necessario uno state pattern vero e proprio ma potrebbe diventarlo col procedere dello sviluppo;
- ora i metodi di <code>GameModel</code> non restituiscono più un oggetto <code>GameModel</code> perché la concatenazione di chiamate non presentava grandi benefici;
- nello strategies pattern di <code>Objective</code> è stata rimossa la strategia <code>ResourceScore</code> che è collassata dentro <code>Count</code>;

Note: per ora il package chat è ancora dentro model ma verrà probabilmente spostato in client_server una volta implementata.

## controller

Il <code>Controller</code> gestisce le partite multiple creando per ciascuna un <code>GameController</code>.

Attualmente non è necessaria una mappa per accedere ad uno specifico <code>GameController</code> in quanto per partecipare a una partita è l'utente a inserire in input l'indice. Qualora fosse necessario cambieremo l'implementazione come consigliato precedentemente.

Nel <code>GameController</code> abbiamo inoltre implementato una coda di oggetti che estendono <code>QueueObject</code> e rappresentano le possibili azioni da eseguire sul model. In questo modo abbiamo realizzato una coda di chiamate per rendere rmi asincrono coerentemente con tcp.

Abbiamo deciso di mantenere sincrone le chiamate durante le fasi di connessione a un <code>GameController</code>. L'asincronia comincia con l'inizio della partita.

## client_server

In questo package abbiamo cinque sotto-package:

#### interfaces

Contiene le interfacce <code>IController</code> e <code>IGameController</code> che vengono implementate rispettivamente da <code>Controller</code> e <code>GameController</code> così da poter essere passati come RemoteObject in rmi. Queste due interfacce definiscono metodi che vengono chiamati da <code>RMIClient</code> e <code>SocketClientHandler</code>. Il primo li chiama da remoto, il secondo li chiama lato server simulando un <code>RMIClient</code> in risposta ai messaggi tcp che riceve dal <code>TCPClient</code>.

L'interfaccia <code>VirtualClient</code> definisce i metodi che lato server vengono chiamati per aggiornare la view attraverso il pattern Observer-Observable (nota: gli observer vengono indicati come listener nel class diagram).
La stessa interfaccia viene implementata da <code>RMIClient</code> e da <code>SocketClientHandler</code> gestendo, per il momento, la comunicazione in modo sincrono e che dovremmo rendere asincrono in futuro per coerenza con quanto spiegato prima.
<code>VirtualClient</code> implementa anche l'interfaccia <code>ShowUpdate</code> del package view per consentire al client di invocare gli stessi metodi sulla propria <code>UI</code>. Ulteriori dettagli nella sezione apposita.

L'interfaccia <code>ClientCommands</code> definisce i metodi che l'utente può chiamare interagendo con la propria <code>UI</code>.

L'interfaccia <code>VirtualServer</code> permette di gestire la prima connessione di rmi. Il metodo <code>RMIServerWrite</code> e l'analogo <code>TCPServerWrite</code> presente in <code>TCPServer</code> servono semplicemente per formattare correttamente una stringa da stampare sul server che vi è una nuova connessione.

#### tcp

Il <code>TCPServer</code> crea un <code>SocketClientHandler</code> per ogni client che si connette. Quest'ultimo viene eseguito da un thread a parte e scambia messaggi con il <code>TCPClient</code> eseguendo metodi lato server sulla base delle stringhe ricevute. Analogamente il <code>TCPClient</code> riceve messaggi dal <code>SocketClientHandler</code> per chiamare metodi lato client.

#### rmi

Implementazione classica rmi con <code>RMIClient</code> e <code>RMIServer</code>. L'unica particolarità è l'asincronia come scritto sopra.

#### listeners

Abbiamo implementato il design pattern Observable-Observer per notificare il client ogni qual volta avviene una modifica di un elemento osservato del model.
Per ora la chiamata è ancora sincrona ma diventerà asincrona.

#### queue

Il package queue contiene le classi che rappresentano i metodi della partita che possono essere chiamati dal client e che possono essere aggiunti nella coda di <code>QueueObject</code> sopracitata.

## view

Nel package è presente l'interfaccia <code>UI</code> con le concretizzazioni <code>TUI</code> e <code>GUI</code>.
L'interfaccia <code>ShowUpdate</code> viene implementata da <code>UI</code> e da <code>VirtualClient</code> e contiene i metodi per aggiornare la view. A questo punto dell'implementazione stiamo valutando di rimuovere l'implementazione da <code>VirtualClient</code> e rendere la <code>UI</code> un RemoteObject per saltare una passaggio di chiamate, che per il momento abbiamo lasciato per valutarne l'utilità.
La catena di chiamate al momento è la seguente:
Model->VirtualClient->UI
e potrebbe diventare direttamente Model->UI.

L'implementazione della <code>TUI</code> utilizza cinque threads per gestire le relative area dell'interfaccia utente:

- input non bloccante per l'area chat
- gestione output chat
- input non bloccante per l'area commandLine
- esecuzione dei comandi della commandLine
- gestione output commandLine

Molti degli attributi di questa classe servono per gestire la concorrenza.
Le costanti permettono di scegliere il layout.

## exceptions

Semplicemente eccezioni ad hoc per l'app.

## utility

Pattern adapter per l'integrazione della libreria Gson e altri strumenti di utility generale come lettura file.
