# ToDo Verbose List

###### <span style="color: red;">Note:</span> <u>se ne fixate qualcuno aggiornate il checkbox!</u>

- [x] Avendo aggiunto gli oggetti network molti metodi che sono nel package client_server.interfaces non vengono mai chiamati da remoto, pertanto se non sono nemmeno utili per creare analogie tra i protocolli network vanno rimossi dalle interfacce che estendono Remote. Gli oggetti network hanno come tipo del parametro execute la classe concreta e non l'interfaccia (più semplice) ---> Override non effettivamente utilizzato in tutti i metodi che vengono chiamati all'interno degli oggetti.
      SPIEGAZIONE: Usare le interfacce è: - utile per creare analogia e coerenze di firme tra TCP e RMI - fondamentale per utilizzare RMI
      Però mischiare le cose e buttare tutto nelle interfacce crea confusione su alcuni temi:
      -Le interfacce che servono per avere oggetti con metodi omonimi (classico utilizzo interfacce) non è necessario che estendano Remote. Tuttavia per il corretto funzionamento dei metodi utilizzati da Oggetti remote RMI l'estendere Remote è necessario ---> per non creare due interfacce separate alcune interfacce estendono Remote anche se non è necessario per la tutti i metodi. - Remote exception è lanciata da RMI --> eccezione più generica di quelle chiamate da TCP.

- [x] se un utente logga e non inserisce il nome prima dell'altro blocca quello successivo (in rmi non sembra succedere ma in tcp si)--> potrebbe succedere anche in RMI ma è molto improbabile (dovrebbero loggare in istanti vicinissimi)
      SOLUZIONE (IDEA):
      ---> scrivi la soluzione corretta
- [x] alla disconnessione del client il server printa uno stack trace ma sarebbe meglio scrivere un messaggio di log con le info
- [x] impostare i valori di default per la connessione se non viene inserito nessun input
      SOLUZIONE: ora ci sono due nuovi valori di DV per forzare l'ip dei server

- [x] non cambia il side della carta dopo che viene selezionata (problema di player state)
- [ ] feedback e storico delle mosse lo vogliamo mettere?
- [x] qualcosa che ricorda che carta viene selezionata
- [x] (controllare le starter card---> soprattutto le risorse centrali...vengono stampate?)
- [x] il log del server non scrive quando viene creato un nuovo game
- [ ] scrivere di chi è il turno anche agli altri player
- [ ] il log del server quando qualcuno chiama un'azione che non è nello stato corretto scrive che è stato fatto ma non specifica chi lo ha fatto (lo togliamo o lo implementiamo?)
- [x] fixare gli input sbagliati nei comandi (cathare l'input vuoto soprattutto)
- [ ] per risolvere le bestemmie sul size della tui forzare lo zoom della tui (e forse il font)
- [ ] previsione dei punti calcolati a fine partita????? (slaitrocka non vuole aspettare ne contare da solo)
- [ ] scrivere in giallo gold deck (chri dice che riesce a fare i contorni crazyyy)
- [x] x chri ---> stampare le carte in ordine di piazzamento
- [ ] aggiungere suono che notifica il player quando p il suo turno

- [ ] gestire correttamente le eccezioni (le stiamo rimandando ma per il debug finale possono aiutare) ---> pensavo anche di creare dei print stack trace più human readable (almeno lato client)

## Debug da fare

- [ ] provare a fare due partite da due in contemporanea
- [x] dare la possibilità di quittare un game prima che parta (prima di playing state (tui))
- [ ] togliere la chat prima di playing state (o introdurre una global chat)
- [x] help non chiama la state.notify() --> risolto
- [ ] RMI ha dei problemi di connessione su ubuntu: la connessione di lookup viene rilevata ma non va oltre e lancia un'eccezione.

## Model

- [x] controllare show down (quando qualcuno raggiunge 20 pt)
- [x] poi si finisce il giro dei turni e si gioca un solo turno aggiuntivo
- [ ] poi devono venir stampati punti sia sul client che sul server (non succede)
- [ ] va creata la UI di resoconto finale

## Nuove

- [ ] al termine della partita chiedere se rigiocare e in caso contrario eliminare dal gamecontroller i player che non vogliono. Se tutti non vogliono cancellare il gamecontroller
- [ ] timer che fa vincere l'unico giocatore rimasto se tutti gli altri sono disconnessi. E direi timer per terminare la partita e cancellare il gamecontroller se sono tutti disconnessi
- [ ] aggiustare wrong input nelle fasi iniziali di lancio del client (rmi tc) (tui gui)
- [ ] se un client termina il programma sembra che non lo tolga dal gameController se il game non è ancora stato avviato (ho un solo client e comunque quando faccio show games vedo che c'è un client ancora connesso ma è impossibile)

## GUI

## TUI
Cosa succde quando hai piazzato la carta e provi a selezionare la terza carta in mano?

