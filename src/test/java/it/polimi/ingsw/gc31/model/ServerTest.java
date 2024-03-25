package it.polimi.ingsw.gc31.model;

import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import it.polimi.ingsw.gc31.Server;
import it.polimi.ingsw.gc31.controller.Controller;
import it.polimi.ingsw.gc31.controller.GameController;
import it.polimi.ingsw.gc31.model.GameModel;
import it.polimi.ingsw.gc31.model.card.Card;
import it.polimi.ingsw.gc31.model.card.GoldCard;
import it.polimi.ingsw.gc31.model.exceptions.GameModelAlreadyCreatedException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNicknameAlreadyExistsException;
import it.polimi.ingsw.gc31.model.exceptions.PlayerNumberNotReachedException;
import it.polimi.ingsw.gc31.view.GameView;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardType;

public class ServerTest<T extends Card> {
    private GameModel gameModel;
    private Controller controller;
    private GameView view;
    private Deck<T> deck;
    private static List<String> usernamesTestList = new ArrayList<String>();

    @BeforeAll
    static void addPlayersToTestList() {
        // creo gli username per i futuri player
        usernamesTestList.add("Alessandro");
        usernamesTestList.add("Christian");
        usernamesTestList.add("Matteo");
        usernamesTestList.add("Lorenzo");
        usernamesTestList.add("Lorenzo");
    }

    // Verifica che:
    // Vengano aggiunti correttamente i nicknames presenti nella usernamesTestList
    // al Controller.
    // Non vengano aggiunti duplicati
    @Test
    void addPlayerToControllerTest() {
        for (String string : usernamesTestList) {
            Server.addPlayerToController(string);
        }
        assert (Server.getController().getGlobalUsernameSet().containsAll(usernamesTestList));
    }

    // Verifica che:
    // Il GameController creato dal Server attraverso il Controller venga creato
    // correttamente con il primo player della usernamesTestList.
    // Vengano aggiunti correttamente i player che non ne hanno comportato la
    // creazione
    // Non sia possibile aggiungere duplicati
    @Test
    void gameControllerCreationTest() {
        GameController gameController = Server.createGameController(usernamesTestList.get(0), 4);
        for (String string : usernamesTestList) {
            Server.addPlayerToGameController(gameController, string);
        }
        assert (gameController.getPlayerList().containsAll(usernamesTestList));
    }

    // Qui viene create un GameModel sul quale è possibile effettuare i test sulla
    // corretta inizializzazione della partita
    @Test
    void gameModelCreationTest() {
        GameController gameController = Server.createGameController(usernamesTestList, 4);
        // assert(gameController.getGameModel())
        /* Qui vanno aggiunti test sul Game Model creato */
        /*
         * Tutto questo sta implicitamente testando anche le deepcopy che ora si
         * chiamano clone
         */
    }

    // Verifica che:
    // La deepcopy del deck restituisca effettivamente un'altro deck con le carti
    // presenti nello stesso ordine
    @Test
    void deepCopyDeck() {
        deck = new Deck<T>(CardType.GOLD);
        deck.refill();
        Deck<T> clone = deck.deepCopy();
        assert (deck.getCard1().equals(clone.getCard1()));
        assert (deck.draw().equals(clone.draw()));
        for (int i = 0; i < deck.getQueue().size(); i++)
            assert (deck.getQueue().poll().equals(clone.getQueue().poll()));
    }

}
