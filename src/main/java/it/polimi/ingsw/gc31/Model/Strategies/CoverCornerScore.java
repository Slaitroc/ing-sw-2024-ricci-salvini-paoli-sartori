package it.polimi.ingsw.gc31.Model.Strategies;

import it.polimi.ingsw.gc31.Model.Card.PlayableCard;

import java.awt.*;
import java.util.Map;

public class CoverCornerScore extends Objective {

    public CoverCornerScore(){
        super();
    }

    /**
     * prendo in input tutte le carte sul campo del giocatore e i point della carta appena giocata
     * sapendo i point della carta appena giocata controllo quante delle 4 carte ai suoi angoli esistono
     * per ogni carta che esiste aggiungo 1 allo score
     * @param placedCard
     * @param point
     * @return
     */
    public int isObjeciveDone(Map<Point, PlayableCard> placedCard, Point point){
        /**
         * creo 4 Point per ogni angolo che possiede la carta
         */
        Point altoDx = new Point((int) point.getX() + 1, (int) point.getY() + 1);
        Point altoSx = new Point((int) point.getX() - 1, (int) point.getY() + 1);
        Point bassoDx = new Point((int) point.getX() + 1, (int) point.getY() - 1);
        Point bassoSx = new Point((int) point.getX() - 1, (int) point.getY() - 1);

        /**
         * verifico quali punti 4 punti adiacenti alla carta esistano. Se esistono aumento il numero di carte che
         * sono coperte da quella appena piazzata
         * alla fine ritorno il numero di carte coperte moltiplicato per 2
         */
        if(placedCard.get(altoDx)!=null){ score++; }
        if(placedCard.get(altoSx)!=null){ score++; }
        if(placedCard.get(bassoDx)!=null){ score++; }
        if(placedCard.get(bassoSx)!=null){ score++; }

        return 2 * score;
    }
}
