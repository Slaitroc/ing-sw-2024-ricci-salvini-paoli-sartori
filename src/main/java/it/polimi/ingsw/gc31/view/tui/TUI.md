# Note
- I comandi della tui vengono eseguiti tutti dal Process Thread (usare il metodo commandToProcess(TUIcommands, boolean) --> con il boolean si può scegliere si aggiungere anche lo state notify per sbloccare direttamente il comando)

- [ ] Nomi da cambiare:
    - la lista dei comandi cmdLineMessages -> cmdLineCommands

- problema dei cursori:
    il cmdOut stampa sempre in risposta a comandi prima che questi vengano sbloccati e in questo modo funziona. MA quando passo alla chat scrivendo 'chat' succede che la chat accede allo scanner in input prima che il cmdOut riesca ad aggiornarsi e finisco per scrivere l' input in mezzo al cmdlineOut invece che nella posizione del cursore di chat input. 
    Prima idea: aspettare che il cmdOut termini di stampare prima di accedere all'input della chat --> come? un altro lock non lo voglio aggiungere. 
    Seconda idea: non scrivere nulla su cmdLineOut (meno bello ma funziona)

