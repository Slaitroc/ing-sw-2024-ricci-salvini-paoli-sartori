package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

/**
 * l'attributo Color penso dovrebbe essere sosituito con Resource. Color si riferisce al colore della pedina del giocatore
 * mentre a me interssa la tipologia di carta (animale, insetto, fungo, vegetale) che corrisponde al certo colore rappresentato
 */
public class ScalaSu extends Objective {
    private Color color;

    public ScalaSu(Color color){
        super();
        this.color=color;
    }

    /**
     * @param placedCard
     * @return
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint){
        /**
         * inizializzo i point massime e minime presenti sul campo del giocatore
         */
        int maxX=findMaxX(placedCard), minX=findMinX(placedCard), maxY=findMaxY(placedCard), minY=findMinY(placedCard);

        /**
         * Partendo dall'angolo in basso a sinistra verifico se è stata soddisfatta la richiesta dell'obiettivo.
         * Ad ogni iterazione mi sposto a destra lungo l'asse orizzontale, quando raggiungo la fine riparto daccapo
         * ma alzandomi sull'asse delle ordinate.
         * TODO una volta ottenuta la carta comincio a verificare ma ho bisogno di confrontare il "colore" dell'obiettivo
         * TODO con quello della carta. Ogni corta possiede l'attributo Colore ma è privta e non c'è un getter, senza
         * TODO questo non so come poter fare il confronto. Per completare comunque il codice ho supposto esistesse un
         * TODO metodo getColor() di Card che ritorna l'attributo colore.
         * TODO Inoltre l'attributo Color è presente unicamente nelle sottoclassi di Card, nella classe Card non è
         * TODO presente l'attributo Color ma sono presenti gli attributi a CardFront e CardBack i quali a loro volta
         * TODO posseggono l'attributo Color.
         */
        for(int j = minY; j <= maxY-2; j++) {
            for (int i = minX; i <= maxX-2; i++) {
                Point point = new Point(i,j);
                /**
                 * se nella posizione considerata non è presente una carta devo passare subito alla prossima posizione
                 */
                if(placedCard.get(point)!=null) {

                    if (placedCard.get(point).getColor().equals(color)) {
                        point = new Point(i+1, j+1);
                        if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                            point = new Point(i+2, j+2);
                            if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                                /**
                                 * incremento score ogni volta che soddisfo l'biettivo di 2 punti
                                 */
                                score += 2;

                                /**
                                 * se l'obiettivo è conseguito devo togliere le carte considerate, non posso usare
                                 * la stessa carta per completare pù volte lo stesso obiettivo
                                 * Idea: setto a null il value associato a quello specifico valore di point, devo
                                 * però avere alla fine dei 3 if un riferimento a tutte e 3 le point utilizzate.
                                 * Posso usare 3 oggetti Point diversi (più pesante prima, ma ora risulta più agevole
                                 * togliere Card) oppure uso uno stesso oggetto che modifico più volte (uso un solo oggetto
                                 * quindi magari meno pesante, tuttavia ora devo ri-modificarlo 3 volte per togliere
                                 * ogni Card)
                                 */
                                placedCard.remove(point);
                                point= new Point(i+1, j+1);
                                placedCard.remove(point);
                                point = new Point(i, j);
                                placedCard.remove(point);
                            }
                        }
                    }
                }
            }
        }
        /**
         * ritorno i punti accumulati
         */
        return score;
    }
}
