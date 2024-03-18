package it.polimi.ingsw.gc31.Model.Strategies;

import it.polimi.ingsw.gc31.Model.Card.PlayableCard;
import it.polimi.ingsw.gc31.Model.Enum.Color;

import java.awt.*;
import java.util.Map;

public class ScalaGiu extends Objective {
    private Color color;

    public ScalaGiu(Color color){
        super();
        this.color=color;
    }

    /**
     * per dettagli su problematiche e funzionalità vedi classe "ScalaSu"
     * obiettivo e funzionamento analogo cambia solamente l'ordine in cui effettuare i confronti
     * @param placedCard
     * @return
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard){
        /**
         * inizializzo i point massime e minime presenti sul campo del giocatore
         */
        int maxX=findMaxX(placedCard), minX=findMinX(placedCard), maxY=findMaxY(placedCard), minY=findMinY(placedCard);

        for(int j = maxY; j >= minY + 2; j--) {
            for (int i = minX; i <= maxX - 2; i++) {
                Point point = new Point(i,j);
                /**
                 * se qui non è presente una carta devo passare subito alla prossima posizione
                 */
                if(placedCard.get(point)!=null) {

                    if (placedCard.get(point).getColor().equals(color)) {
                        point = new Point(i + 1, j + 1);
                        if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                            point = new Point(i + 2, j + 2);
                            if (placedCard.get(point)!=null && placedCard.get(point).getColor().equals(color)) {
                                /**
                                 * incremento score ogni volta che soddisfo l'biettivo di 2 punti
                                 */
                                score+=2;

                                /**
                                 * rimuovo le carte utilizzate per ottenere i punti
                                 */
                                placedCard.remove(point);
                                point = new Point(i+1, j+1);
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
