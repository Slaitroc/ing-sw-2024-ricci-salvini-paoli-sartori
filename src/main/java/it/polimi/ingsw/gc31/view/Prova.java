package it.polimi.ingsw.gc31.view;

import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.*;

import java.util.*;
import it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater;
import it.polimi.ingsw.gc31.utility.gsonUtility.ObjectiveAdapter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;
import static org.fusesource.jansi.Ansi.ansi;


public class Prova {
    public static void main(String[] args) {
        AnsiConsole.systemInstall();
        System.out.print("\033[H\033[2J");
        System.out.flush();

//
//        Objective ob = new ResourceScore(Resources.ANIMAL);
//        String obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        Objective ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        ob = new CoverCornerScore();
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // count
//        List resources = new ArrayList();
//        resources.add(Resources.MUSHROOM);
//        resources.add(Resources.MUSHROOM);
//        resources.add(Resources.MUSHROOM);
//        ob = new Count(resources);
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // lshape
//        ob = new LShape();
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // lshape reverse
//        ob = new LShapeReverse();
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // Seven
//        ob = new Seven();
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // SevenReverse
//        ob = new SevenReverse();
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // StairDown
//        ob = new StairDown(CardColor.RED);
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        // stairUp
//        ob = new StairUp(CardColor.BLUE);
//        obJson = gsonTranslater.toJson(ob, Objective.class);
//        System.out.println(obJson);
//        ob1 = gsonTranslater.fromJson(obJson, Objective.class);
//
//        Deck<ObjectiveCard> deck = new Deck<>(CardType.OBJECTIVE);
//        List<String> deckJson = new ArrayList<>();
//
//        for (int i=0; i<16; i++) {
//            String json = gsonTranslater.toJson(deck.draw(), ObjectiveCard.class);
//            System.out.println(json);
//            deckJson.add(json);
//        }
//
//        List<ObjectiveCard> deckFromJson = new ArrayList<>();
//        for (int i=0; i<16; i++) {
//            deckFromJson.add(gsonTranslater.fromJson(deckJson.get(i), ObjectiveCard.class));
//        }
//

        StringBuilder res = new StringBuilder();
        res.append(Ansi.ansi().cursor(5,5).a("ok"));

        StringBuilder res2 = new StringBuilder();
        res2.append(Ansi.ansi().cursor(10, 10).a("ok2"));

        res.append(res2);

        System.out.println(res);
        AnsiConsole.systemUninstall();
    }
}
