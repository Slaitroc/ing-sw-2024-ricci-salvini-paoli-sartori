# Peer-Review 1: UML

Christian Salvini, Lorenzo Ricci, Matteo Paoli, Alessandro Sartori

Gruppo 31

Valutazione del diagramma UML delle classi del gruppo 21.

## Lati positivi

- L'UML presenta già oggetti e metodi per la gestione delle funzionalità aggiuntive quali chat, gestione di partite multiple e persistenza del game. Inoltre consente una comprensione sufficientemente dettagliata della possibile implementazione.

- Apprezzato l'utilizzo dello strategy pattern per la gestione delle carte obiettivo.

## Lati negativi

#### Card

- La gestione delle risorse tramite l'oggetto Corner potrebbe essere semplificata usando una lista di Resource e utilizzando l'indice per identificare le posizioni delle risorse nella carta. In questo modo sarebbe riutilizzabile anche per le StarterCard.

- `getNeededResources()`, presente solamente in GoldCard ne rende articolato l'utilizzo. Ad esempio per effettuare una chiamata da un elemento generico di `handsCard`:Card[] sembra necessario verificarne il tipo con `instanceof`.

- L'utilizzo di `Resource.CORNER` per rappresentare la sfida delle GoldCard e il calcolo dei punti ad essa associati potrebbe essere gestita sempre tramite strategy pattern.

#### Strategy Pattern e ObjectiveCard

- `calculatePoints()` è un metodo di ObjectiveCard e dell'interfaccia GoalStrategy, quindi al chiamante sono disponibili
  `positionCard.calculatePoints()` e `positionCard.goalStrategy.calculatePoints()` creando un'ambiguità non strettamente necessaria.
  Sebbene questa situazione sia risolvibile impostando l'attributo `goalStrategy` di PositionCard come privato una soluzione che semplifica ulteriormente il codice potrebbe essere utilizzare un attributo che implementa GoalStrategy direttamente nella classe astratta ObjectiveCard.
  In questo modo si possono gestire le varie implementazioni di `calculatePoints()` evitando di creare due diversi livelli di ereditarietà.
  WiseManCard e KingdomCard diventerebbero due ulteriori implementazioni di GoalStrategy.

#### Field

- Racchiudere tutti gli attributi `private count*` in una `Map<Resource,Integer>`
- Un modo più agevole di `getPlayedCard()` e `getPlayedPosition()` per gestire le carte presenti nella mappa playArea potrebbe essere restituire direttamente la mappa, o una sua copia, al chiamante che potrebbe a quel punto usare i metodi di `Map`.

È consigliabile aggiungere i packages per aumentare la leggibilità dell'UML.

## Confronto tra le architetture

Le architetture sono simili in molti punti. Le principali differenze sono:

- Per quanto riguarda lo strategy pattern, come è stato consigliato nella sezione precedente, abbiamo racchiuso al suo interno anche le sfide delle carte oro e di tutte le carte obiettivo.
- abbiamo creato una classe Deck che crea le carte e le mischia. Inoltre contiene la logica che permette di pescare. Sebbene questa scelta sia più vicina al paradigma OO, la gestione dei deck vista in questo UML potrebbe essere più semplice e immediata.
- L'architettura dell'oggetto Card è differente in quanto abbiamo scelto di creare classi apposite per i due lati che presentano caratteristiche comuni nelle varie carte.
- È diversa anche la gestione delle risorse all'interno delle carte, per le quali abbiamo utilizzato una lista dove ad ogni indice corrisponde una posizione sulla carta: da 0 a 3 sono gli angoli, da 4 a 6 le risorse centrali se presenti.
- Per la costruzione delle carte ci siamo affidati alla libreria Gson, in questo modo possiamo creare gli oggetti delle carte da un file json tramite deserializzazione. Dall'UML analizzato non è chiaro come questo avvenga e potrebbe dunque esserci una differenza.
