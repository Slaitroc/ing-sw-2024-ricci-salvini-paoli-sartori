package it.polimi.ingsw.gc31.view.tui;

public enum TUIstateCommands {
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#stateNotify()} command
     */
    NOTIFY("notify"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_invalidCommand()}
     * command
     * command}
     */
    INVALID("invalid"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_showCommandsInfo()}
     * command
     * command}
     */
    SHOW_COMMAND_INFO("help"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_refresh()} command
     * command}
     */
    REFRESH("ref"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#setUsername()} command
     * command}
     */
    SET_USERNAME("set username"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#reconnect()} command
     * command}
     */
    RECONNECT("reconnect"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_createGame()} command
     * command}
     */
    CREATE_GAME("create game"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_joinGame()} command
     * command}
     */
    JOIN_GAME("join game"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_showGames()} command
     * command}
     */
    SHOW_GAMES("show games"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_quitGame()} command
     * command}
     */
    QUIT_GAME("quit"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_ready()} command
     * command}
     */
    READY("ready"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_drawGold()} command
     * command}
     */
    DRAW_GOLD("dg"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_drawResource()} command
     * command}
     */
    DRAW_RESOURCES("dr"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_chooseSecreteObjective()}
     * command
     * command}
     */
    CHOOSE_SECRET_OBJ("co"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_playStarter()} command
     * command}
     */
    PLAY_STARTER("ps"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_play()} command
     * command}
     */
    PLAY("p"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_selectCard()} command
     * command}
     */
    SELECT_CARD("s"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_changeSide()} command
     * command}
     */
    CHANGE_SIDE("c"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_changeStarterSide()}
     * command
     * command}
     */
    CHANGE_STARTER_SIDE("cs"),
    /**
     * Defines the tag for the
     * {@link it.polimi.ingsw.gc31.view.tui.TUIstate#command_movePlayArea()} command
     * command}
     */
    MOVE_PLAY_AREA("mv"),
    // /**
    // * Defines the tag for the
    // * {@link it.polimi.ingsw.gc31.view.tui.TUIstate}
    // * command}
    // */
    // ANOTHERMATCH("another match");
    ;

    private final String tag;

    /**
     * Constructor for the TUIstateCommands enum
     * 
     * @param tag
     */
    TUIstateCommands(String tag) {
        this.tag = tag;
    }

    /**
     * Returns the tag of the TUIstateCommands enum
     * 
     * @return tag
     */
    @Override
    public String toString() {
        return tag;
    }
}
