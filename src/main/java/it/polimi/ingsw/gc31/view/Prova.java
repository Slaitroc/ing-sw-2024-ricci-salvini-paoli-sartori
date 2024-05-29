package it.polimi.ingsw.gc31.view;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.DrawResObj;
import it.polimi.ingsw.gc31.client_server.queue.serverQueue.ServerQueueObject;
import it.polimi.ingsw.gc31.model.card.ObjectiveCard;
import it.polimi.ingsw.gc31.model.card.PlayableCard;
import it.polimi.ingsw.gc31.model.card.ResourceCard;
import it.polimi.ingsw.gc31.model.deck.Deck;
import it.polimi.ingsw.gc31.model.enumeration.CardColor;
import it.polimi.ingsw.gc31.model.enumeration.CardType;
import it.polimi.ingsw.gc31.model.enumeration.Resources;
import it.polimi.ingsw.gc31.model.strategies.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Parameter;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.*;
import it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater;
import it.polimi.ingsw.gc31.utility.gsonUtility.ObjectiveAdapter;
import it.polimi.ingsw.gc31.utility.gsonUtility.RuntimeTypeAdapterFactory;
import it.polimi.ingsw.gc31.utility.gsonUtility.ServerQueueObjAdapter;
import org.fusesource.jansi.Ansi;
import org.fusesource.jansi.AnsiConsole;

import static org.fusesource.jansi.Ansi.ansi;

import static it.polimi.ingsw.gc31.utility.gsonUtility.GsonTranslater.gsonTranslater;


public class Prova {
    public static void main(String[] args){
    }
}
