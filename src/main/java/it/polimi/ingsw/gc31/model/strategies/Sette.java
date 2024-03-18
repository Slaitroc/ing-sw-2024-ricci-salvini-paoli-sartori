package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.enumeration.Color;

public class Sette extends Objective {
    /**
     * color1 rappresenta il colore presente più volte (2)
     */
    private Color color1;
    /**
     * color2 rappresenta il colore presente meno volte (1)
     */
    private Color color2;

    public Sette(Color color1, Color color2){
        super();
        this.color1=color1;
        this.color2=color2;
    }

    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point uselessPoint){
        /**
         * inizializzo i point massime e minime presenti sul campo del giocatore
         */
        int maxX=findMaxX(placedCard), minX=findMinX(placedCard), maxY=findMaxY(placedCard), minY=findMinY(placedCard);

        for(int j = maxY; j >= minY + 2; j--) {
            for (int i = maxX; i >= minX + 1; i--) {
                Point point = new Point(i,j);

                /**
                 * se la carta non è presente vado subito avanti
                 * altrimenti controllo se anche le condizioni successive sono verificate
                 */
                if(placedCard.get(point)!=null) {

                    if(placedCard.get(point).getColor().equals(color2)){
                        point = new Point(i-1, j-1);
                        if(placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color1)){
                            point = new Point(i-1, j-2);
                            if(placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color1)){
                                score+=3;

                                /**
                                 * rimuovo le carte utilizzate per ottenere i punti
                                 */
                                placedCard.remove(point);
                                point = new Point(i-1, j-1);
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