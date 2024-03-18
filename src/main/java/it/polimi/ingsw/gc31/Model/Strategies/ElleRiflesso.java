package it.polimi.ingsw.gc31.Model.Strategies;

import it.polimi.ingsw.gc31.Model.Card.PlayableCard;
import it.polimi.ingsw.gc31.Model.Enum.Color;

import java.awt.*;
import java.util.Map;

public class ElleRiflesso extends Objective {
    /**
     * color1 rappresenta il colore presente più volte(2)
     */
    private Color color1;
    /**
     * color2 rappresenta il colore presente meno volte(1)
     */
    private Color color2;

    public ElleRiflesso(Color color1, Color color2){
        super();
        this.color1=color1;
        this.color2=color2;
    }

    public int isObjectiveDone(Map<Point, PlayableCard> placedCard){
        /**
         * inizializzo i point massime presente sul campo del giocatore
         */
        int maxX=findMaxX(placedCard), minX=findMinX(placedCard), maxY=findMaxY(placedCard), minY=findMinY(placedCard);

        for(int j = maxY; j >= minY + 2; j--) {
            for (int i = minX + 1; i <= maxX; i++) {
                Point point = new Point(i,j);
                if(placedCard.get(point)!=null) {

                    if(placedCard.get(point).getColor().equals(color1)){
                        point = new Point(i, j-1);
                        if(placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color1)){
                            point = new Point(i-1, j-2);
                            if(placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color2)){
                                score+=3;

                                /**
                                 * rimuovo le carte utilizzate per ottenere i punti
                                 */
                                placedCard.remove(point);
                                point = new Point(i, j-1);
                                placedCard.remove(point);
                                point = new Point(i, j);
                                placedCard.remove(point);
                            }
                        }
                    }
                }
                /**
                 * se qui non è presente una carta devo passare subito alla prossima posizione
                 */
            }
        }

        /**
         * ritorno i punti accumulati
         */
        return score;
    }
}
