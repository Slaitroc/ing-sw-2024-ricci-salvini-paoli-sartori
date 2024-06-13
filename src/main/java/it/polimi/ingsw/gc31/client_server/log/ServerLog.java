package it.polimi.ingsw.gc31.client_server.log;

import it.polimi.ingsw.gc31.utility.DV;

public class ServerLog {

    private ServerLog() {

    }

    public static void tcpWrite(String text) {
        System.out.println(DV.ANSI_YELLOW + DV.TCP_SERVER_TAG + DV.ANSI_RESET + text);
    }

    public static void rmiWrite(String text) {
        System.out.println(DV.ANSI_GREEN + DV.RMI_SERVER_TAG + DV.ANSI_RESET + text);
    }

    public static void controllerWrite(String text) {
//        System.out.println(DV.ANSI_GREEN + DV.RMI_SERVER_TAG + DV.ANSI_BLUE
//                + DV.CONTROLLER_TAG + DV.ANSI_RESET + text);
        System.out.println( DV.ANSI_BLUE
                + DV.CONTROLLER_TAG + DV.ANSI_RESET + text);

    }

    public static void gControllerWrite(String text, int idGame) {
        System.out.println(DV.ANSI_PURPLE
                + DV.gameControllerTag(String.valueOf(idGame)) + DV.ANSI_RESET + text);
    }
}
