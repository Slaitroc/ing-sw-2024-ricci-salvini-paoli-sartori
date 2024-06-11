# ToDo Verbose List

###### <span style="color: red;">Note:</span> <u>se ne fixate qualcuno aggiornate il checkbox!</u>

- [x] Avendo aggiunto gli oggetti network molti metodi che sono nel package client_server.interfaces non vengono mai chiamati da remoto, pertanto se non sono nemmeno utili per creare analogie tra i protocolli network vanno rimossi dalle interfacce che estendono Remote. Gli oggetti network hanno come tipo del parametro execute la classe concreta e non l'interfaccia (più semplice) ---> Override non effettivamente utilizzato in tutti i metodi che vengono chiamati all'interno degli oggetti.

      SPIEGAZIONE: Usare le interfacce è:
            - utile per creare analogia e coerenze di firme tra TCP e RMI
            - fondamentale per utilizzare RMI
      Però mischiare le cose e buttare tutto nelle interfacce crea confusione su alcuni temi:
            -Le interfacce che servono per avere oggetti con metodi omonimi (classico utilizzo interfacce) non è necessario che estendano Remote. Tuttavia per il corretto funzionamento dei metodi utilizzati da Oggetti remote RMI l'estendere Remote è necessario ---> per non creare due interfacce separate alcune interfacce estendono Remote anche se non è necessario per la tutti i metodi.
            - Remote exception è chiamata da RMI --> eccezione più generica a quelle chiamate da TCP.

- [] rmi ha dei problemi (stavo hostando io su codespace e chri da ubuntu non riusciva a connettersi con RMI (con TCP si) la prima connessione di lookup veniva rilevata --> magari il problema è la risposta)

- [ ] se un utente logga e non inserisce il nome prima dell'altro blocca quello successivo (in rmi non sembra succedere ma in tcp si)--> potrebbe succedere anche in RMI ma è molto improbabile (dovrebbero loggare in istanti vicinissimi)
      SOLUZIONE (IDEA):
      idea 1 -> assegnare un id al client e tenere traccia del counter lato server (servono anche dei nuovi oggetti network per incrementare il counter)
      idea 1 non bellissima
      idea 2 -> sapere chi è il client serve per poter inviare il connect object, visto che forse abbiamo fatto un altro canale per l'heartBeat se quello funziona con le stringhe potremmo usarlo anche per settare le connessioni iniziali --> così non serve più connect object e nemmeno salvare le temporary connection.
- [ ] alla disconnessione del client il server printa uno stack trace ma sarebbe meglio scrivere un messaggio di log con le info
- [x] impostare i valori di default per la connessione se non viene inserito nessun input
      SOLUZIONE: ora ci sono due nuovi valori di DV per forzare l'ip

- [ ] non cambia il side della carta dopo che viene selezionata (problema di player state)
- [ ] feedback e storico delle mosse lo vogliamo mettere? magari anche qualcosa che ricorda che carta viene selezionata
- [ ] (controllare le starter card---> soprattutto le risorse centrali...vengono stampate?)
- [ ] il log del server non scrive quando viene creato un nuovo game
- [ ] scrivere di chi è il turno anche agli altri player
- [ ] il log del server quando qualcuno chiama un'azione che non è nello stato corretto scrive che è stato fatto ma non specifica chi lo ha fatto (lo togliamo o lo implementiamo?)
- [ ] fixare gli input sbagliati nei comandi (cathare l'input vuoto soprattutto)
- [ ] è successo una volta che mi è rimasta una carta in mano dopo aver piazzato (magari facciamo un comando per cancellare la carta corrispondente post place)
- [ ] per risolvere le bestemmie sul size della tui forzare lo zoom della tui (e forse il font)
- [ ] previsione dei punti calcolati a fine partita????? (slaitrocka non vuole aspettare ne contare da solo)
- [ ] scrivere in giallo gold deck (chri dice che riesce a fare i contorni crazyyy)
- [ ] x chri ---> stampare le carte in ordine di piazzamento
- [ ] aggiungere suono che notifica il player quando p il suo turno

- [ ] gestire correttamente le eccezioni (le stiamo rimandando ma per il debug finale possono aiutare) ---> pensavo anche di creare dei print stack trace più human readable (almeno lato client)

## Debug da fare

- [ ] provare a fare due partite da due in contemporanea
- [ ] dare la possibilità di quittare un game prima che parta (prima di playing state (tui))
- [ ] togliere la chat prima di playing state (o introdurre una global chat)
- [x] help non chiama la state.notify() --> risolto

## Model

- [ ] controllare show down (quando qualcuno raggiunge 20 pt)
- [ ] poi si finisce il giro dei turni e si gioca un solo turno aggiuntivo
- [ ] poi devono venir stampati punti sia sul client che sul server (non succede)
- [ ] va creata la tui di resoconto finale
