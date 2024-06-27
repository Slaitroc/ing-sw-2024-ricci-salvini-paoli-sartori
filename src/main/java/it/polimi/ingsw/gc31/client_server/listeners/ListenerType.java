package it.polimi.ingsw.gc31.client_server.listeners;

/**
 * Enumerator class for the various listener types, use these to tell the {@link GameListenerHandler} which listeners to create, notify, or remove
 *
 * @author sslvo
 */
public enum ListenerType {
    PLAYAREA,
    HAND,
    CHOOSE_OBJECTIVE,
    STARTER_CARD,
    TURN,
    OBJECTIVE_CARD,
    GOLD_DECK,
    RESOURCE_DECK,
    COMMON_OBJECTIVE,
    PLAYER_SCORE
}
