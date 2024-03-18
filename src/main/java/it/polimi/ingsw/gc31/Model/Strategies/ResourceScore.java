package it.polimi.ingsw.gc31.Model.Strategies;

import it.polimi.ingsw.gc31.Model.Card.PlayableCard;
import it.polimi.ingsw.gc31.Model.Enum.Resources;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 *
 */
public class ResourceScore extends Objective {
    private Resources resource;

    public ResourceScore(Resources resource){
        super();
        this.resource=resource;
    }

    /**
     * @param placedCard : is the card the player is placing now
     * @return int : the number of points the player get by placing this card
     */
    public int isObjectiveDone(Map<Point, PlayableCard> placedCard) {
        /**
         * itero per ogni coordinata presente nella mappa ottenendo ogni carta posizionata. Per ogni carta
         * ottengo la lista di risorse che possiede e verifico per ogni posizione della lista se la Resource
         * che cerco è presente, contando quante volte appare. Alla fine del conteggio so quante risorse uguali a
         * Resource sono presenti sul tavolo e ne ritorno il valore.
         */
        for (Point c : placedCard.keySet()) {
            for (int i=0; i >= 0 && i < placedCard.get(c).getResources().size(); i++) {
                if (resource.equals(placedCard.get(c).getResources().get(i))) {
                    score++;
                }
            }
        }
        return score;
    }
}
