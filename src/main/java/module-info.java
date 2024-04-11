module it.polimi.ingsw.gc31 {
    requires transitive javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires org.controlsfx.controls;
    requires java.desktop;
    requires com.google.gson;
    requires java.rmi;
    requires MaterialFX;

    opens it.polimi.ingsw.gc31.view to javafx.fxml;

    exports it.polimi.ingsw.gc31; // Esporta il tuo package principale
    exports it.polimi.ingsw.gc31.controller;
    exports it.polimi.ingsw.gc31.model.exceptions;
    exports it.polimi.ingsw.gc31.client_server.interfaces;
    exports it.polimi.ingsw.gc31.client_server.tcp;
    exports it.polimi.ingsw.gc31.client_server.rmi;
    exports it.polimi.ingsw.gc31.rmi_tutorial.basic_setup;
    exports it.polimi.ingsw.gc31.rmi_tutorial.our_setup;
    exports it.polimi.ingsw.gc31.rmi_tutorial.super_clean_basic_setup;

}
