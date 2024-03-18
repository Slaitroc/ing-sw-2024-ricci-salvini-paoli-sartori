package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.List;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Resources;

public class Count extends Objective {
    private List<Resources> resources;

    public Count(List<Resources> resources){
        super();
        this.resources=resources;
    }

    /**
     * ritorno il numero di punti ottenuti dal giocatore
     * a seconda del "sottotipo" della carta obiettivo richiamo diversi metodi privati interni alla classe
     * caso 1: obiettivo con 3 risorse che siano solo animali, funghi, insetti o piante
     * caso 2: obiettivo che contiene gli altri tipi di risorse (pergamena, inchiostro, piuma)
     * @param placedCard
     * @return
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint){
        /**
         * se la lista di risorse da controllare contiene animali, insetti, funghi o piante richiamo la funzione
         * countAPIM che ritorna il numero di punti che a mia volta ritorno al chiamante.
         * Altrimenti richiamo e ritorno il valore della funzione countFIS per il caso che la lista di risorse
         * contenga gli altri possibili oggetti.
         * I metodi sono leggermente diversi, il numero di punti ottenuti è diverso... Potrebbe essere overkill, valuto
         * alla fine dell'implementazione se è opportuno unirli in un unico metodo
         */
        if(resources.get(0)==Resources.ANIMAL || resources.get(0)==Resources.PLANT ||
                resources.get(0)==Resources.INSECT || resources.get(0)==Resources.MUSHROOM ) {
            return countAPIM(placedCard);
        }
        return countFIS(placedCard);
    }

    /**
     * conto e ritorno i punti ottenuti nel caso in cui la lista contenga piante, animali, insetti o funghi
     * @param placedCard
     * @return
     */
    private int countAPIM(Map<Point, PlayableCard> placedCard){
        /**
         * salvo in seed il tipo di risorsa che voglio cercare. Prendo la prima perchè sono tutte uguali
         * suppongo che la lista sia ben formata
         */
        Resources seed = resources.get(0);
        int found=searchOnBoard(placedCard, seed);
        return 2 * ( (found - found % 3 ) / 3 );
    }

    /**
     * conto e ritorno i punti ottenuti nel caso in cui la lista contenga inchiostro, piume o pergamena
     * @param placedCard
     * @return
     */
    private int countFIS(Map<Point, PlayableCard> placedCard) {
        Resources seed = null;
        int found = 0;

        /**
         * se la lunghezza della lista resources è pari a 2 so che avrò la stessa risorsa ripetuta nella lista
         * trovo quante volte è presente la risorsa con il metodo searchOnBoard
         */
        if (resources.size() == 2) {
            seed = resources.get(0);
            found = searchOnBoard(placedCard, seed);

            /**
             * ritorno il numero di coppie trovato, contando per ciascuna coppia 2 punti
             */
            return 2 * ((found - found % 2) / 2);
        }

        /**
         * trovo la risorsa con minore ricorrenza, che sarà uguale al numero di terne complete presenti nel campo
         */
        found = searchOnBoard(placedCard, Resources.FEATHER);
        if (searchOnBoard(placedCard, Resources.INK) < found)
            found = searchOnBoard(placedCard, Resources.INK);
        if (searchOnBoard(placedCard, Resources.SCROLL) < found)
            found = searchOnBoard(placedCard, Resources.SCROLL);

        /**
         * ritorno il numero di terne trovato, contando per ciascuna terna 3 punti
         */
        return 3 * ( (found - found % 3) / 3);
    }

    /**
     * ritorna quante volte è presente la risorsa seed all'interno del campo di gioco del giocatore
     * @param placedCard
     * @param seed
     * @return
     */
    private int searchOnBoard(Map<Point, PlayableCard> placedCard, Resources seed) {
        List<Resources> list=null;

        /**
         * per ogni chiave della mappa controllo la carta corrispondente
         */
        for (Point c : placedCard.keySet()) {
            list = placedCard.get(c).getResources();

            /**
             * itero la lista di risorse presenti nella carta, nel caso in cui la risorsa alla posizione i sia
             * uguale a quella ricercata (seed) incremento il contatore
             */
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i).equals(seed)) {
                    score++;
                }
            }

        }

        /**
         * ritorno il numero di risorse pari a seed trovate
         */
        return score;
    }
}
