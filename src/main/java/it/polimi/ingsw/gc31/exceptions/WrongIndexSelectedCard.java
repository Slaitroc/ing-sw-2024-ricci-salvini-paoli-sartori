package it.polimi.ingsw.gc31.exceptions;

/**
 * This exception is launched if the index selected is not valid. For example for selecting a card.
 *
 * @see it.polimi.ingsw.gc31.controller.GameController#selectCard(String, int)
 */
public class WrongIndexSelectedCard extends Exception{
    public WrongIndexSelectedCard(){
        super("Wrong index selected");
    }
}
