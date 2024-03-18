package it.polimi.ingsw.gc31.model.strategies;

import java.awt.*;
import java.util.Map;

import it.polimi.ingsw.gc31.model.card.PlayableCard;

public abstract class Objective {
    protected int score;

    public Objective(){
        this.score=0;
    }

    public int isObjectiveDone(Map<Point, PlayableCard> placedCard, Point point){
        return 0;
    }

    protected int findMaxX(Map<Point, PlayableCard> placedCard) {
        int maxX=0;
        for (Point c : placedCard.keySet()) {
            if (maxX < c.getX()) {
                maxX = (int) c.getX();
            }
        }
        return maxX;
    }

    protected int findMaxY(Map<Point, PlayableCard> placedCard) {
        int maxY=0;
        for (Point c : placedCard.keySet()) {
            if (maxY < c.getY()) {
                maxY = (int) c.getY();
            }
        }
        return maxY;
    }

    protected int findMinX(Map<Point, PlayableCard> placedCard) {
        int minX=0;
        for (Point c : placedCard.keySet()) {
            if (minX > c.getX()) {
                minX = (int) c.getX();
            }
        }
        return minX;
    }

    protected int findMinY(Map<Point, PlayableCard> placedCard) {
        int minY=0;
        for (Point c : placedCard.keySet()) {
            if (minY > c.getY()) {
                minY = (int) c.getY();
            }
        }
        return minY;
    }
}

