package it.polimi.ingsw.gc31.view.tui;

public enum TUIcommands {
    NOTIFY("notify"),
    INVALID("invalid"),
    SHOW_COMMAND_INFO("help"),
    INITIAL("initial"),
    REFRESH("ref"),
    SET_USERNAME("set username"),
    CREATE_GAME("create game"),
    JOIN_GAME("join game"),
    SHOW_GAMES("show games"),
    QUIT_GAME("quit"),
    READY("ready"),
    DRAW_GOLD("dg"),
    DRAW_RESOURCES("dr"),
    CHOOSE_SERCRET_OBJ("co"),
    PLAY_STARTER("ps"),
    PLAY("p"),
    SELECT_CARD("s"),
    CHANGE_SIDE("c"),
    CHANGE_STARTER_SIDE("cs"),
    MOVE_PLAY_AREA("mv");

    private final String tag;

    TUIcommands(String tag) {
        this.tag = tag;
    }

    @Override
    public String toString() {
        return tag;
    }
}
